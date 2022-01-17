package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link cotelab.dupfilefinder2.pipeline.Pipeline}.
 */
public class PipelineTest {
	protected Mockery context;

	protected Sequence sequence;

	@After
	public void runAfterTests() throws Exception {
		context.assertIsSatisfied();
	}

	@Before
	public void runBeforeTests() throws Exception {
		context = new Mockery() {
			{
				setThreadingPolicy(new Synchroniser());
				setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
			}
		};

		sequence = context.sequence(getClass().getName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#gbcStateProperty()}.
	 */
	@Test
	public void testGbcStateProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContentPhase.stateProperty(), fixture.gbcStateProperty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#gbsStateProperty()}.
	 */
	@Test
	public void testGbsStateProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySizePhase.stateProperty(), fixture.gbsStateProperty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBC2MSIQueueName()}.
	 */
	@Test
	public void testGetGBC2MSIQueueName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContent2MatchingSubtreeIdentificationQueue.getName(),
				fixture.getGBC2MSIQueueName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBC2MSIQueuePutCount()}.
	 */
	@Test
	public void testGetGBC2MSIQueuePutCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContent2MatchingSubtreeIdentificationQueue.getPutCount(),
				fixture.getGBC2MSIQueuePutCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBC2MSIQueueTakeCount()}.
	 */
	@Test
	public void testGetGBC2MSIQueueTakeCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContent2MatchingSubtreeIdentificationQueue.getTakeCount(),
				fixture.getGBC2MSIQueueTakeCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBCBytesComparedCount()}.
	 */
	@Test
	public void testGetGBCBytesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContentPhase.getBytesComparedCount(), fixture.getGBCBytesComparedCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBCFilesComparedCount()}.
	 */
	@Test
	public void testGetGBCFilesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContentPhase.getFilesComparedCount(), fixture.getGBCFilesComparedCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBCPhaseName()}.
	 */
	@Test
	public void testGetGBCPhaseName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContentPhase.getPhaseName(), fixture.getGBCPhaseName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBCUniqueCount()}.
	 */
	@Test
	public void testGetGBCUniqueCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupByContentPhase.getUniqueCount(), fixture.getGBCUniqueCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBS2GBCQueueName()}.
	 */
	@Test
	public void testGetGBS2GBCQueueName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySize2GroupByContentQueue.getName(), fixture.getGBS2GBCQueueName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBS2GBCQueuePutCount()}.
	 */
	@Test
	public void testGetGBS2GBCQueuePutCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySize2GroupByContentQueue.getPutCount(), fixture.getGBS2GBCQueuePutCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBS2GBCQueueTakeCount()}.
	 */
	@Test
	public void testGetGBS2GBCQueueTakeCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySize2GroupByContentQueue.getTakeCount(), fixture.getGBS2GBCQueueTakeCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBSFilesMeasuredCount()}.
	 */
	@Test
	public void testGetGBSFilesMeasuredCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySizePhase.getFilesMeasuredCount(), fixture.getGBSFilesMeasuredCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBSPhaseName()}.
	 */
	@Test
	public void testGetGBSPhaseName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySizePhase.getPhaseName(), fixture.getGBSPhaseName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBSSizeCount()}.
	 */
	@Test
	public void testGetGBSSizeCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySizePhase.getSizeCount(), fixture.getGBSSizeCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBSUniqueCount()}.
	 */
	@Test
	public void testGetGBSUniqueCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySizePhase.getUniqueCount(), fixture.getGBSUniqueCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getGBSUnmeasurableCount()}.
	 */
	@Test
	public void testGetGBSUnmeasurableCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.groupBySizePhase.getUnmeasurableCount(), fixture.getGBSUnmeasurableCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getMSIPathGroupsConsideredProperty()}.
	 */
	@Test
	public void testGetMSIPathGroupsConsideredProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.matchingSubtreeIdentificationPhase.getPathGroupsConsideredProperty(),
				fixture.getMSIPathGroupsConsideredProperty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getMSIPhaseName()}.
	 */
	@Test
	public void testGetMSIPhaseName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.matchingSubtreeIdentificationPhase.getPhaseName(), fixture.getMSIPhaseName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSS2GBSQueueName()}.
	 */
	@Test
	public void testGetSS2GBSQueueName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearch2GroupBySizeQueue.getName(), fixture.getSS2GBSQueueName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSS2GBSQueuePutCount()}.
	 */
	@Test
	public void testGetSS2GBSQueuePutCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearch2GroupBySizeQueue.getPutCount(), fixture.getSS2GBSQueuePutCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSS2GBSQueueTakeCount()}.
	 */
	@Test
	public void testGetSS2GBSQueueTakeCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearch2GroupBySizeQueue.getTakeCount(), fixture.getSS2GBSQueueTakeCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSSPDirectoryCount()}.
	 */
	@Test
	public void testGetSSPDirectoryCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.getDirectoryCount(), fixture.getSSPDirectoryCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSSPFailedAccessCount()}.
	 */
	@Test
	public void testGetSSPFailedAccessCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.getFailedAccessCount(), fixture.getSSPFailedAccessCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSSPOtherCount()}.
	 */
	@Test
	public void testGetSSPOtherCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.getOtherCount(), fixture.getSSPOtherCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSSPPhaseName()}.
	 */
	@Test
	public void testGetSSPPhaseName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.getPhaseName(), fixture.getSSPPhaseName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSSPRegularFileCount()}.
	 */
	@Test
	public void testGetSSPRegularFileCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.getRegularFileCount(), fixture.getSSPRegularFileCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSSPSymbolicLinkCount()}.
	 */
	@Test
	public void testGetSSPSymbolicLinkCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.getSymbolicLinkCount(), fixture.getSSPSymbolicLinkCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#getSSPUnreadableCount()}.
	 */
	@Test
	public void testGetSSPUnreadableCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.getUnreadableCount(), fixture.getSSPUnreadableCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#msiStateProperty()}.
	 */
	@Test
	public void testMsiStateProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.matchingSubtreeIdentificationPhase.stateProperty(), fixture.msiStateProperty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#newGroupByContentPhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testNewGroupByContentPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertNotNull(fixture.newGroupByContentPhase("phase", mockInput, mockOutput));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#newGroupBySizePhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testNewGroupBySizePhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertNotNull(fixture.newGroupBySizePhase("phase", mockInput, mockOutput));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#newMatchingSubtreeIdentificationPhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testNewMatchingSubtreeIdentificationPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertNotNull(fixture.newMatchingSubtreeIdentificationPhase("phase", mockInput, mockOutput));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#newPipelineQueue(int, java.lang.String)}.
	 */
	@Test
	public void testNewPipelineQueue() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertNotNull(fixture.newPipelineQueue(5, "q"));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#newSubtreeSearchPhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testNewSubtreeSearchPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertNotNull(fixture.newSubtreeSearchPhase("phase", mockInput, mockOutput));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#Pipeline(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testPipeline() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Pipeline#sspStateProperty()}.
	 */
	@Test
	public void testSspStateProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Pipeline fixture = new Pipeline("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());

		assertEquals(fixture.subtreeSearchPhase.stateProperty(), fixture.sspStateProperty());
	}

}
