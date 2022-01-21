package cotelab.dupfilefinder2;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class HeapTracker extends AnimationTimer {
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
	public HeapTracker(ProgressBar aProgressBar, Label aHeapMessage) {
		heapProgressBar = aProgressBar;
		heapMessage = aHeapMessage;
		
		start();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This implementation updates a progress bar and a message.
	 */
	@Override
	public void handle(long now) {
		updateHeapProgressBar();
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
	 * Update the progress bar and associated message.
	 */
	protected void updateHeapProgressBar() {
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
}
