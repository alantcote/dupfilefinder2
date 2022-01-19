package cotelab.dupfilefinder2.pipeline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A {@link Phase} designed to group files by size. The input is a single
 * collection containing pathnames of regular files. The output is a stream of
 * collections of pathnames of files with matching lengths.
 */
public class GroupBySizePhase extends Phase {
	/**
	 * The number of files measured.
	 */
	protected SimpleIntegerProperty filesMeasuredCount = new SimpleIntegerProperty(0);

	/**
	 * A map from file size to path group.
	 */
	protected MultiValuedMap<Long, Path> size2PathMap = newMultiValuedMapLongPath();

	/**
	 * The number of file sizes found.
	 */
	protected SimpleIntegerProperty sizeCount = new SimpleIntegerProperty(0);

	/**
	 * The number of unique files discovered.
	 */
	protected SimpleIntegerProperty uniqueCount = new SimpleIntegerProperty(0);

	/**
	 * The number of files that couldn't be measured.
	 */
	protected SimpleIntegerProperty unmeasurableCount = new SimpleIntegerProperty(0);

	/**
	 * Construct a new object.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public GroupBySizePhase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super(name, theInput, theOutput);
	}

	/**
	 * @return the filesMeasuredCount
	 */
	public SimpleIntegerProperty getFilesMeasuredCount() {
		return filesMeasuredCount;
	}

	/**
	 * @return the sizeCount
	 */
	public SimpleIntegerProperty getSizeCount() {
		return sizeCount;
	}

	/**
	 * @return the uniqueCount
	 */
	public SimpleIntegerProperty getUniqueCount() {
		return uniqueCount;
	}

	/**
	 * @return the unmeasurableCount
	 */
	public SimpleIntegerProperty getUnmeasurableCount() {
		return unmeasurableCount;
	}

	@Override
	protected Void call() throws Exception {
		try {
			Collection<Path> batch;

			// obeying EOF convention
			while (((batch = inputQueue.take()) != null) && !batch.isEmpty()) {

				processBatch(batch);
			}
		} catch (InterruptedException e) {
			// if cancelled, it'll be discovered later
		}

		if (!isCancelled()) {
			publishResults();
		}

		outputQueue.put(new ArrayList<Path>()); // EOF convention

		return null;
	}

	protected long fileSize(Path path) throws IOException {
		return Files.size(path);
	}

	/**
	 * @return a new object.
	 */
	protected MultiValuedMap<Long, Path> newMultiValuedMapLongPath() {
		return MultiMapUtils.newListValuedHashMap();
	}

	protected void processBatch(Collection<Path> batch) {
		for (Path path : batch) {
			if (isCancelled()) {
				break;
			}

			try {
				long size = fileSize(path);
				size2PathMap.put(size, path);

				filesMeasuredCount.set(filesMeasuredCount.get() + 1);
			} catch (IOException e) {
				unmeasurableCount.set(unmeasurableCount.get() + 1);
			}
		}
	}

	protected void publishResults() {
		if (isCancelled()) {
			return;
		}

		Set<Long> sizes = size2PathMap.keySet();

		for (Long size : sizes) {
			Collection<Path> group = size2PathMap.get(size);
			int groupSize = group.size();

			if (isCancelled()) {
				break;
			}

			// if a group is empty, one can only wonder how it came to be

			if (groupSize == 1) {
				// if there's only 1 in the group, it's a unique file
				uniqueCount.set(uniqueCount.get() + 1);
			} else if (groupSize > 1) {
				// if there are 2 or more in the group, they match by size
				try {
					outputQueue.put(group);
				} catch (InterruptedException e) {
					if (isCancelled()) {
						break;
					}
				}
			}
		}
	}

}
