package cotelab.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;

/**
 * The type of queue used to link phases in a pipeline.
 */
public class PipelineQueue extends HistoryTrackingQueue<Collection<Path>> {
	/**
	 * For serialization support.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The name of this queue.
	 */
	protected SimpleStringProperty name = newSimpleStringProperty();

	/**
	 * Construct a new object.
	 * 
	 * @param capacity the maximum number of items that may be in the queue at a
	 *                 time.
	 * @param aName    a name for the new object.
	 */
	public PipelineQueue(int capacity, String aName) {
		super(capacity);

		name.set(aName);
	}

	/**
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}

	/**
	 * @return a new object with value set to <code>null</code>.
	 */
	protected SimpleStringProperty newSimpleStringProperty() {
		return new SimpleStringProperty(null);
	}

}
