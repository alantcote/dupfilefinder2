package cotelab.dupfilefinder2.pipeline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A {@link LinkedBlockingQueue} that keeps track of the number of puts and
 * takes.
 * 
 * @param <E> the class of object that will pass through the queue.
 */
public class HistoryTrackingQueue<E> extends LinkedBlockingQueue<E> {
	/**
	 * Serialization support.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The number of objects put into the queue.
	 */
	protected SimpleIntegerProperty putCount = newSimpleIntegerProperty();

	/**
	 * The number of objects taken from the queue.
	 */
	protected SimpleIntegerProperty takeCount = newSimpleIntegerProperty();

	/**
	 * {@inheritDoc}
	 */
	public HistoryTrackingQueue(int queueCapacity) {
		super(queueCapacity);
	}

	/**
	 * @return the putCount
	 */
	public SimpleIntegerProperty getPutCount() {
		return putCount;
	}

	/**
	 * @return the takeCount
	 */
	public SimpleIntegerProperty getTakeCount() {
		return takeCount;
	}

	/**
	 * Increment the putCount.
	 * 
	 * @param increment the value to be added.
	 */
	public synchronized void incrementPutCount(int increment) {
		putCount.set(putCount.get() + increment);
	}

	/**
	 * Increment the takeCount.
	 * 
	 * @param increment the value to be added.
	 */
	public synchronized void incrementTakeCount(int increment) {
		takeCount.set(takeCount.get() + increment);
	}

	/**
	 * {@inheritDoc} This method counts the taken items.
	 */
	@Override
	public E poll() {
		E taken = super.poll();

		if (taken != null) {
			incrementTakeCount(1);
		}

		return taken;
	}

	/**
	 * {@inheritDoc} This method counts the taken items.
	 */
	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		E taken = super.poll(timeout, unit);

		if (taken != null) {
			incrementTakeCount(1);
		}

		return taken;
	}

	/**
	 * {@inheritDoc} This method increments putCount.
	 */
	@Override
	public void put(E e) throws InterruptedException {
		super.put(e);

		incrementPutCount(1);
	}

	/**
	 * {@inheritDoc} This method increments takeCount.
	 */
	@Override
	public E take() throws InterruptedException {
		E taken = super.take();

		incrementTakeCount(1);

		return taken;
	}

	/**
	 * @return a new object.
	 */
	protected SimpleIntegerProperty newSimpleIntegerProperty() {
		return new SimpleIntegerProperty(0);
	}

}
