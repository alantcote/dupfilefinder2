package cotelab.dupfilefinder2;

import static org.junit.Assert.*;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.jfxrunner.JavaFxJUnit4ClassRunner;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for {@link HeapMonitor}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class HeapMonitorTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#conciseFormat(long)}.
	 */
	@Test
	public void testConciseFormat() {
		final ProgressBar mockProgressBar = context.mock(ProgressBar.class, "mockProgressBar");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		HeapMonitor fixture = new HeapMonitor(mockProgressBar, mockLabel);
		long millis = (HeapMonitor.GIG * 5) + (HeapMonitor.MEG * 5) + (HeapMonitor.KILO * 5) + 5;
		String expected = "5.5G";
		String actual = fixture.conciseFormat(millis);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#getRuntime()}.
	 */
	@Test
	public void testGetRuntime() {
		// don't know how to check whether static method was called.
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#HeapMonitor(javafx.scene.control.ProgressBar, javafx.scene.control.Label)}.
	 */
	@Test
	public void testHeapMonitor() {
		final ProgressBar mockProgressBar = context.mock(ProgressBar.class, "mockProgressBar");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		HeapMonitor fixture = new HeapMonitor(mockProgressBar, mockLabel);
		
		assertEquals(mockProgressBar, fixture.heapProgressBar);
		assertEquals(mockLabel, fixture.heapMessage);
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#isCancelled()}.
	 */
	@Test
	public void testIsCancelled() {
		final ProgressBar mockProgressBar = context.mock(ProgressBar.class, "mockProgressBar");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		HeapMonitor fixture = new HeapMonitor(mockProgressBar, mockLabel);
		
		assertFalse(fixture.isCancelled());
		fixture.setCancelled(true);
		assertTrue(fixture.isCancelled());
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#platformRunLater(java.lang.Runnable)}.
	 */
	@Test
	public void testPlatformRunLater() {
		// don't know how to check whether static method was called.
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#run()}.
	 */
	@Test
	public void testRun() {
		final ProgressBar mockProgressBar = context.mock(ProgressBar.class, "mockProgressBar");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		HeapMonitor fixture = new HeapMonitor(mockProgressBar, mockLabel) {

			@Override
			protected void threadSleep(long delay) throws InterruptedException {
				assertEquals(HeapMonitor.DELAY_MILLIS, delay);
			}

			@Override
			protected void updateHeapProgressBar() {
				setCancelled(true);
			}
			
		};
		
		fixture.run();
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#setCancelled(boolean)}.
	 */
	@Test
	public void testSetCancelled() {
		final ProgressBar mockProgressBar = context.mock(ProgressBar.class, "mockProgressBar");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		HeapMonitor fixture = new HeapMonitor(mockProgressBar, mockLabel);
		
		fixture.setCancelled(true);
		assertTrue(fixture.isCancelled());
		fixture.setCancelled(false);
		assertFalse(fixture.isCancelled());
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#threadSleep(long)}.
	 */
	@Test
	public void testThreadSleep() {
		// don't know how to check whether static method was called.
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.HeapMonitor#updateHeapProgressBar()}.
	 */
	@Test
	public void testUpdateHeapProgressBar() {
		final ProgressBar mockProgressBar = context.mock(ProgressBar.class, "mockProgressBar");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final SimpleIntegerProperty count = new SimpleIntegerProperty(0);
		HeapMonitor fixture = new HeapMonitor(mockProgressBar, mockLabel) {

			@Override
			protected void platformRunLater(Runnable aRunnable) {
				assertNotNull(aRunnable);
				count.set(1 + count.get());
			}
			
		};
		
		fixture.updateHeapProgressBar();
		
		assertEquals(1, count.get());
	}

}
