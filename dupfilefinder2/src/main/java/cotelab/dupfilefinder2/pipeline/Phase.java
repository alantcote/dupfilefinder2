/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

/**
 * A {@link Task} that may have child tasks that it manages.
 * 
 * A tree of Phase objects may be created and managed as one.
 */
public abstract class Phase extends Task<Void> implements QueueProcessor {

	/**
	 * The handler of problems in child Phases.
	 */
	protected ChildProblemEventHandler childProblemEventHandler;

	/**
	 * The child Phases.
	 */
	protected final ArrayList<Phase> children = newPhaseArrayList();

	/**
	 * The input queue.
	 */
	protected final PipelineQueue inputQueue;

	/**
	 * The output queue.
	 */
	protected final PipelineQueue outputQueue;

	/**
	 * The name of this object.
	 */
	protected final SimpleStringProperty phaseName = new SimpleStringProperty(null);

	/**
	 * Construct a new object.
	 * 
	 * If the new object is to have managed child phases, they should be added to
	 * {@link #children} in the constructor.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public Phase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super();

		phaseName.set(name);
		inputQueue = theInput;
		outputQueue = theOutput;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		boolean success = true;
		ArrayList<Phase> livingKids = newPhaseArrayList();

		System.err.println("Phase.cancel(): cancel entered for " + phaseName.get());
		Throwable throwable = new Throwable();

		throwable.printStackTrace();

		for (Phase child : children) {
			if (!child.isDone()) {
				livingKids.add(child);
			}
		}

		for (Phase child : livingKids) {
			if ((!child.isCancelled()) && (!child.isDone())) {
				System.out.println("Phase.cancel(): cancelling " + child.phaseName.get());

				success = success && child.cancel(mayInterruptIfRunning);

				System.out.println("Phase.cancel(): cancelling " + child.phaseName.get() + ": " + success);
			}
		}

		if ((!isCancelled()) && (!isDone())) {
			System.out.println("Phase.cancel(): cancelling " + phaseName.get());

			success = success && super.cancel(mayInterruptIfRunning);

			System.out.println("Phase.cancel(): cancelling " + phaseName.get() + ": " + success);
		}

		return success;
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getInputName() {
		return inputQueue.getName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getInputPutCount() {
		return inputQueue.getPutCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PipelineQueue getInputQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getInputTakeCount() {
		return inputQueue.getTakeCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getOutputName() {
		return outputQueue.getName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getOutputPutCount() {
		return outputQueue.getPutCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PipelineQueue getOutputQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getOutputTakeCount() {
		return outputQueue.getTakeCount();
	}

	/**
	 * @return the property.
	 */
	public SimpleStringProperty getPhaseName() {
		return phaseName;
	}

	/**
	 * {@inheritDoc} This is a no-go for this class; changing queues while in
	 * operation could confuse things.
	 */
	@Override
	public void setInputQueue(PipelineQueue queue) {
		throw new UnsupportedOperationException("This class does not support this operation");
	}

	/**
	 * {@inheritDoc} This is a no-go for this class; changing queues while in
	 * operation could confuse things.
	 */
	@Override
	public void setOutputQueue(PipelineQueue queue) {
		throw new UnsupportedOperationException("This class does not support this operation");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Void call() throws Exception {
		try {
//			System.out.println("Phase.call(): starting child phases");

			startChildren();

//			System.out.println("Phase.call(): waiting for child phases to end");

			waitForChildrenDone();
		} catch (Exception e) {
			System.err.println("Phase.call(): caught" + e.getMessage());
			e.printStackTrace();
			cancel();
		}

//		System.out.println("Phase.call(): method complete");

		return null;
	}

	/**
	 * @return a new object.
	 */
	protected ArrayList<Phase> newPhaseArrayList() {
		return new ArrayList<Phase>();
	}

	/**
	 * Start up the children.
	 */
	protected void startChildren() {
		if (children.size() != 0) {
			childProblemEventHandler = new ChildProblemEventHandler();

			for (Phase child : children) {
				child.setOnCancelled(childProblemEventHandler);
				child.setOnFailed(childProblemEventHandler);
			}

			for (Phase child : children) {
				Thread th = new Thread(child);

				th.setDaemon(true);

//				System.out.println("Phase.startChildren(): starting child " + child.getPhaseName().get());

				th.start();
			}
		}
	}

	/**
	 * Wait for the children to end, whether by cancellation, failure, or success.
	 */
	protected void waitForChildrenDone() {
		ArrayList<Phase> livingKids = newPhaseArrayList();

		for (Phase child : children) {
			if (!child.isDone()) {
				livingKids.add(child);
			}
		}

		do {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}

			livingKids.clear();

			for (Phase child : children) {
				if (!child.isDone()) {
					livingKids.add(child);
				}
			}
		} while (livingKids.size() > 0);
	}
}
