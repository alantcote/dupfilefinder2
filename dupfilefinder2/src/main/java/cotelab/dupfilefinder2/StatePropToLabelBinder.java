/**
 * 
 */
package cotelab.dupfilefinder2;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Label;

/**
 * In reality, a {@link ChangeListener<Number>} that updates a {@link Label}
 * when a change happens. The update to the Label takes place in the JavaFX
 * thread.
 */
public class StatePropToLabelBinder implements ChangeListener<State> {
	/**
	 * The {@link FXMLController}.
	 */
	protected FXMLController controller;

	/**
	 * The label to be updated.
	 */
	protected Label label;

	/**
	 * Construct a new object.
	 * 
	 * @param aLabel         the {@link Label} to be updated when
	 *                       {@link #changed(ObservableValue, State, State)} is
	 *                       called.
	 * @param fxmlController the {@link FXMLController}.
	 */
	public StatePropToLabelBinder(Label aLabel, FXMLController fxmlController) {
		label = aLabel;
		controller = fxmlController;
	}

	/**
	 * {@inheritDoc} Update the label with the new value.
	 */
	public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
		updateInFXThread(label, newValue);
	}

	/**
	 * Update the text of a {@link Label} with a string value of a {@link State}.
	 * 
	 * @param field    the Label.
	 * @param newValue the State.
	 */
	protected void updateInFXThread(Label field, State newValue) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				field.setText(newValue.toString());

				controller.updateElapsedTime();
			}

		});
	}

}
