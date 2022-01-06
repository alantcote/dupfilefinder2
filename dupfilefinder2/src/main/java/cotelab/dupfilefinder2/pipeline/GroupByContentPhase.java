/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A {@link Phase} designed to group files by content. The input is a sequence
 * of collections of pathnames to be grouped. The output is a sequence of
 * collections of pathnames of files with matching contents.
 *
 * Outputs an empty group as an end of file mark.
 * 
 * @author alantcote
 *
 */
public class GroupByContentPhase extends Phase {
	public static final int WORKER_COUNT = 5;

	protected ThreadSafeSimpleLongProperty bytesComparedCount = new ThreadSafeSimpleLongProperty(0);
	protected ThreadSafeSimpleIntegerProperty filesComparedCount = new ThreadSafeSimpleIntegerProperty(0);
	protected ThreadSafeSimpleIntegerProperty uniqueCount = new ThreadSafeSimpleIntegerProperty(0);
	protected GroupByContentWorker workers[] = new GroupByContentWorker[WORKER_COUNT];

	/**
	 * Construct a new object.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public GroupByContentPhase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super(name, theInput, theOutput);

		System.out.println("GroupByContentPhase.GroupByContentPhase(): creating workers");

		for (int i = 0; i < WORKER_COUNT; ++i) {
			workers[i] = new GroupByContentWorker("GroupByContentWorker " + (i + 1), theInput, theOutput);

			workers[i].getBytesComparedCount().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					long rollup = 0;

					for (GroupByContentWorker worker : workers) {
						rollup += worker.getBytesComparedCount().get();
					}

					bytesComparedCount.set(rollup);
				}

			});

			workers[i].getFilesComparedCount().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					int rollup = 0;

					for (GroupByContentWorker worker : workers) {
						rollup += worker.getFilesComparedCount().get();
					}

					filesComparedCount.set(rollup);
				}

			});

			workers[i].getUniqueCount().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					int rollup = 0;

					for (GroupByContentWorker worker : workers) {
						rollup += worker.getUniqueCount().get();
					}

					uniqueCount.set(rollup);
				}

			});

			children.add(workers[i]);
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

	@Override
	protected Void call() throws Exception {
		Void result = super.call();
		
		outputQueue.put(new ArrayList<Path>());
		
		return result;
	}

}
