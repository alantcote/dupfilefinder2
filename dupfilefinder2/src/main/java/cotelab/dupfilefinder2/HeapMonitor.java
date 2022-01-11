package cotelab.dupfilefinder2;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

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

	/**
	 * an instance of {@link Runtime}.
	 */
	protected Runtime runtime = getRuntime();

	/**
	 * @return an instance of {@link Runtime}.
	 */
	protected Runtime getRuntime() {
		return Runtime.getRuntime();
	}
	
	/**
	 * The {@link ProgressBar}.
	 */
	protected ProgressBar heapProgressBar;
	
	/**
	 * Construct a new object.
	 * @param aProgressBar
	 * @param aHeapMessage
	 */
	public HeapMonitor(ProgressBar aProgressBar, Label aHeapMessage) {
		heapProgressBar = aProgressBar;
		heapMessage = aHeapMessage;
	}
	
	/**
	 * The message {@link Label}.
	 */
	protected Label heapMessage;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (true) {
			try {
				while (true) {
					Thread.sleep(DELAY_MILLIS);

					updateHeapProgressBar();
				}
			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
		}
	}

	/**
	 * Format a heap size value.
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
	 * Update the progress bar and associated message.
	 */
	protected void updateHeapProgressBar() {
		Platform.runLater(new Runnable() {
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
