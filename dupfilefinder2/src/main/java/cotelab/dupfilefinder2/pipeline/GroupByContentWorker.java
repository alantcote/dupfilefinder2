package cotelab.dupfilefinder2.pipeline;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;

import cotelab.dupfilefinder2.beans.property.FXThreadIntegerProperty;
import cotelab.dupfilefinder2.beans.property.FXThreadLongProperty;
import cotelab.dupfilefinder2.pipeline.phase.Phase;
import cotelab.dupfilefinder2.pipeline.queueing.PipelineQueue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 * A {@link Phase} designed to group files by content. The input is a sequence
 * of collections of pathnames to be grouped. The output is a sequence of
 * collections of pathnames of files with matching contents.
 */
public class GroupByContentWorker extends Phase {
	/**
	 * The buffer size for buffered input streams.
	 */
	public static final int BIS_BUFFER_SIZE = 1024 * 1024;

	/**
	 * the buffer size for buffered input streams used for n-way comparisons.
	 */
	public static final int NWCES_BUFFER_SIZE = 1024 * 1024;

	/**
	 * The number of bytes compared.
	 */
	protected FXThreadLongProperty bytesComparedCount = newThreadSafeSimpleLongProperty();

	/**
	 * The number of files compared.
	 */
	protected FXThreadIntegerProperty filesComparedCount = newThreadSafeSimpleIntegerProperty();

	/**
	 * The number of unique files found.
	 */
	protected FXThreadIntegerProperty uniqueCount = newThreadSafeSimpleIntegerProperty();

	/**
	 * Construct a new object.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public GroupByContentWorker(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super(name, theInput, theOutput);
	}

	/**
	 * @return the bytesComparedCount
	 */
	public SimpleLongProperty getBytesComparedCount() {
		return bytesComparedCount;
	}

	/**
	 * @return the filesComparedCount
	 */
	public SimpleIntegerProperty getFilesComparedCount() {
		return filesComparedCount;
	}

