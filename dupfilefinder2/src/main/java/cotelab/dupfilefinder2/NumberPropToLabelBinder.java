/**
 * 
 */
package cotelab.dupfilefinder2;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

/**
 * In reality, a {@link ChangeListener<Number>} that updates a {@link Label}
 * when a change happens. The update to the Label takes place in the JavaFX
 * thread.
 */
public class NumberPropToLabelBinder implements ChangeListener<Number> {
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
	 * @param aLabel the {@link Label} to be updated when
	 *               {@link #changed(ObservableValue, Number, Number)} is called.
	 * @param fxmlController the {@link FXMLController}.
	 */
	public NumberPropToLabelBinder(Label aLabel, FXMLController fxmlController) {
		label = aLabel;
		controller = fxmlController;
	}

	/**
	 * {@inheritDoc} Update the label with the new value.
	 */
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		updateInFXThread(label, newValue);
	}

	/**
	 * Update the text of a {@link Label} with a string value of a {@link Number}.
	 * 
	 * @param field    the Label.
	 * @param newValue the Number.
	 */
	protected void updateInFXThread(Label field, Number newValue) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				field.setText(newValue.toString());

				controller.updateElapsedTime();
			}

		});
	}

}
