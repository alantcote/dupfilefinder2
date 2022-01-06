package cotelab.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;

/**
 * The type of queue used to link phases in a pipeline.
 * 
 * @author alantcote
 *
 */
public class PipelineQueue extends HistoryTrackingQueue<Collection<Path>> {

	/**
	 * For serialization support.
	 */
	private static final long serialVersionUID = 1L;
	protected SimpleStringProperty name = new SimpleStringProperty(null);

	/**
	 * Construct a new object.
	 * 
	 * @param capacity the maximum number of items that may be in the queue at a
	 *                 time.
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

}
