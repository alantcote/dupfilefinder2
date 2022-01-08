/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A pipeline. The input to the pipeline is a collection of file and/or
 * directory pathnames to be compared to one another. The output from the
 * pipeline is a number of collections of file and directory pathnames; each
 * output collection is comprised of pathnames that have identical contents.
 * 
 * @author alantcote
 *
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
		
		// TODO if any phase finishes with no output, remaining phases should be
		// cancelled

		System.out.println("Pipeline.Pipeline(): creating inter-phase queues");

		subtreeSearch2GroupBySizeQueue = new PipelineQueue(Integer.MAX_VALUE, "SubtreeSearch to GroupBySize");
		groupBySize2GroupByContentQueue = new PipelineQueue(Integer.MAX_VALUE, "GroupBySize to GroupByContent");
		groupByContent2MatchingSubtreeIdentificationQueue = new PipelineQueue(Integer.MAX_VALUE,
				"GroupByContent to MatchingSubtreeIdentification");

		System.out.println("Pipeline.Pipeline(): creating phases");

		subtreeSearchPhase = new SubtreeSearchPhase("Subtree Search Phase", inputQueue, subtreeSearch2GroupBySizeQueue);
		groupBySizePhase = new GroupBySizePhase("Group By Size Phase", subtreeSearch2GroupBySizeQueue,
				groupBySize2GroupByContentQueue);
		groupByContentPhase = new GroupByContentPhase("Group By Content Phase", groupBySize2GroupByContentQueue,
				groupByContent2MatchingSubtreeIdentificationQueue);
		matchingSubtreeIdentificationPhase = new MatchingSubtreeIdentificationPhase(
				"Matching Subtree Identification Phase", groupByContent2MatchingSubtreeIdentificationQueue, outputQueue);

		System.out.println("Pipeline.Pipeline(): registering phases");

		children.add(subtreeSearchPhase);
		children.add(groupBySizePhase);
		children.add(groupByContentPhase);
		children.add(matchingSubtreeIdentificationPhase);

		System.out.println("Pipeline.Pipeline(): method complete");
	}

	public ReadOnlyObjectProperty<State> gbcStateProperty() {
		return groupByContentPhase.stateProperty();
	}

	public ReadOnlyObjectProperty<State> gbsStateProperty() {
		return groupBySizePhase.stateProperty();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getGBC2MSIQueueName() {
		return groupByContent2MatchingSubtreeIdentificationQueue.getName();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getGBC2MSIQueuePutCount() {
		return groupByContent2MatchingSubtreeIdentificationQueue.getPutCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getGBC2MSIQueueTakeCount() {
		return groupByContent2MatchingSubtreeIdentificationQueue.getTakeCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getBytesComparedCount()
	 */
	public ThreadSafeSimpleLongProperty getGBCBytesComparedCount() {
		return groupByContentPhase.getBytesComparedCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getFilesComparedCount()
	 */
	public ThreadSafeSimpleIntegerProperty getGBCFilesComparedCount() {
		return groupByContentPhase.getFilesComparedCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getGBCPhaseName() {
		return groupByContentPhase.getPhaseName();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getUniqueCount()
	 */
	public ThreadSafeSimpleIntegerProperty getGBCUniqueCount() {
		return groupByContentPhase.getUniqueCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getGBS2GBCQueueName() {
		return groupBySize2GroupByContentQueue.getName();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getGBS2GBCQueuePutCount() {
		return groupBySize2GroupByContentQueue.getPutCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getGBS2GBCQueueTakeCount() {
		return groupBySize2GroupByContentQueue.getTakeCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getFilesMeasuredCount()
	 */
	public SimpleIntegerProperty getGBSFilesMeasuredCount() {
		return groupBySizePhase.getFilesMeasuredCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getGBSPhaseName() {
		return groupBySizePhase.getPhaseName();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getSizeCount()
	 */
	public SimpleIntegerProperty getGBSSizeCount() {
		return groupBySizePhase.getSizeCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getUniqueCount()
	 */
	public SimpleIntegerProperty getGBSUniqueCount() {
		return groupBySizePhase.getUniqueCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getUnmeasurableCount()
	 */
	public SimpleIntegerProperty getGBSUnmeasurableCount() {
		return groupBySizePhase.getUnmeasurableCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#getPathGroupsConsideredProperty()
	 */
	public ThreadSafeSimpleIntegerProperty getMSIPathGroupsConsideredProperty() {
		return matchingSubtreeIdentificationPhase.getPathGroupsConsideredProperty();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getMSIPhaseName() {
		return matchingSubtreeIdentificationPhase.getPhaseName();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.PipelineQueue#getName()
	 */
	public SimpleStringProperty getSS2GBSQueueName() {
		return subtreeSearch2GroupBySizeQueue.getName();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getPutCount()
	 */
	public SimpleIntegerProperty getSS2GBSQueuePutCount() {
		return subtreeSearch2GroupBySizeQueue.getPutCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.HistoryTrackingQueue#getTakeCount()
	 */
	public SimpleIntegerProperty getSS2GBSQueueTakeCount() {
		return subtreeSearch2GroupBySizeQueue.getTakeCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getDirectoryCount()
	 */
	public SimpleIntegerProperty getSSPDirectoryCount() {
		return subtreeSearchPhase.getDirectoryCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getFailedAccessCount()
	 */
	public SimpleIntegerProperty getSSPFailedAccessCount() {
		return subtreeSearchPhase.getFailedAccessCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getOtherCount()
	 */
	public SimpleIntegerProperty getSSPOtherCount() {
		return subtreeSearchPhase.getOtherCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()
	 */
	public SimpleStringProperty getSSPPhaseName() {
		return subtreeSearchPhase.getPhaseName();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getRegularFileCount()
	 */
	public SimpleIntegerProperty getSSPRegularFileCount() {
		return subtreeSearchPhase.getRegularFileCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getSymbolicLinkCount()
	 */
	public SimpleIntegerProperty getSSPSymbolicLinkCount() {
		return subtreeSearchPhase.getSymbolicLinkCount();
	}

	/**
	 * @return
	 * @see cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getUnreadableCount()
	 */
	public SimpleIntegerProperty getSSPUnreadableCount() {
		return subtreeSearchPhase.getUnreadableCount();
	}

	public ReadOnlyObjectProperty<State> msiStateProperty() {
		return matchingSubtreeIdentificationPhase.stateProperty();
	}

	public ReadOnlyObjectProperty<State> sspStateProperty() {
		return subtreeSearchPhase.stateProperty();
	}

}
