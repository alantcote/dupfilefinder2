package cotelab.dupfilefinder2.pipeline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A {@link LinkedBlockingQueue} that keeps track of the number of puts and
 * takes.
 * @author alantcote
 *
 * @param <E>
 */
public class HistoryTrackingQueue<E> extends LinkedBlockingQueue<E> {
	/**
	 * Serialization support.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The number of objects put into the queue.
	 */
	protected SimpleIntegerProperty putCount = new SimpleIntegerProperty(0);

	/**
	 * The number of objects taken from the queue.
	 */
	protected SimpleIntegerProperty takeCount = new SimpleIntegerProperty(0);

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
	 * {@inheritDoc}
	 * This method increments putCount.
	 */
	@Override
	public void put(E e) throws InterruptedException {
		super.put(e);

		incrementPutCount(1);
	}

	/**
	 * {@inheritDoc}
	 * This method increments takeCount.
	 */
	@Override
	public E take() throws InterruptedException {
		E taken = super.take();

		incrementTakeCount(1);

		return taken;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		E taken = super.poll(timeout, unit);

		if (taken != null) {
			incrementTakeCount(1);
		}

		return taken;
	}

	@Override
	public E poll() {
		E taken = super.poll();

		if (taken != null) {
			incrementTakeCount(1);
		}

		return taken;
	}

}
