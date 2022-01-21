package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.application.Platform;

/**
 * Test case for {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue}.
 */
public class HistoryTrackingQueueTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()}.
	 */
	@Test
	public void testGetPutCount() {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		assertEquals(0, fixture.getPutCount().get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()}.
	 */
	@Test
	public void testGetTakeCount() {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		assertEquals(0, fixture.getTakeCount().get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#HistoryTrackingQueue(int)}.
	 */
	@Test
	public void testHistoryTrackingQueue() {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		assertNotNull(fixture);
		assertEquals(5, fixture.remainingCapacity());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#incrementPutCount(int)}.
	 */
	@Test
	public void testIncrementPutCount() {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				fixture.incrementPutCount(5);

				assertEquals(5, fixture.getPutCount().get());
			}
			
		});
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#incrementTakeCount(int)}.
	 */
	@Test
	public void testIncrementTakeCount() {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				fixture.incrementTakeCount(5);

				assertEquals(5, fixture.getTakeCount().get());
			}
			
		});

	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#newFXThreadIntegerProperty()}.
	 */
	@Test
	public void testNewSimpleIntegerProperty() {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		assertNotNull(fixture.newFXThreadIntegerProperty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#poll()}.
	 * 
	 * @throws InterruptedException if thrown by the code under test.
	 */
	@Test
	public void testPoll() throws InterruptedException {
		final HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					fixture.put("item");
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertEquals(null, e.getMessage());
				}

				assertEquals("item", fixture.poll());
				assertEquals(1, fixture.getTakeCount().get());
			}
		});
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#poll(long, java.util.concurrent.TimeUnit)}.
	 * 
	 * @throws InterruptedException if thrown by the code under test.
	 */
	@Test
	public void testPollLongTimeUnit() throws InterruptedException {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					fixture.put("item");
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertEquals(null, e.getMessage());
				}

				try {
					assertEquals("item", fixture.poll(1, TimeUnit.SECONDS));
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertEquals(null, e.getMessage());
				}
				assertEquals(1, fixture.getTakeCount().get());
			}
		});
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#put(java.lang.Object)}.
	 * 
	 * @throws InterruptedException if thrown by the code under test.
	 */
	@Test
	public void testPutE() throws InterruptedException {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					fixture.put("item");
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertEquals(null, e.getMessage());
				}

				assertEquals("item", fixture.poll());
				assertEquals(1, fixture.getPutCount().get());
			}
		});
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#take()}.
	 * 
	 * @throws InterruptedException if thrown by the code under test.
	 */
	@Test
	public void testTake() throws InterruptedException {
		HistoryTrackingQueue<String> fixture = new HistoryTrackingQueue<String>(5);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					fixture.put("item");
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertEquals(null, e.getMessage());
				}
				try {
					assertEquals("item", fixture.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertEquals(null, e.getMessage());
				}
				assertEquals(1, fixture.getTakeCount().get());
			}
		});
	}

}
