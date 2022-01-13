package cotelab.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * A {@link Phase} designed to group files by content. The input is a sequence
 * of collections of pathnames to be grouped. The output is a sequence of
 * collections of pathnames of files with matching contents.
 *
 * Outputs an empty group as an end of file mark.
 */
public class GroupByContentPhase extends Phase {
	/**
	 * The number of worker threads.
	 */
	public static final int WORKER_COUNT = 5;

	/**
	 * The number of bytes compared.
	 */
	protected ThreadSafeSimpleLongProperty bytesComparedCount = newThreadSafeSimpleLongProperty();

	/**
	 * The number of files compared.
	 */
	protected ThreadSafeSimpleIntegerProperty filesComparedCount = newThreadSafeSimpleIntegerProperty();

	/**
	 * The number of unique files identified.
	 */
	protected ThreadSafeSimpleIntegerProperty uniqueCount = newThreadSafeSimpleIntegerProperty();

	/**
	 * Construct a new object.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public GroupByContentPhase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super(name, theInput, theOutput);

		for (int i = 0; i < WORKER_COUNT; ++i) {
			GroupByContentWorker worker = newGroupByContentWorker("GroupByContentWorker " + (i + 1), theInput,
					theOutput);

			worker.getBytesComparedCount().addListener(newLongRollupListener(bytesComparedCount));
			worker.getFilesComparedCount().addListener(newIntegerRollupListener(filesComparedCount));
			worker.getUniqueCount().addListener(newIntegerRollupListener(uniqueCount));

			children.add(worker);
		}
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
		Void result = superCall();

		outputQueue.put(newPathArrayList()); // EOF convention

		return result;
	}

	/**
	 * @return a new object.
	 */
	protected GroupByContentWorker newGroupByContentWorker(String name, PipelineQueue input, PipelineQueue output) {
		return new GroupByContentWorker(name, input, output);
	}

	/**
	 * @return a new object.
	 */
	protected IntegerRollupListener newIntegerRollupListener(ThreadSafeSimpleIntegerProperty prop) {
		return new IntegerRollupListener(prop);
	}

	/**
	 * @return a new object.
	 */
	protected LongRollupListener newLongRollupListener(ThreadSafeSimpleLongProperty prop) {
		return new LongRollupListener(prop);
	}

	/**
	 * @return
	 */
	protected ArrayList<Path> newPathArrayList() {
		return new ArrayList<Path>();
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

	/**
	 * @return
	 * @throws Exception
	 */
	protected Void superCall() throws Exception {
		return super.call();
	}

}
