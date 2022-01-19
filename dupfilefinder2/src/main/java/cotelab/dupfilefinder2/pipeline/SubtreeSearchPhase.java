package cotelab.dupfilefinder2.pipeline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A {@link Phase} designed to enumerate the files in designated subtrees of the
 * file system. The input is a single collection containing pathnames of files
 * and directories to be treated as roots of subtrees to be enumerated. The
 * output is a collection of the enumerated regular file pathnames.
 */
public class SubtreeSearchPhase extends Phase {

	/**
	 * The number of directory paths found.
	 */
	protected SimpleIntegerProperty directoryCount = newSimpleIntegerProperty();

	/**
	 * The number of paths that could not be accessed.
	 */
	protected SimpleIntegerProperty failedAccessCount = newSimpleIntegerProperty();

	/**
	 * The number of paths that didn't fit in another category.
	 */
	protected SimpleIntegerProperty otherCount = newSimpleIntegerProperty();

	/**
	 * The number of regular file paths found.
	 */
	protected SimpleIntegerProperty regularFileCount = newSimpleIntegerProperty();

	/**
	 * The number of symbolic link paths found.
	 */
	protected SimpleIntegerProperty symbolicLinkCount = newSimpleIntegerProperty();

	/**
	 * The number of paths that could not be read.
	 */
	protected SimpleIntegerProperty unreadableCount = newSimpleIntegerProperty();

	/**
	 * Construct a new object.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public SubtreeSearchPhase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super(name, theInput, theOutput);
	}

	/**
	 * @return the directoryCount
	 */
	public SimpleIntegerProperty getDirectoryCount() {
		return directoryCount;
	}

	/**
	 * @return the failedAccessCount
	 */
	public SimpleIntegerProperty getFailedAccessCount() {
		return failedAccessCount;
	}

	/**
	 * @return the otherCount
	 */
	public SimpleIntegerProperty getOtherCount() {
		return otherCount;
	}

	/**
	 * @return the regularFileCount
	 */
	public SimpleIntegerProperty getRegularFileCount() {
		return regularFileCount;
	}

	/**
	 * @return the symbolicLinkCount
	 */
	public SimpleIntegerProperty getSymbolicLinkCount() {
		return symbolicLinkCount;
	}

	/**
	 * @return the unreadableCount
	 */
	public SimpleIntegerProperty getUnreadableCount() {
		return unreadableCount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Void call() throws Exception {
		Collection<Path> packet;

		while (null != (packet = inputQueue.poll(5, TimeUnit.SECONDS))) {
			if (isCancelled()) {
				break;
			}

			if (packet.isEmpty()) { // EOF convention
				break;
			}

			for (Path aPath : packet) {
				if (isCancelled()) {
					break;
				}

				PathSearchVisitor pathSearchVisitor = newPathSearchVisitor();

				pathSearchVisitor.getDirectoryCount().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						int delta = newValue.intValue() - oldValue.intValue();

						directoryCount.set(directoryCount.get() + delta);
					}
				});
				pathSearchVisitor.getFailedAccessCount().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						int delta = newValue.intValue() - oldValue.intValue();

						failedAccessCount.set(failedAccessCount.get() + delta);
					}
				});
				pathSearchVisitor.getOtherCount().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						int delta = newValue.intValue() - oldValue.intValue();

						otherCount.set(otherCount.get() + delta);
					}
				});
				pathSearchVisitor.getRegularFileCount().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						int delta = newValue.intValue() - oldValue.intValue();

						regularFileCount.set(regularFileCount.get() + delta);
					}
				});
				pathSearchVisitor.getSymbolicLinkCount().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						int delta = newValue.intValue() - oldValue.intValue();

						symbolicLinkCount.set(symbolicLinkCount.get() + delta);
					}
				});
				pathSearchVisitor.getUnreadableCount().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						int delta = newValue.intValue() - oldValue.intValue();

						unreadableCount.set(unreadableCount.get() + delta);
					}
				});

				Files.walkFileTree(aPath, pathSearchVisitor);

				if (pathSearchVisitor.getFiles().size() > 0) {
					outputQueue.put(pathSearchVisitor.getFiles());
				}
			}
		}

		outputQueue.put(newPathArrayList()); // EOF convention

		return null;
	}

	/**
	 * @return a new object.
	 */
	protected Collection<Path> newPathArrayList() {
		return new ArrayList<Path>();
	}

	/**
	 * @return a new object.
	 */
	protected PathSearchVisitor newPathSearchVisitor() {
		return new PathSearchVisitor();
	}

	/**
	 * @return a new object with its value set to zero.
	 */
	protected SimpleIntegerProperty newSimpleIntegerProperty() {
		return new SimpleIntegerProperty(0);
	}

}
