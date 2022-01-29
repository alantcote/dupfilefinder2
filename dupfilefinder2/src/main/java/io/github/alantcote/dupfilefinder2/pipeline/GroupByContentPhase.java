package io.github.alantcote.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.ArrayList;

import io.github.alantcote.dupfilefinder2.beans.property.FXThreadIntegerProperty;
import io.github.alantcote.dupfilefinder2.beans.property.FXThreadLongProperty;
import io.github.alantcote.dupfilefinder2.beans.value.IntegerRollupListener;
import io.github.alantcote.dupfilefinder2.beans.value.LongRollupListener;
import io.github.alantcote.dupfilefinder2.pipeline.phase.Phase;
import io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

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
	protected FXThreadLongProperty bytesComparedCount = newThreadSafeSimpleLongProperty();

	/**
	 * The number of files compared.
	 */
	protected FXThreadIntegerProperty filesComparedCount = newThreadSafeSimpleIntegerProperty();

	/**
	 * The number of unique files identified.
	 */
	protected FXThreadIntegerProperty uniqueCount = newThreadSafeSimpleIntegerProperty();

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
	protected IntegerRollupListener newIntegerRollupListener(FXThreadIntegerProperty prop) {
		return new IntegerRollupListener(prop);
	}

	/**
	 * @return a new object.
	 */
	protected LongRollupListener newLongRollupListener(FXThreadLongProperty prop) {
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
	protected FXThreadIntegerProperty newThreadSafeSimpleIntegerProperty() {
		return new FXThreadIntegerProperty(0);
	}

	/**
	 * @return a new object.
	 */
	protected FXThreadLongProperty newThreadSafeSimpleLongProperty() {
		return new FXThreadLongProperty(0);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected Void superCall() throws Exception {
		return super.call();
	}

}
