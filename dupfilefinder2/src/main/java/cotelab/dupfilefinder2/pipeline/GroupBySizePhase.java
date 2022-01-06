/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A {@link Phase} designed to group files by size. The input is a single
 * collection containing pathnames of regular files. The output is a stream of
 * collections of pathnames of files with matching lengths.
 * 
 * @author alantcote
 *
 */
public class GroupBySizePhase extends Phase {
	protected SimpleIntegerProperty filesMeasuredCount = new SimpleIntegerProperty(0);
	protected Hashtable<Long, ArrayList<Path>> size2PathMap = new Hashtable<Long, ArrayList<Path>>();
	protected SimpleIntegerProperty sizeCount = new SimpleIntegerProperty(0);
	protected SimpleIntegerProperty uniqueCount = new SimpleIntegerProperty(0);
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
	 * @return the unmeasurableCount
	 */
	public SimpleIntegerProperty getUnmeasurableCount() {
		return unmeasurableCount;
	}

	@Override
	protected Void call() throws Exception {
//		System.out.println("GroupBySizePhase.call(): entry");

		try {
//			System.out.println("GroupBySizePhase.call(): taking");

			// only really expect one batch
			Collection<Path> batch = inputQueue.take();

//			System.out.println("GroupBySizePhase.call(): calling processBatch()");

			processBatch(batch);

//			System.out.println("GroupBySizePhase.call(): batch processed");
		} catch (InterruptedException e) {
			// if cancelled, it'll be discovered later
//			System.out.println("GroupBySizePhase.call(): caught exception");
		}

//		System.out.println("GroupBySizePhase.call(): checking cancellation");

		if (!isCancelled()) {
//			System.out.println("GroupBySizePhase.call(): publishing results");

			publishResults();
		}

//		System.out.println("GroupBySizePhase.call(): method completed");

		return null;
	}

	protected void processBatch(Collection<Path> batch) {
		for (Path path : batch) {
			if (isCancelled()) {
				break;
			}

			try {
				long size = Files.size(path);
				ArrayList<Path> pathColl = size2PathMap.get(size);

				if (pathColl == null) {
					pathColl = new ArrayList<Path>();

					pathColl.add(path);

					size2PathMap.put(size, pathColl);

					sizeCount.set(size2PathMap.size());
				} else {
					pathColl.add(path);
				}

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
		
		Collection<ArrayList<Path>> groups = size2PathMap.values();

		for (ArrayList<Path> group : groups) {
			if (isCancelled()) {
				break;
			}
			
			int groupSize = group.size();
			
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

	/**
	 * @return the uniqueCount
	 */
	public SimpleIntegerProperty getUniqueCount() {
		return uniqueCount;
	}

}
