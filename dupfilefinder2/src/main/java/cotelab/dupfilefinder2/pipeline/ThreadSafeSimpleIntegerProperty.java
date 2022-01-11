/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

/**
 * A {@link SimpleIntegerProperty} that does its work in the JavaFX thread.
 */
public class ThreadSafeSimpleIntegerProperty {
	/**
	 * The decorated {@link SimpleIntegerProperty}.
	 */
	protected final SimpleIntegerProperty sip;

	/**
	 * Construct a new object.
	 */
	public ThreadSafeSimpleIntegerProperty() {
		sip = newSimpleIntegerProperty();
	}

	/**
	 * Construct a new object.
	 * 
	 * @param initialValue the initial value.
	 */
	public ThreadSafeSimpleIntegerProperty(int initialValue) {
		sip = newSimpleIntegerProperty(initialValue);
	}

	/**
	 * Add a change listener.
	 * 
	 * @param listener a change listener.
	 * @see javafx.beans.property.IntegerPropertyBase#addListener(javafx.beans.value.ChangeListener)
	 */
	public void addListener(ChangeListener<? super Number> listener) {
		sip.addListener(listener);
	}

	/**
	 * Add an invalidation listener.
	 * 
	 * @param listener an invalidation listener.
	 * @see javafx.beans.property.IntegerPropertyBase#addListener(javafx.beans.InvalidationListener)
	 */
	public void addListener(InvalidationListener listener) {
		sip.addListener(listener);
	}

//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#add(double)
//	 */
//	public DoubleBinding add(double other) {
//		return sip.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#add(float)
//	 */
//	public FloatBinding add(float other) {
//		return sip.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#add(int)
//	 */
//	public IntegerBinding add(int other) {
//		return sip.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#add(long)
//	 */
//	public LongBinding add(long other) {
//		return sip.add(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#add(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding add(ObservableNumberValue other) {
//		return sip.add(other);
//	}

	/**
	 * Get the property's value.
	 * 
	 * @return the property's value.
	 * @see javafx.beans.property.IntegerPropertyBase#get()
	 */
	public int get() {
		// not sure how to do this in a truly thread-safe manner. take our chances.
		return sip.get();
	}

