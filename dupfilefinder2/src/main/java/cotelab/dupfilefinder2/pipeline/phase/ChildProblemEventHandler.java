package cotelab.dupfilefinder2.pipeline.phase;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 * An event handler for worker state changes.
 */
public class ChildProblemEventHandler implements EventHandler<WorkerStateEvent> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(WorkerStateEvent event) {
		// For testability, we'd like some of the following to be extracted to a
		// <code>newThrowable()</code> method, but that would defeat the purpose of
		// getting a clean stack trace.
		Throwable t = new Throwable();

		System.err.println("Phase.ChildProblemEventHandler.handle(): handling " + event);
		t.printStackTrace();
	}
}
