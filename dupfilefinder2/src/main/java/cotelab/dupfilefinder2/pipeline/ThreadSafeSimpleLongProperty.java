/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;

/**
 * A {@link SimpleLongProperty} that does its work in the JavaFX thread.
 */
public class ThreadSafeSimpleLongProperty {
	/**
	 * The backing {@link SimpleLongProperty}.
	 */
	protected final SimpleLongProperty slp;

	/**
	 * Construct a new object.
	 */
	public ThreadSafeSimpleLongProperty() {
		slp = newSimpleLongProperty();
	}

	/**
	 * Construct a new object.
	 * 
	 * @param initialValue the initial value of the property.
	 */
	public ThreadSafeSimpleLongProperty(long initialValue) {
		slp = newSimpleLongProperty(initialValue);
	}

	/**
	 * Add a change listener, which always will be called on the JavaFX thread.
	 * 
	 * @param listener a change listener.
	 * @see javafx.beans.property.LongPropertyBase#addListener(javafx.beans.value.ChangeListener)
	 */
	public void addListener(ChangeListener<? super Number> listener) {
		slp.addListener(listener);
	}

	/**
	 * Add an invalidation listener, which always will be called on the JavaFX
	 * thread.
	 * 
	 * @param listener an invalidation listener.
	 * @see javafx.beans.property.LongPropertyBase#addListener(javafx.beans.InvalidationListener)
	 */
	public void addListener(InvalidationListener listener) {
		slp.addListener(listener);
	}

//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#add(double)
//	 */
//	public DoubleBinding add(double other) {
//		return slp.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#add(float)
//	 */
//	public FloatBinding add(float other) {
//		return slp.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#add(int)
//	 */
//	public LongBinding add(int other) {
//		return slp.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#add(long)
//	 */
//	public LongBinding add(long other) {
//		return slp.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#add(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding add(ObservableNumberValue other) {
//		return slp.add(other);
//	}

	/**
	 * Get the property's value.
	 * 
	 * @return
	 * @see javafx.beans.property.LongPropertyBase#get()
	 */
	public long get() {
		// not sure how to do this in a truly thread-safe manner. take our chances.
		return slp.get();
	}

