package cotelab.dupfilefinder2.pipeline;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A pipeline. The input to the pipeline is a collection of file and/or
 * directory pathnames to be compared to one another. The output from the
 * pipeline is a number of collections of file and directory pathnames; each
 * output collection is comprised of pathnames that have identical contents.
 */
public class Pipeline extends Phase {
	/**
	 * The queue from {@link #groupByContentPhase} to
	 * {@link #matchingSubtreeIdentificationPhase}.
	 */
	protected final PipelineQueue groupByContent2MatchingSubtreeIdentificationQueue;

	/**
	 * The GroupByContent phase.
	 */
	protected final GroupByContentPhase groupByContentPhase;

	/**
	 * The queue from {@link #groupBySizePhase} to {@link #groupByContentPhase}.
	 */
	protected final PipelineQueue groupBySize2GroupByContentQueue;

	/**
	 * The GroupBySize phase.
	 */
	protected final GroupBySizePhase groupBySizePhase;

	/**
	 * The MatchingSubtreeIdentification phase.
	 */
	protected final MatchingSubtreeIdentificationPhase matchingSubtreeIdentificationPhase;

	/**
	 * The queue from {@link #subtreeSearchPhase} to {@link #groupBySizePhase}.
	 */
	protected final PipelineQueue subtreeSearch2GroupBySizeQueue;

	/**
	 * The SubtreeSearch phase.
	 */
	protected final SubtreeSearchPhase subtreeSearchPhase;

	/**
	 * Construct a new object.
	 * 
	 * @param aName     a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public Pipeline(String aName, PipelineQueue theInput, PipelineQueue theOutput) {
		super(aName, theInput, theOutput);

		// if any phase finishes with no output, remaining phases could be
		// cancelled

		// create queues
		subtreeSearch2GroupBySizeQueue = newPipelineQueue(Integer.MAX_VALUE, "SubtreeSearch to GroupBySize");
		groupBySize2GroupByContentQueue = newPipelineQueue(Integer.MAX_VALUE, "GroupBySize to GroupByContent");
		groupByContent2MatchingSubtreeIdentificationQueue = newPipelineQueue(Integer.MAX_VALUE,
				"GroupByContent to MatchingSubtreeIdentification");

		// create phases and link them together
		subtreeSearchPhase = newSubtreeSearchPhase("Subtree Search Phase", inputQueue, subtreeSearch2GroupBySizeQueue);
		groupBySizePhase = newGroupBySizePhase("Group By Size Phase", subtreeSearch2GroupBySizeQueue,
				groupBySize2GroupByContentQueue);
		groupByContentPhase = newGroupByContentPhase("Group By Content Phase", groupBySize2GroupByContentQueue,
				groupByContent2MatchingSubtreeIdentificationQueue);
		matchingSubtreeIdentificationPhase = newMatchingSubtreeIdentificationPhase(
				"Matching Subtree Identification Phase", groupByContent2MatchingSubtreeIdentificationQueue,
				outputQueue);

		// put the phases where the superclass can find and start them
		children.add(subtreeSearchPhase);
		children.add(groupBySizePhase);
		children.add(groupByContentPhase);
		children.add(matchingSubtreeIdentificationPhase);
	}

	/**
	 * Get the GroupByContentPhase state property.
	 * 
	 * @return the property.
	 */
	public ReadOnlyObjectProperty<State> gbcStateProperty() {
		return groupByContentPhase.stateProperty();
	}

	/**
	 * Get the GroupBySizePhase state property.
	 * 
	 * @return the property.
	 */
	public ReadOnlyObjectProperty<State> gbsStateProperty() {
		return groupBySizePhase.stateProperty();
	}

