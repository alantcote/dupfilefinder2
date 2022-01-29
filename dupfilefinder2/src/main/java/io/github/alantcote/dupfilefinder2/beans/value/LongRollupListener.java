package io.github.alantcote.dupfilefinder2.beans.value;

import io.github.alantcote.dupfilefinder2.beans.property.FXThreadLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A change listener that rolls up the change to a long property.
 */
public class LongRollupListener implements ChangeListener<Number> {
	/**
	 * The property to update.
	 */
	protected FXThreadLongProperty prop;

	/**
	 * Construct a new object.
	 * 
	 * @param prop2 the property to update.
	 */
	public LongRollupListener(FXThreadLongProperty prop2) {
		prop = prop2;
	}

	/**
	 * {@inheritDoc} This method increments the property by the difference between
	 * new and old values.
	 */
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		prop.increment(newValue.longValue() - oldValue.longValue());
	}
}
