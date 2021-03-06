package io.github.alantcote.dupfilefinder2.beans.value;

import io.github.alantcote.dupfilefinder2.beans.property.FXThreadIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A change listener that rolls up the change to an integer property.
 */
public class IntegerRollupListener implements ChangeListener<Number> {
	/**
	 * The property to update.
	 */
	protected FXThreadIntegerProperty prop;

	/**
	 * Construct a new object.
	 * 
	 * @param aProp the property to update.
	 */
	public IntegerRollupListener(FXThreadIntegerProperty aProp) {
		prop = aProp;
	}

	/**
	 * {@inheritDoc} This method increments the property by the difference between
	 * new and old values.
	 */
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		prop.increment(newValue.intValue() - oldValue.intValue());
	}
}