	/**
	 * @return the groupByContent2MatchingSubtreeIdentificationQueue name.
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getGBC2MSIQueueName() {
		return groupByContent2MatchingSubtreeIdentificationQueue.getName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getGBC2MSIQueuePutCount() {
		return groupByContent2MatchingSubtreeIdentificationQueue.getPutCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getGBC2MSIQueueTakeCount() {
		return groupByContent2MatchingSubtreeIdentificationQueue.getTakeCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getBytesComparedCount()
	 */
	public ThreadSafeSimpleLongProperty getGBCBytesComparedCount() {
		return groupByContentPhase.getBytesComparedCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getFilesComparedCount()
	 */
	public ThreadSafeSimpleIntegerProperty getGBCFilesComparedCount() {
		return groupByContentPhase.getFilesComparedCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getGBCPhaseName() {
		return groupByContentPhase.getPhaseName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getUniqueCount()
	 */
	public ThreadSafeSimpleIntegerProperty getGBCUniqueCount() {
		return groupByContentPhase.getUniqueCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getGBS2GBCQueueName() {
		return groupBySize2GroupByContentQueue.getName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getGBS2GBCQueuePutCount() {
		return groupBySize2GroupByContentQueue.getPutCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getGBS2GBCQueueTakeCount() {
		return groupBySize2GroupByContentQueue.getTakeCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getFilesMeasuredCount()
	 */
	public SimpleIntegerProperty getGBSFilesMeasuredCount() {
		return groupBySizePhase.getFilesMeasuredCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getGBSPhaseName() {
		return groupBySizePhase.getPhaseName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getSizeCount()
	 */
	public SimpleIntegerProperty getGBSSizeCount() {
		return groupBySizePhase.getSizeCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getUniqueCount()
	 */
	public SimpleIntegerProperty getGBSUniqueCount() {
		return groupBySizePhase.getUniqueCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getUnmeasurableCount()
	 */
	public SimpleIntegerProperty getGBSUnmeasurableCount() {
		return groupBySizePhase.getUnmeasurableCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#getPathGroupsConsideredProperty()
	 */
	public ThreadSafeSimpleIntegerProperty getMSIPathGroupsConsideredProperty() {
		return matchingSubtreeIdentificationPhase.getPathGroupsConsideredProperty();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getMSIPhaseName() {
		return matchingSubtreeIdentificationPhase.getPhaseName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getSS2GBSQueueName() {
		return subtreeSearch2GroupBySizeQueue.getName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getSS2GBSQueuePutCount() {
		return subtreeSearch2GroupBySizeQueue.getPutCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getSS2GBSQueueTakeCount() {
		return subtreeSearch2GroupBySizeQueue.getTakeCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getDirectoryCount()
	 */
	public SimpleIntegerProperty getSSPDirectoryCount() {
		return subtreeSearchPhase.getDirectoryCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getFailedAccessCount()
	 */
	public SimpleIntegerProperty getSSPFailedAccessCount() {
		return subtreeSearchPhase.getFailedAccessCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getOtherCount()
	 */
	public SimpleIntegerProperty getSSPOtherCount() {
		return subtreeSearchPhase.getOtherCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getSSPPhaseName() {
		return subtreeSearchPhase.getPhaseName();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getRegularFileCount()
	 */
	public SimpleIntegerProperty getSSPRegularFileCount() {
		return subtreeSearchPhase.getRegularFileCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getSymbolicLinkCount()
	 */
	public SimpleIntegerProperty getSSPSymbolicLinkCount() {
		return subtreeSearchPhase.getSymbolicLinkCount();
	}

	/**
	 * @return the property.
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getUnreadableCount()
	 */
	public SimpleIntegerProperty getSSPUnreadableCount() {
		return subtreeSearchPhase.getUnreadableCount();
	}

	public ReadOnlyObjectProperty<State> msiStateProperty() {
		return matchingSubtreeIdentificationPhase.stateProperty();
	}

	/**
	 * @return the property.
	 */
	public ReadOnlyObjectProperty<State> sspStateProperty() {
		return subtreeSearchPhase.stateProperty();
	}

	/**
	 * @return a new object.
	 */
	protected GroupByContentPhase newGroupByContentPhase(String aName, PipelineQueue theInput,
			PipelineQueue theOutput) {
		return new GroupByContentPhase(aName, theInput, theOutput);
	}

	/**
	 * @return a new object.
	 */
	protected GroupBySizePhase newGroupBySizePhase(String aName, PipelineQueue theInput, PipelineQueue theOutput) {
		return new GroupBySizePhase(aName, theInput, theOutput);
	}

	/**
	 * @return a new object.
	 */
	protected MatchingSubtreeIdentificationPhase newMatchingSubtreeIdentificationPhase(String aName,
			PipelineQueue theInput, PipelineQueue theOutput) {
		return new MatchingSubtreeIdentificationPhase(aName, theInput, theOutput);
	}

	/**
	 * @return a new object.
	 */
	protected PipelineQueue newPipelineQueue(int capacity, String aName) {
		return new PipelineQueue(capacity, aName);
	}

	/**
	 * @return a new object.
	 */
	protected SubtreeSearchPhase newSubtreeSearchPhase(String aName, PipelineQueue theInput, PipelineQueue theOutput) {
		return new SubtreeSearchPhase(aName, theInput, theOutput);
	}

}
