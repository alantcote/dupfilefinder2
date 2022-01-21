package cotelab.dupfilefinder2;

import java.time.Duration;
import java.time.Instant;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

/**
 * Tracker for elapsed time.
 */
public class ElapsedTimeTracker extends AnimationTimer {
	/**
	 * The interval between {@link #startStampProperty} and
	 * {@link #endStampProperty}.
	 * 
	 * Probably should be a bound property.
	 */
	protected SimpleObjectProperty<Duration> elapsedProperty = new SimpleObjectProperty<Duration>();

	/**
	 * The display field to update with elapsed time information.
	 */
	protected Label elapsedTimeField;

	/**
	 * The current time, if this object has tracked. Otherwise, the time that this
	 * object was constructed.
	 */
	protected SimpleObjectProperty<Instant> endStampProperty = new SimpleObjectProperty<Instant>();

	/**
	 * The time that this object began tracking, if it has tracked. Otherwise, the
	 * time that this object was constructed.
	 */
	protected SimpleObjectProperty<Instant> startStampProperty = new SimpleObjectProperty<Instant>();

	/**
	 * Indicator for whether this object is tracking.
	 */
	protected SimpleBooleanProperty trackingActiveProperty = new SimpleBooleanProperty(false);

	/**
	 * Construct a new object.
	 * 
	 * @param aLabel display field to update with elapsed time information.
	 */
	public ElapsedTimeTracker(Label aLabel) {
		super();

		elapsedTimeField = aLabel;

		startStampProperty.set(Instant.now());
		endStampProperty.set(startStampProperty.get());
		elapsedProperty.set(Duration.between(startStampProperty.get(), endStampProperty.get()));

		elapsedTimeField.textProperty().bind(elapsedProperty.asString());
	}

	/**
	 * Begin tracking elapsed time.
	 */
	public void beginTracking() {
		startStampProperty.set(Instant.now());
		endStampProperty.set(startStampProperty.get());
		elapsedProperty.set(Duration.between(startStampProperty.get(), endStampProperty.get()));

		trackingActiveProperty.set(true);
		
		start();
	}

	/**
	 * @return the elapsedProperty
	 */
	public SimpleObjectProperty<Duration> elapsedProperty() {
		return elapsedProperty;
	}

	/**
	 * @return the endStampProperty
	 */
	public SimpleObjectProperty<Instant> endStampProperty() {
		return endStampProperty;
	}

	/**
	 * @return the elapsedTimeField
	 */
	public Label getElapsedTimeField() {
		return elapsedTimeField;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(long now) {
		if (isTrackingActive()) {
			endStampProperty.set(Instant.now());
			elapsedProperty.set(Duration.between(startStampProperty.get(), endStampProperty.get()));
		}
	}

	/**
	 * Get the value of {@link trackingActiveProperty}.
	 * 
	 * @return the value of {@link trackingActiveProperty}.
	 */
	public boolean isTrackingActive() {
		return trackingActiveProperty.get();
	}

	/**
	 * @return the startStampProperty
	 */
	public SimpleObjectProperty<Instant> startStampProperty() {
		return startStampProperty;
	}

	/**
	 * Stop tracking elapsed time.
	 */
	public void stopTracking() {
		trackingActiveProperty.set(false);
		
		stop();
	}

	/**
	 * @return the trackingActiveProperty
	 */
	public SimpleBooleanProperty trackingActiveProperty() {
		return trackingActiveProperty;
	}

}
