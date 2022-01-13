package cotelab.dupfilefinder2.pipeline;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A change listener that rolls up the change to an integer property.
 */
public class IntegerRollupListener implements ChangeListener<Number> {
	/**
	 * The property to update.
	 */
	protected ThreadSafeSimpleIntegerProperty prop;

	/**
	 * Construct a new object.
	 * 
	 * @param aProp the property to update.
	 */
	public IntegerRollupListener(ThreadSafeSimpleIntegerProperty aProp) {
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
