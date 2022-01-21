package cotelab.dupfilefinder2.beans.property;

import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;

/**
 * A {@link SimpleLongProperty} that only has its value mutated in the JavaFX
 * application thread.
 * 
 * This mutator override behavior is intended to ensure that observers are
 * always notified on the JavaFX application thread. The exception to this is
 * when an instance of this class is bound to dependencies.
 */
public class FXThreadLongProperty extends SimpleLongProperty {

	/**
	 * @param initialValue the initial value of the wrapped value
	 */
	public FXThreadLongProperty(long initialValue) {
		super(initialValue);
	}

	/**
	 * @param bean the bean of this property
	 * @param name the name of this property
	 */
	public FXThreadLongProperty(Object bean, String name) {
		super(bean, name);
	}

	/**
	 * @param bean         the bean of this property
	 * @param name         the name of this property
	 * @param initialValue the initial value of the wrapped value
	 */
	public FXThreadLongProperty(Object bean, String name, long initialValue) {
		super(bean, name, initialValue);
	}

	/**
	 * Increment the property's value.
	 * 
	 * @param addend the amount by which to increment.
	 */
	public void increment(long addend) {
		if (Platform.isFxApplicationThread()) {
			superSet(get() + addend);
		} else {
			Platform.runLater(new Runnable() {
				public void run() {
					superSet(get() + addend);
				}
			});
		}
	}

	/**
	 * {@inheritDoc} Implemented to do its work on the JavaFX application thread.
	 */
	@Override
	public void set(long newValue) {
		if (Platform.isFxApplicationThread()) {
			superSet(newValue);
		} else {
			Platform.runLater(new Runnable() {
				public void run() {
					superSet(newValue);
				}
			});
		}
	}

	/**
	 * {@inheritDoc} Implemented to do its work on the JavaFX application thread.
	 */
	@Override
	public void setValue(Number v) {
		if (Platform.isFxApplicationThread()) {
			superSetValue(v);
		} else {
			Platform.runLater(new Runnable() {
				public void run() {
					superSetValue(v);
				}
			});
		}
	}

	/**
	 * Call <code>super.set(newValue)</code>.
	 * 
	 * @param newValue the new value for the property value
	 */
	private void superSet(long newValue) {
		super.set(newValue);
	}

	/**
	 * Call <code>super.setValue(v)</code>.
	 * 
	 * @param v the new value for the property value
	 */
	private void superSetValue(Number v) {
		super.setValue(v);
	}
}
