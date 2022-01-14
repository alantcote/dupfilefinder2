package cotelab.dupfilefinder2;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * A monitor for the Java heap.
 */
public class HeapMonitor implements Runnable {
	/**
	 * Number of milliseconds between updates.
	 */
	protected static final long DELAY_MILLIS = 500;

	/**
	 * Number of bytes in a gigabyte.
	 */
	protected static final long GIG = 1024 * 1024 * 1024;

	/**
	 * Number of bytes in a kilobyte.
	 */
	protected static final long KILO = 1024;

	/**
	 * Number of bytes in a megabyte.
	 */
	protected static final long MEG = 1024 * 1024;

	protected boolean cancelled = false;

	/**
	 * The message {@link Label}.
	 */
	protected Label heapMessage;

	/**
	 * The {@link ProgressBar}.
	 */
	protected ProgressBar heapProgressBar;

	/**
	 * an instance of {@link Runtime}.
	 */
	protected Runtime runtime = getRuntime();

	/**
	 * Construct a new object.
	 * 
	 * @param aProgressBar
	 * @param aHeapMessage
	 */
	public HeapMonitor(ProgressBar aProgressBar, Label aHeapMessage) {
		heapProgressBar = aProgressBar;
		heapMessage = aHeapMessage;
	}

	/**
	 * @return the cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (!isCancelled()) {
			try {
				while (!isCancelled()) {
					threadSleep(DELAY_MILLIS);

					updateHeapProgressBar();
				}
			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
		}
	}

	/**
	 * @param cancelled the cancelled to set
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * Format a heap size value.
	 * 
	 * @param val a heap size value.
	 * @return the formatted value.
	 */
	protected String conciseFormat(long val) {
		String result = "";

		if (val >= GIG) {
			result = Long.toString(val / GIG) + "." + Long.toString((val % GIG) / MEG) + "G";
		} else if (val >= MEG) {
			result = Long.toString(val / MEG) + "." + Long.toString((val % MEG) / KILO) + "M";
		} else if (val >= KILO) {
			result = Long.toString(val / KILO) + "." + Long.toString(val % KILO) + "K";
		} else {
			result = Long.toString(val) + "B";
		}

		return result;
	}

	/**
	 * @return an instance of {@link Runtime}.
	 */
	protected Runtime getRuntime() {
		return Runtime.getRuntime();
	}

	/**
	 * Call {@link Platform#runLater} to run the parameter.
	 * 
	 * @param aRunnable the parameter to runLater().
	 */
	protected void platformRunLater(Runnable aRunnable) {
		Platform.runLater(aRunnable);
	}

	/**
	 * Sleep for a given number of milliseconds.
	 * 
	 * @param delay the number of milliseconds to sleep.
	 * @throws InterruptedException if thrown by {@link Thread#sleep}.
	 */
	protected void threadSleep(long delay) throws InterruptedException {
		Thread.sleep(delay);
	}

	/**
	 * Update the progress bar and associated message.
	 */
	protected void updateHeapProgressBar() {
		platformRunLater(new Runnable() {
			@Override
			public void run() {
				long totalHeap = runtime.totalMemory();
				long freeHeap = runtime.freeMemory();
				long usedHeapBytes = totalHeap - freeHeap;
				double progress = ((double) usedHeapBytes) / totalHeap;
				String usedString = conciseFormat(usedHeapBytes);
				String totalString = conciseFormat(totalHeap);
				String mssg = usedString + " / " + totalString + " heap used.";

				heapProgressBar.setProgress(progress);
				heapMessage.setText(mssg);
			}
		});
	}

}