	/**
	 * @return the uniqueCount
	 */
	public SimpleIntegerProperty getUniqueCount() {
		return uniqueCount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Void call() throws Exception {
		try {
			Collection<Path> batch = inputQueue.take();

			while (!isCancelled() && (batch != null) && !batch.isEmpty()) { // EOF convention
				processBatch(batch);

				if (isCancelled()) {
					break;
				}

				batch = inputQueue.poll();
			}
		} catch (Exception e) {
			showException(e);
		}

		return null;
	}

	protected long fileSize(Path path) throws IOException {
		return Files.size(path);
	}

	/**
	 * @param collection a group of input streams, the count of which will be the
	 *                   array dimension.
	 * @return a new object.
	 */
	protected InputStream[] newArrayOfInputStream(Collection<InputStream> collection) {
		return newInputStreamArray(collection.size());
	}

	/**
	 * @param is         the raw {@link InputStream}.
	 * @param bufferSize the buffer size.
	 * @return a new object.
	 * @throws IOException if thrown by the underlying code.
	 */
	protected InputStream newBufferedInputStream(InputStream is, int bufferSize) throws IOException {
		return new BufferedInputStream(is, bufferSize);
	}

	/**
	 * @return a new object.
	 */
	protected byte[] newByteBuffer() {
		return new byte[NWCES_BUFFER_SIZE];
	}

	/**
	 * @param size the dimension of the array.
	 * @return a new object.
	 */
	protected InputStream[] newInputStreamArray(int size) {
		return new BufferedInputStream[size];
	}

	/**
	 * @return a new object.
	 */
	protected Collection<InputStream> newInputStreamCollection() {
		return new ArrayList<InputStream>();
	}

	/**
	 * @return a new object.
	 */
	protected Collection<Collection<InputStream>> newInputStreamGroupCollection() {
		return new ArrayList<Collection<InputStream>>();
	}

	/**
	 * @return a new object.
	 */
	protected Map<InputStream, Path> newInputStreamToPathMap() {
		return new HashMap<InputStream, Path>();
	}

	/**
	 * @return a new object.
	 */
	protected MultiValuedMap<Integer, InputStream> newIntegerToInputStreamMultiValuedMap() {
		return MultiMapUtils.newListValuedHashMap();
	}

	/**
	 * @return a new object.
	 */
	protected Collection<Path> newPathArrayList() {
		return new ArrayList<Path>();
	}

	/**
	 * @return a new object.
	 */
	protected Collection<Collection<Path>> newPathGroupCollection() {
		return new ArrayList<Collection<Path>>();
	}

	/**
	 * @param aPath the path to open.
	 * @return a new object.
	 * @throws IOException if any is thrown by the underlying code.
	 */
	protected InputStream newPathInputStream(Path aPath) throws IOException {
		return Files.newInputStream(aPath);
	}

	/**
	 * @return a new object.
	 */
	protected Collection<Path> newPathLinkedList() {
		return new LinkedList<Path>();
	}

	/**
	 * @return a new object.
	 */
	protected FXThreadIntegerProperty newThreadSafeSimpleIntegerProperty() {
		return new FXThreadIntegerProperty(0);
	}

	/**
	 * @return a new object.
	 */
	protected FXThreadLongProperty newThreadSafeSimpleLongProperty() {
		return new FXThreadLongProperty(0);
	}

	protected Collection<Collection<Path>> nWayCompareEqualPaths(Collection<Path> pathColl) {
		Collection<Collection<Path>> retValue = newPathGroupCollection();
		Map<InputStream, Path> inputStream2Path = newInputStreamToPathMap();
		Collection<InputStream> inputStreams = newInputStreamCollection();
		Collection<Collection<InputStream>> nwcesRC;
		String printlnPrefix = phaseName.get() + ": nWayCompareEqualPaths(): ";
		Collection<Path> missingFilePaths = newPathArrayList();

		if (pathColl.size() < 2) {
			return retValue;
		}

		// assemble inputStream2Path, inputStreams and missingFilePaths
		for (Path aPath : pathColl) {

			if (isCancelled()) {
				break;
			}

			try {
				long fileLen = fileSize(aPath);
				int bisBufferSize = BIS_BUFFER_SIZE;
				InputStream is = null;

				if (fileLen < bisBufferSize) {
					bisBufferSize = (int) fileLen + 1;
				}

				is = newBufferedInputStream(newPathInputStream(aPath), bisBufferSize);
				inputStream2Path.put(is, aPath);
				inputStreams.add(is);
			} catch (NoSuchFileException e) {
				missingFilePaths.add(aPath);
			} catch (FileSystemException e) {
				missingFilePaths.add(aPath);
			} catch (IOException e) {
				showException(e);

				return retValue;
			}
		}

		if (isCancelled()) {
			return retValue;
		}

		// eliminate paths flagged as missing from the path collection
		if (!missingFilePaths.isEmpty()) {
			for (Path bPath : missingFilePaths) {
				pathColl.remove(bPath);
			}
		}

		if (pathColl.size() < 2) {
			return retValue;
		}

		// bucket the streams by equality
		nwcesRC = nWayCompareEqualStreams(inputStreams);

		if (isCancelled()) {
			return retValue;
		}

		// close the input streams and assemble the path list
		for (Collection<InputStream> isColl : nwcesRC) {
			Collection<Path> pathList = newPathLinkedList();

			if (isCancelled()) {
				break;
			}

			for (InputStream is : isColl) {
				pathList.add(inputStream2Path.get(is));
				try {
					is.close();
				} catch (IOException e) {
					showException(e);

					return retValue;
				}
			}

			retValue.add(pathList);
		}

		return retValue;
	}

	/**
	 * Compare a collection of input streams for equality.
	 * 
	 * @param collection the input streams to compare.
	 * @return a group of groups of input streams that match each other.
	 */
	protected Collection<Collection<InputStream>> nWayCompareEqualStreams(Collection<InputStream> collection) {
		Collection<Collection<InputStream>> retValue = newInputStreamGroupCollection();
		MultiValuedMap<Integer, InputStream> stepResult = newIntegerToInputStreamMultiValuedMap();
		Set<Integer> stepKeys;
		int srcSize = collection.size();
		Collection<Collection<InputStream>> recursiveResult;
		InputStream srcArray[] = collection.<InputStream>toArray(newArrayOfInputStream(collection));

		if (srcSize == 0) {
			return retValue;
		}

		if (srcSize == 1) {
			retValue.add(collection);

			return retValue;
		}

		if (isCancelled()) {
			return retValue;
		}

		// skip over the matching prefixes.

		if (streamsMatch(collection)) {
			retValue.add(collection);

//			System.out.println(printlnPrefix + "streams match - method completed");

			return retValue;
		}

		if (isCancelled()) {
			return retValue;
		}

		// OK. So they're not identical. Back up and analyze byte for byte.

		for (InputStream bis : srcArray) {
			try {
				bis.reset();
			} catch (IOException ex) {
				showException(ex);

				return retValue;
			}
		}

		while (true) {
			int theByte = 0;

			stepResult.clear();

			for (InputStream is : srcArray) {
				Integer theInteger;

				if (isCancelled()) {
					return retValue;
				}

				try {
					theByte = is.read();
				} catch (IOException ex) {
					theByte = -1;
				}

				theInteger = theByte;
				stepResult.put(theInteger, is);
			}

			if (isCancelled()) {
				return retValue;
			}

			stepKeys = stepResult.keySet();
			if (stepKeys.size() != 1) {
				break;
			}

			if (theByte < 0) {
				retValue.add(collection);

				return retValue;
			}
		}

		// if we get here, there are multiple groups in stepResult

		for (Integer key : stepResult.keySet()) {
			if (isCancelled()) {
				return retValue;
			}

			recursiveResult = nWayCompareEqualStreams(stepResult.get(key));
			retValue.addAll(recursiveResult);
		}

		return retValue;
	}

	protected void processBatch(Collection<Path> batch) throws Exception {
		int nbrPaths = batch.size();
		Path firstPath = (Path) (batch.toArray()[0]);
		long fileLen = fileSize(firstPath);
		Collection<Collection<Path>> pathCollColl = nWayCompareEqualPaths(batch);

		if (isCancelled()) {
			return;
		}

		for (Collection<Path> pathColl : pathCollColl) {
			int size = pathColl.size();

			if (isCancelled()) {
				break;
			}

			if (size > 1) {
				outputQueue.put(pathColl);
			} else {
				uniqueCount.increment(1);
			}

			filesComparedCount.increment(size);
		}

		bytesComparedCount.increment(fileLen * nbrPaths);
	}

	/**
	 * Determine whether a collection of equal-length streams contain identical
	 * data.
	 * <p>
	 * There are 2 notable side-effects: The streams are consumed to the end, if
	 * they match, and the streams are marked at suitable points at which to begin
	 * byte-by-byte comparison, if they don't match.
	 * 
	 * @param collection the collection of equal-length streams.
	 * @return the truth-value of the expression, "the streams contain identical
	 *         data".
	 */
	protected boolean streamsMatch(Collection<InputStream> collection) {
		int srcSize = collection.size();
		InputStream[] srcArray = collection.<InputStream>toArray(newInputStreamArray(srcSize));
		byte[] masterBuffer = newByteBuffer();
		boolean buffersMatch = true;

		if (collection.size() < 2) {
			return false;
		}

		do {
			int masterCount = 0;

			if (isCancelled()) {
				return false;
			}

			for (InputStream bis : srcArray) {
				bis.mark(2 * NWCES_BUFFER_SIZE);

				if (isCancelled()) {
					return false;
				}
			}

			try {
				masterCount = srcArray[0].read(masterBuffer, 0, NWCES_BUFFER_SIZE);
			} catch (IOException e) {
				buffersMatch = false;
			}

			if (masterCount < 0) {
				// this means we hit end of file.
				// we already know all the streams are the same length.
				// there has been no mismatch to this point.
				// thus, the streams have matched, entirely.

				return true;
			}

			for (int i = 1; buffersMatch && (i < srcArray.length); ++i) {
				int slaveCount = 0;
				byte[] slaveBuffer = newByteBuffer();

				if (isCancelled()) {
					return false;
				}

				try {
					slaveCount = srcArray[i].read(slaveBuffer, 0, masterCount);
					buffersMatch = (slaveCount == masterCount) && Arrays.equals(slaveBuffer, masterBuffer);
				} catch (IOException e) {
					buffersMatch = false;
				}

				if (isCancelled()) {
					return false;
				}

			}
		} while (buffersMatch);

		return false;
	}

}
