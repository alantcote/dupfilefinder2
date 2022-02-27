package io.github.alantcote.dupfilefinder2.pipeline.phase;

import io.github.alantcote.fxutilities.javafx.scene.control.ExceptionAlert;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;

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
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ExceptionAlert ea = new ExceptionAlert(AlertType.ERROR, t, "Caught Exception");

				ea.showAndWait();
			}
			
		});
	}
}