	/**
	 * Increment the property's value.
	 * 
	 * @param addend the amount by which the value is to be incremented.
	 */
	public void increment(long addend) {
		Platform.runLater(new Runnable() {
			public void run() {
				slp.set(slp.get() + addend);
			}
		});
	}

//	/**
//	 * @return
//	 * @see javafx.beans.property.LongProperty#asObject()
//	 */
//	public ObjectProperty<Long> asObject() {
//		return slp.asObject();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#asString()
//	 */
//	public StringBinding asString() {
//		return slp.asString();
//	}
//
//	/**
//	 * @param locale
//	 * @param format
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#asString(java.util.Locale,
//	 *      java.lang.String)
//	 */
//	public StringBinding asString(Locale locale, String format) {
//		return slp.asString(locale, format);
//	}
//
//	/**
//	 * @param format
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#asString(java.lang.String)
//	 */
//	public StringBinding asString(String format) {
//		return slp.asString(format);
//	}
//
//	/**
//	 * @param rawObservable
//	 * @see javafx.beans.property.LongPropertyBase#bind(javafx.beans.value.ObservableValue)
//	 */
//	public void bind(ObservableValue<? extends Number> rawObservable) {
//		slp.bind(rawObservable);
//	}
//
//	/**
//	 * @param other
//	 * @see javafx.beans.property.LongProperty#bindBidirectional(javafx.beans.property.Property)
//	 */
//	public void bindBidirectional(Property<Number> other) {
//		slp.bindBidirectional(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#divide(double)
//	 */
//	public DoubleBinding divide(double other) {
//		return slp.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#divide(float)
//	 */
//	public FloatBinding divide(float other) {
//		return slp.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#divide(int)
//	 */
//	public LongBinding divide(int other) {
//		return slp.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#divide(long)
//	 */
//	public LongBinding divide(long other) {
//		return slp.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#divide(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding divide(ObservableNumberValue other) {
//		return slp.divide(other);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#doubleValue()
//	 */
//	public double doubleValue() {
//		return slp.doubleValue();
//	}
//
//	/**
//	 * @param obj
//	 * @return
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	public boolean equals(Object obj) {
//		return slp.equals(obj);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#floatValue()
//	 */
//	public float floatValue() {
//		return slp.floatValue();
//	}

	/**
	 * Remove a change listener.
	 * 
	 * @param listener a change listener.
	 * @see javafx.beans.property.LongPropertyBase#removeListener(javafx.beans.value.ChangeListener)
	 */
	public void removeListener(ChangeListener<? super Number> listener) {
		slp.removeListener(listener);
	}

//	/**
//	 * @return
//	 * @see javafx.beans.property.SimpleLongProperty#getBean()
//	 */
//	public Object getBean() {
//		return slp.getBean();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.property.SimpleLongProperty#getName()
//	 */
//	public String getName() {
//		return slp.getName();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#getValue()
//	 */
//	public Long getValue() {
//		return slp.getValue();
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(double)
//	 */
//	public BooleanBinding greaterThan(double other) {
//		return slp.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(float)
//	 */
//	public BooleanBinding greaterThan(float other) {
//		return slp.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(int)
//	 */
//	public BooleanBinding greaterThan(int other) {
//		return slp.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(long)
//	 */
//	public BooleanBinding greaterThan(long other) {
//		return slp.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding greaterThan(ObservableNumberValue other) {
//		return slp.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(double)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(double other) {
//		return slp.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(float)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(float other) {
//		return slp.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(int)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(int other) {
//		return slp.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(long)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(long other) {
//		return slp.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(ObservableNumberValue other) {
//		return slp.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @return
//	 * @see java.lang.Object#hashCode()
//	 */
//	public int hashCode() {
//		return slp.hashCode();
//	}

	/**
	 * Remove an invalidation listener.
	 * 
	 * @param listener an invalidation listener.
	 * @see javafx.beans.property.LongPropertyBase#removeListener(javafx.beans.InvalidationListener)
	 */
	public void removeListener(InvalidationListener listener) {
		slp.removeListener(listener);
	}

//	/**
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#intValue()
//	 */
//	public int intValue() {
//		return slp.intValue();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.property.LongPropertyBase#isBound()
//	 */
//	public boolean isBound() {
//		return slp.isBound();
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(double, double)
//	 */
//	public BooleanBinding isEqualTo(double other, double epsilon) {
//		return slp.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(float, double)
//	 */
//	public BooleanBinding isEqualTo(float other, double epsilon) {
//		return slp.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(int)
//	 */
//	public BooleanBinding isEqualTo(int other) {
//		return slp.isEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(int, double)
//	 */
//	public BooleanBinding isEqualTo(int other, double epsilon) {
//		return slp.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(long)
//	 */
//	public BooleanBinding isEqualTo(long other) {
//		return slp.isEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(long, double)
//	 */
//	public BooleanBinding isEqualTo(long other, double epsilon) {
//		return slp.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding isEqualTo(ObservableNumberValue other) {
//		return slp.isEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(javafx.beans.value.ObservableNumberValue,
//	 *      double)
//	 */
//	public BooleanBinding isEqualTo(ObservableNumberValue other, double epsilon) {
//		return slp.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(double, double)
//	 */
//	public BooleanBinding isNotEqualTo(double other, double epsilon) {
//		return slp.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(float, double)
//	 */
//	public BooleanBinding isNotEqualTo(float other, double epsilon) {
//		return slp.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(int)
//	 */
//	public BooleanBinding isNotEqualTo(int other) {
//		return slp.isNotEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(int, double)
//	 */
//	public BooleanBinding isNotEqualTo(int other, double epsilon) {
//		return slp.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(long)
//	 */
//	public BooleanBinding isNotEqualTo(long other) {
//		return slp.isNotEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(long, double)
//	 */
//	public BooleanBinding isNotEqualTo(long other, double epsilon) {
//		return slp.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding isNotEqualTo(ObservableNumberValue other) {
//		return slp.isNotEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(javafx.beans.value.ObservableNumberValue,
//	 *      double)
//	 */
//	public BooleanBinding isNotEqualTo(ObservableNumberValue other, double epsilon) {
//		return slp.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(double)
//	 */
//	public BooleanBinding lessThan(double other) {
//		return slp.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(float)
//	 */
//	public BooleanBinding lessThan(float other) {
//		return slp.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(int)
//	 */
//	public BooleanBinding lessThan(int other) {
//		return slp.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(long)
//	 */
//	public BooleanBinding lessThan(long other) {
//		return slp.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding lessThan(ObservableNumberValue other) {
//		return slp.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(double)
//	 */
//	public BooleanBinding lessThanOrEqualTo(double other) {
//		return slp.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(float)
//	 */
//	public BooleanBinding lessThanOrEqualTo(float other) {
//		return slp.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(int)
//	 */
//	public BooleanBinding lessThanOrEqualTo(int other) {
//		return slp.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(long)
//	 */
//	public BooleanBinding lessThanOrEqualTo(long other) {
//		return slp.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding lessThanOrEqualTo(ObservableNumberValue other) {
//		return slp.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#longValue()
//	 */
//	public long longValue() {
//		return slp.longValue();
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#multiply(double)
//	 */
//	public DoubleBinding multiply(double other) {
//		return slp.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#multiply(float)
//	 */
//	public FloatBinding multiply(float other) {
//		return slp.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#multiply(int)
//	 */
//	public LongBinding multiply(int other) {
//		return slp.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#multiply(long)
//	 */
//	public LongBinding multiply(long other) {
//		return slp.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#multiply(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding multiply(ObservableNumberValue other) {
//		return slp.multiply(other);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#negate()
//	 */
//	public LongBinding negate() {
//		return slp.negate();
//	}

	/**
	 * Set a new value for the property.
	 * 
	 * @param newValue the new value.
	 * @see javafx.beans.property.LongPropertyBase#set(long)
	 */
	public void set(long newValue) {
		Platform.runLater(new Runnable() {
			public void run() {
				slp.set(newValue);
			}
		});
	}

	/**
	 * Set a new value for the property.
	 * 
	 * @param v the new value.
	 * @see javafx.beans.property.LongProperty#setValue(java.lang.Number)
	 */
	public void setValue(Number v) {
		Platform.runLater(new Runnable() {
			public void run() {
				slp.setValue(v);
			}
		});
	}

	/**
	 * A factory for {@link SimpleLongProperty} objects.
	 * 
	 * This trivial method helps enable unit testing.
	 * 
	 * @return the new object.
	 */
	protected SimpleLongProperty newSimpleLongProperty() {
		return new SimpleLongProperty();
	}

	/**
	 * A factory for {@link SimpleLongProperty} objects.
	 * 
	 * This trivial method helps enable unit testing.
	 * 
	 * @param initialValue the initial value of the property.
	 * @return the new object.
	 */
	protected SimpleLongProperty newSimpleLongProperty(long initialValue) {
		return new SimpleLongProperty(initialValue);
	}

//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#subtract(double)
//	 */
//	public DoubleBinding subtract(double other) {
//		return slp.subtract(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#subtract(float)
//	 */
//	public FloatBinding subtract(float other) {
//		return slp.subtract(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#subtract(int)
//	 */
//	public LongBinding subtract(int other) {
//		return slp.subtract(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.LongExpression#subtract(long)
//	 */
//	public LongBinding subtract(long other) {
//		return slp.subtract(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#subtract(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding subtract(ObservableNumberValue other) {
//		return slp.subtract(other);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.property.LongPropertyBase#toString()
//	 */
//	public String toString() {
//		return slp.toString();
//	}
//
//	/**
//	 * 
//	 * @see javafx.beans.property.LongPropertyBase#unbind()
//	 */
//	public void unbind() {
//		slp.unbind();
//	}
//
//	/**
//	 * @param other
//	 * @see javafx.beans.property.LongProperty#unbindBidirectional(javafx.beans.property.Property)
//	 */
//	public void unbindBidirectional(Property<Number> other) {
//		slp.unbindBidirectional(other);
//	}

}