	/**
	 * Increment the property's value.
	 * 
	 * @param addend the amount by which to increment.
	 */
	public void increment(int addend) {
		Platform.runLater(new Runnable() {
			public void run() {
				sip.set(sip.get() + addend);
			}
		});
	}

//	/**
//	 * @return
//	 * @see javafx.beans.property.IntegerProperty#asObject()
//	 */
//	public ObjectProperty<Integer> asObject() {
//		return sip.asObject();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#asString()
//	 */
//	public StringBinding asString() {
//		return sip.asString();
//	}
//
//	/**
//	 * @param locale
//	 * @param format
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#asString(java.util.Locale, java.lang.String)
//	 */
//	public StringBinding asString(Locale locale, String format) {
//		return sip.asString(locale, format);
//	}
//
//	/**
//	 * @param format
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#asString(java.lang.String)
//	 */
//	public StringBinding asString(String format) {
//		return sip.asString(format);
//	}
//
//	/**
//	 * @param rawObservable
//	 * @see javafx.beans.property.IntegerPropertyBase#bind(javafx.beans.value.ObservableValue)
//	 */
//	public void bind(ObservableValue<? extends Number> rawObservable) {
//		sip.bind(rawObservable);
//	}
//
//	/**
//	 * @param other
//	 * @see javafx.beans.property.IntegerProperty#bindBidirectional(javafx.beans.property.Property)
//	 */
//	public void bindBidirectional(Property<Number> other) {
//		sip.bindBidirectional(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#divide(double)
//	 */
//	public DoubleBinding divide(double other) {
//		return sip.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#divide(float)
//	 */
//	public FloatBinding divide(float other) {
//		return sip.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#divide(int)
//	 */
//	public IntegerBinding divide(int other) {
//		return sip.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#divide(long)
//	 */
//	public LongBinding divide(long other) {
//		return sip.divide(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#divide(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding divide(ObservableNumberValue other) {
//		return sip.divide(other);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#doubleValue()
//	 */
//	public double doubleValue() {
//		return sip.doubleValue();
//	}
//
//	/**
//	 * @param obj
//	 * @return
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	public boolean equals(Object obj) {
//		return sip.equals(obj);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#floatValue()
//	 */
//	public float floatValue() {
//		return sip.floatValue();
//	}

	/**
	 * Remove a change listener.
	 * 
	 * @param listener a change listener.
	 * @see javafx.beans.property.IntegerPropertyBase#removeListener(javafx.beans.value.ChangeListener)
	 */
	public void removeListener(ChangeListener<? super Number> listener) {
		sip.removeListener(listener);
	}

//	/**
//	 * @return
//	 * @see javafx.beans.property.SimpleIntegerProperty#getBean()
//	 */
//	public Object getBean() {
//		return sip.getBean();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.property.SimpleIntegerProperty#getName()
//	 */
//	public String getName() {
//		return sip.getName();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#getValue()
//	 */
//	public Integer getValue() {
//		return sip.getValue();
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(double)
//	 */
//	public BooleanBinding greaterThan(double other) {
//		return sip.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(float)
//	 */
//	public BooleanBinding greaterThan(float other) {
//		return sip.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(int)
//	 */
//	public BooleanBinding greaterThan(int other) {
//		return sip.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(long)
//	 */
//	public BooleanBinding greaterThan(long other) {
//		return sip.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThan(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding greaterThan(ObservableNumberValue other) {
//		return sip.greaterThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(double)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(double other) {
//		return sip.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(float)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(float other) {
//		return sip.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(int)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(int other) {
//		return sip.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(long)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(long other) {
//		return sip.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#greaterThanOrEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding greaterThanOrEqualTo(ObservableNumberValue other) {
//		return sip.greaterThanOrEqualTo(other);
//	}
//
//	/**
//	 * @return
//	 * @see java.lang.Object#hashCode()
//	 */
//	public int hashCode() {
//		return sip.hashCode();
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#intValue()
//	 */
//	public int intValue() {
//		return sip.intValue();
//	}

	/**
	 * Remove an invalidation listener.
	 * 
	 * @param listener an invalidation listener.
	 * @see javafx.beans.property.IntegerPropertyBase#removeListener(javafx.beans.InvalidationListener)
	 */
	public void removeListener(InvalidationListener listener) {
		sip.removeListener(listener);
	}

//	/**
//	 * @return
//	 * @see javafx.beans.property.IntegerPropertyBase#isBound()
//	 */
//	public boolean isBound() {
//		return sip.isBound();
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(double, double)
//	 */
//	public BooleanBinding isEqualTo(double other, double epsilon) {
//		return sip.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(float, double)
//	 */
//	public BooleanBinding isEqualTo(float other, double epsilon) {
//		return sip.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(int)
//	 */
//	public BooleanBinding isEqualTo(int other) {
//		return sip.isEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(int, double)
//	 */
//	public BooleanBinding isEqualTo(int other, double epsilon) {
//		return sip.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(long)
//	 */
//	public BooleanBinding isEqualTo(long other) {
//		return sip.isEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(long, double)
//	 */
//	public BooleanBinding isEqualTo(long other, double epsilon) {
//		return sip.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding isEqualTo(ObservableNumberValue other) {
//		return sip.isEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isEqualTo(javafx.beans.value.ObservableNumberValue, double)
//	 */
//	public BooleanBinding isEqualTo(ObservableNumberValue other, double epsilon) {
//		return sip.isEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(double, double)
//	 */
//	public BooleanBinding isNotEqualTo(double other, double epsilon) {
//		return sip.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(float, double)
//	 */
//	public BooleanBinding isNotEqualTo(float other, double epsilon) {
//		return sip.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(int)
//	 */
//	public BooleanBinding isNotEqualTo(int other) {
//		return sip.isNotEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(int, double)
//	 */
//	public BooleanBinding isNotEqualTo(int other, double epsilon) {
//		return sip.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(long)
//	 */
//	public BooleanBinding isNotEqualTo(long other) {
//		return sip.isNotEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(long, double)
//	 */
//	public BooleanBinding isNotEqualTo(long other, double epsilon) {
//		return sip.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding isNotEqualTo(ObservableNumberValue other) {
//		return sip.isNotEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @param epsilon
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#isNotEqualTo(javafx.beans.value.ObservableNumberValue, double)
//	 */
//	public BooleanBinding isNotEqualTo(ObservableNumberValue other, double epsilon) {
//		return sip.isNotEqualTo(other, epsilon);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(double)
//	 */
//	public BooleanBinding lessThan(double other) {
//		return sip.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(float)
//	 */
//	public BooleanBinding lessThan(float other) {
//		return sip.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(int)
//	 */
//	public BooleanBinding lessThan(int other) {
//		return sip.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(long)
//	 */
//	public BooleanBinding lessThan(long other) {
//		return sip.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThan(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding lessThan(ObservableNumberValue other) {
//		return sip.lessThan(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(double)
//	 */
//	public BooleanBinding lessThanOrEqualTo(double other) {
//		return sip.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(float)
//	 */
//	public BooleanBinding lessThanOrEqualTo(float other) {
//		return sip.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(int)
//	 */
//	public BooleanBinding lessThanOrEqualTo(int other) {
//		return sip.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(long)
//	 */
//	public BooleanBinding lessThanOrEqualTo(long other) {
//		return sip.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#lessThanOrEqualTo(javafx.beans.value.ObservableNumberValue)
//	 */
//	public BooleanBinding lessThanOrEqualTo(ObservableNumberValue other) {
//		return sip.lessThanOrEqualTo(other);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#longValue()
//	 */
//	public long longValue() {
//		return sip.longValue();
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#multiply(double)
//	 */
//	public DoubleBinding multiply(double other) {
//		return sip.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#multiply(float)
//	 */
//	public FloatBinding multiply(float other) {
//		return sip.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#multiply(int)
//	 */
//	public IntegerBinding multiply(int other) {
//		return sip.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#multiply(long)
//	 */
//	public LongBinding multiply(long other) {
//		return sip.multiply(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#multiply(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding multiply(ObservableNumberValue other) {
//		return sip.multiply(other);
//	}
//
//	/**
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#negate()
//	 */
//	public IntegerBinding negate() {
//		return sip.negate();
//	}

	/**
	 * Set the property's value.
	 * 
	 * @param newValue the new value.
	 * @see javafx.beans.property.IntegerPropertyBase#set(int)
	 */
	public void set(int newValue) {
		Platform.runLater(new Runnable() {
			public void run() {
				sip.set(newValue);
			}
		});
	}

	/**
	 * Set the property's value.
	 * 
	 * @param v the new value.
	 * @see javafx.beans.property.IntegerProperty#setValue(java.lang.Number)
	 */
	public void setValue(Number v) {
		Platform.runLater(new Runnable() {
			public void run() {
				sip.setValue(v);
			}
		});
	}

	/**
	 * Get a string value of the property.
	 * 
	 * @return a string value of the property.
	 * @see javafx.beans.property.IntegerPropertyBase#toString()
	 */
	public String toString() {
		// not sure how to do this in a truly thread-safe manner. take our chances.
		return sip.toString();
	}

	/**
	 * A factory for {@link SimpleIntegerProperty} objects.
	 * 
	 * @return a new object.
	 */
	protected SimpleIntegerProperty newSimpleIntegerProperty() {
		return new SimpleIntegerProperty();
	}

//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#subtract(double)
//	 */
//	public DoubleBinding subtract(double other) {
//		return sip.subtract(other);
//	}

//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#subtract(float)
//	 */
//	public FloatBinding subtract(float other) {
//		return sip.subtract(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#subtract(int)
//	 */
//	public IntegerBinding subtract(int other) {
//		return sip.subtract(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.IntegerExpression#subtract(long)
//	 */
//	public LongBinding subtract(long other) {
//		return sip.subtract(other);
//	}
//
//	/**
//	 * @param other
//	 * @return
//	 * @see javafx.beans.binding.NumberExpressionBase#subtract(javafx.beans.value.ObservableNumberValue)
//	 */
//	public NumberBinding subtract(ObservableNumberValue other) {
//		return sip.subtract(other);
//	}

	/**
	 * A factory for {@link SimpleIntegerProperty} objects.
	 * 
	 * @param initialValue the initial value.
	 * @return a new object.
	 */
	protected SimpleIntegerProperty newSimpleIntegerProperty(int initialValue) {
		return new SimpleIntegerProperty(initialValue);
	}

//	/**
//	 * 
//	 * @see javafx.beans.property.IntegerPropertyBase#unbind()
//	 */
//	public void unbind() {
//		sip.unbind();
//	}
//
//	/**
//	 * @param other
//	 * @see javafx.beans.property.IntegerProperty#unbindBidirectional(javafx.beans.property.Property)
//	 */
//	public void unbindBidirectional(Property<Number> other) {
//		sip.unbindBidirectional(other);
//	}

}
