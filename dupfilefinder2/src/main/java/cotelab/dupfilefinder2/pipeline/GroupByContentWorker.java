/**
 * 
 */
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
import java.util.List;
import java.util.Set;

import net.sf.cotelab.util.collections.HashMultiMap;

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
	protected ThreadSafeSimpleLongProperty bytesComparedCount = newThreadSafeSimpleLongProperty();

	/**
	 * The number of files compared.
	 */
	protected ThreadSafeSimpleIntegerProperty filesComparedCount = newThreadSafeSimpleIntegerProperty();

	/**
	 * The number of unique files found.
	 */
	protected ThreadSafeSimpleIntegerProperty uniqueCount = newThreadSafeSimpleIntegerProperty();

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
	public ThreadSafeSimpleLongProperty getBytesComparedCount() {
		return bytesComparedCount;
	}

	/**
	 * @return the filesComparedCount
	 */
	public ThreadSafeSimpleIntegerProperty getFilesComparedCount() {
		return filesComparedCount;
	}

	/**
	 * @return the uniqueCount
	 */
	public ThreadSafeSimpleIntegerProperty getUniqueCount() {
		return uniqueCount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Void call() throws Exception {
		// TODO refactor for readability and testability
		try {
//			String printlnPrefix = phaseName.get() + ": call(): ";

//			System.out.println(printlnPrefix + "taking batch from " + inputQueue.getName().get());

			Collection<Path> batch = inputQueue.take();

			while (!isCancelled() && (batch != null) && !batch.isEmpty()) { // EOF convention
				int nbrPaths = batch.size();
				Path firstPath = (Path) (batch.toArray()[0]);
				long fileLen = 1;

				try {
					fileLen = Files.size(firstPath);
				} catch (NoSuchFileException e) {
					if (nbrPaths < 3) {
						return null;
					} else {
						batch.remove(firstPath);
					}
				}

//				System.out.println(printlnPrefix + "processing " + nbrPaths + " files @ " + fileLen + " bytes");

//				System.out.println(printlnPrefix + "calling nWayCompareEqualPaths(batch)");

				Collection<Collection<Path>> pathCollColl = nWayCompareEqualPaths(batch);

				if (isCancelled()) {
					break;
				}

//				System.out.println(printlnPrefix + "producing results");

				for (Collection<Path> pathColl : pathCollColl) {
					int size = pathColl.size();

					if (isCancelled()) {
						break;
					}

					if (size >= 2) {
						outputQueue.put(pathColl);
					} else {
						uniqueCount.increment(1);
					}
				}

				filesComparedCount.increment(nbrPaths);
				bytesComparedCount.increment(fileLen * nbrPaths);

				if (isCancelled()) {
					break;
				}

//				System.out.println(printlnPrefix + "polling for new batch");

				batch = inputQueue.poll();
			}
		} catch (Exception e) {
			System.err.println("GroupByContentWorker.call: caught: " + e.getMessage());
			e.printStackTrace();
		}

//		System.out.println(printlnPrefix + "method completed");

		return null;
	}

	/**
	 * @param aPath      the path to open.
	 * @param bufferSize the buffer size.
	 * @return a new object.
	 * @throws IOException if thrown by the underlying code.
	 */
	protected BufferedInputStream newBufferedInputStream(Path aPath, int bufferSize) throws IOException {
		return new BufferedInputStream(Files.newInputStream(aPath), bufferSize);
	}

	/**
	 * @param src a group of input streams, the count of which will be the array
	 *            dimension.
	 * @return a new object.
	 */
	protected BufferedInputStream[] newBufferedInputStreamArray(Collection<BufferedInputStream> src) {
		return new BufferedInputStream[src.size()];
	}

	/**
	 * @param size the dimension of the array.
	 * @return a new object.
	 */
	protected BufferedInputStream[] newBufferedInputStreamArray(int size) {
		return new BufferedInputStream[size];
	}

	/**
	 * @return a new object.
	 */
	protected ArrayList<BufferedInputStream> newBufferedInputStreamArrayList() {
		return new ArrayList<BufferedInputStream>();
	}

	/**
	 * @return a new object.
	 */
	protected ArrayList<Collection<BufferedInputStream>> newBufferedInputStreamGroupArrayList() {
		return new ArrayList<Collection<BufferedInputStream>>();
	}

	/**
	 * @return a new object.
	 */
	protected HashMap<BufferedInputStream, Path> newBufferedInputStreamToPathHashMap() {
		return new HashMap<BufferedInputStream, Path>();
	}

	/**
	 * @return a new object.
	 */
	protected byte[] newByteBuffer() {
		return new byte[NWCES_BUFFER_SIZE];
	}

	/**
	 * @return a new object.
	 */
	protected HashMultiMap<Integer, BufferedInputStream> newIntegerToBufferedInputStreamHashMultiMap() {
		return new HashMultiMap<Integer, BufferedInputStream>();
	}

	/**
	 * @return a new object.
	 */
	protected ArrayList<Path> newPathArrayList() {
		return new ArrayList<Path>();
	}

	/**
	 * @return a new object.
	 */
	protected ArrayList<Collection<Path>> newPathGroupArrayList() {
		return new ArrayList<Collection<Path>>();
	}

	/**
	 * @return a new object.
	 */
	protected LinkedList<Path> newPathLinkedList() {
		return new LinkedList<Path>();
	}

	/**
	 * @return a new object.
	 */
	protected ThreadSafeSimpleIntegerProperty newThreadSafeSimpleIntegerProperty() {
		return new ThreadSafeSimpleIntegerProperty(0);
	}

	/**
	 * @return a new object.
	 */
	protected ThreadSafeSimpleLongProperty newThreadSafeSimpleLongProperty() {
		return new ThreadSafeSimpleLongProperty(0);
	}

	protected Collection<Collection<Path>> nWayCompareEqualPaths(Collection<Path> pathColl) {
		// TODO refactor for readability and testability
		List<Collection<Path>> retValue = newPathGroupArrayList();
		HashMap<BufferedInputStream, Path> inputStream2Path = newBufferedInputStreamToPathHashMap();
		ArrayList<BufferedInputStream> inputStreams = newBufferedInputStreamArrayList();
		Collection<Collection<BufferedInputStream>> nwcesRC;
		String printlnPrefix = phaseName.get() + ": nWayCompareEqualPaths(): ";
		ArrayList<Path> missingFilePaths = newPathArrayList();

//		System.out.println(printlnPrefix + "opening files");

		for (Path aPath : pathColl) {

			if (isCancelled()) {
				break;
			}

			try {
				long fileLen = Files.size(aPath);
				int bisBufferSize = BIS_BUFFER_SIZE;
				BufferedInputStream is = null;

				if (fileLen < bisBufferSize) {
					bisBufferSize = (int) fileLen + 1;
				}

//				is = new BufferedInputStream(new FileInputStream(aPath.toFile()), bisBufferSize);
				is = newBufferedInputStream(aPath, bisBufferSize);
				inputStream2Path.put(is, aPath);
				inputStreams.add(is);
			} catch (NoSuchFileException e) {
//              output.getUnreadableFilesIdentified().add(aPath);
//              output.getRegularFiles().remove(aPath);
				missingFilePaths.add(aPath);
//				System.err.println(printlnPrefix + "caught: " + e.getMessage());
//				e.printStackTrace();
			} catch (FileSystemException e) {
				missingFilePaths.add(aPath);
			} catch (IOException e) {
				System.err.println(printlnPrefix + "caught: " + e.getMessage());
				e.printStackTrace();

				return retValue;
			}
		}

		if (isCancelled()) {
			return retValue;
		}

		if (!missingFilePaths.isEmpty()) {
			for (Path bPath : missingFilePaths) {
				pathColl.remove(bPath);
			}
		}

		if (pathColl.size() < 2) {
			return retValue;
		}

//		System.out.println(printlnPrefix + "calling nWayCompareEqualStreams()");

//        nwcesRC = Streams.nWayCompareEqualStreams(inputStreams);
		nwcesRC = nWayCompareEqualStreams(inputStreams);

		if (isCancelled()) {
			return retValue;
		}

		for (Collection<BufferedInputStream> isColl : nwcesRC) {
			LinkedList<Path> pathList = newPathLinkedList();

			if (isCancelled()) {
				break;
			}

			for (InputStream is : isColl) {
				pathList.add(inputStream2Path.get(is));
				try {
					is.close();
				} catch (IOException e) {
					System.err.println(printlnPrefix + "caught");
					e.printStackTrace();

					return retValue;
				}
			}

			retValue.add(pathList);
		}

//		System.out.println(printlnPrefix + "method completed");

		return retValue;
	}

	/**
	 * Compare a collection of input streams for equality.
	 * 
	 * @param src the input streams to compare.
	 * @return a group of groups of input streams that match each other.
	 */
	protected Collection<Collection<BufferedInputStream>> nWayCompareEqualStreams(Collection<BufferedInputStream> src) {
		// TODO refactor for readability and testability
		ArrayList<Collection<BufferedInputStream>> retValue = newBufferedInputStreamGroupArrayList();
		HashMultiMap<Integer, BufferedInputStream> stepResult = newIntegerToBufferedInputStreamHashMultiMap();
		Set<Integer> stepKeys;
		int srcSize = src.size();
		Collection<Collection<BufferedInputStream>> recursiveResult;
		BufferedInputStream srcArray[] = src.<BufferedInputStream>toArray(newBufferedInputStreamArray(src));
//		String printlnPrefix = phaseName.get() + ": nWayCompareEqualStreams(): ";

		if (srcSize == 0) {
			return retValue;
		}

		if (srcSize == 1) {
			retValue.add(src);

			return retValue;
		}

		if (isCancelled()) {
			return retValue;
		}

		// skip over the matching prefixes.

//		System.out.println(printlnPrefix + "calling streamsMatch()");

		if (streamsMatch(src)) {
			retValue.add(src);

//			System.out.println(printlnPrefix + "streams match - method completed");

			return retValue;
		}

		if (isCancelled()) {
			return retValue;
		}

		// OK. So they're not identical. Back up and analyze byte for byte.

//		System.out.println(printlnPrefix + "resetting streams");

		for (BufferedInputStream bis : srcArray) {
			try {
				bis.reset();
			} catch (IOException ex) {
//                Logger.getLogger(Streams.class.getName()).log(
//                        Level.SEVERE, null, ex);
				System.err.println(phaseName.get() + ": nWayCompareEqualStreams(): caught ...");
				ex.printStackTrace();

				return retValue;
			}
		}

//		System.out.println(printlnPrefix + "comparing byte by byte");

		while (true) {
			int theByte = 0;

			stepResult.clear();

			for (BufferedInputStream is : srcArray) {
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
				retValue.add(src);

				return retValue;
			}
		}

		// if we get here, there are multiple groups in stepResult

//		System.out.println(printlnPrefix + "comparing byte by byte split the groups");

		for (Integer key : stepResult.keySet()) {
			if (isCancelled()) {
				return retValue;
			}

//			System.out.println(printlnPrefix + "recursing");
			recursiveResult = nWayCompareEqualStreams(stepResult.get(key));
			retValue.addAll(recursiveResult);
		}

//		System.out.println(printlnPrefix + "method completed");

		return retValue;
	}

	/**
	 * Determine whether a collection of equal-length streams contain identical
	 * data.
	 * <p>
	 * There are 2 notable side-effects: The streams are consumed to the end, if
	 * they match, and the streams are marked at suitable points at which to begin
	 * byte-by-byte comparison, if they don't match.
	 * 
	 * @param src the collection of equal-length streams.
	 * @return the truth-value of the expression, "the streams contain identical
	 *         data".
	 */
	protected boolean streamsMatch(Collection<BufferedInputStream> src) {
		// TODO refactor for readability and testability
		int srcSize = src.size();
		BufferedInputStream srcArray[] = src.<BufferedInputStream>toArray(newBufferedInputStreamArray(srcSize));
		byte[] masterBuffer = newByteBuffer();
		boolean buffersMatch = true;
//		String printlnPrefix = phaseName.get() + ": streamsMatch(): ";

//		System.out.println(printlnPrefix + "entry");

		do {
			int masterCount = 0;

			if (isCancelled()) {
				return false;
			}

			for (BufferedInputStream bis : srcArray) {
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

//				System.out.println(printlnPrefix + "method completed - streams matched");

				return true;
			}

//			System.out.println(printlnPrefix + "checking a piece");
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

//		System.out.println(printlnPrefix + "method completed - streams did not match");

		return false;
	}

}
