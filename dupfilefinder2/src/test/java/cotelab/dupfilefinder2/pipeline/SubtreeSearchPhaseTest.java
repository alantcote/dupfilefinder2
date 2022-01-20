package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase}.
 */
public class SubtreeSearchPhaseTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#call()}.
	 * 
	 * @throws Exception if thrown by the code under test.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCall() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockInput).poll(5, TimeUnit.SECONDS);
				will(returnValue(null));

				oneOf(mockOutput).put(with(any(ArrayList.class)));
			}
		});

		assertNull(fixture.call());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getDirectoryCount()}.
	 */
	@Test
	public void testGetDirectoryCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertEquals(fixture.directoryCount, fixture.getDirectoryCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getFailedAccessCount()}.
	 */
	@Test
	public void testGetFailedAccessCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertEquals(fixture.failedAccessCount, fixture.getFailedAccessCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getOtherCount()}.
	 */
	@Test
	public void testGetOtherCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertEquals(fixture.otherCount, fixture.getOtherCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getRegularFileCount()}.
	 */
	@Test
	public void testGetRegularFileCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertEquals(fixture.regularFileCount, fixture.getRegularFileCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getSymbolicLinkCount()}.
	 */
	@Test
	public void testGetSymbolicLinkCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertEquals(fixture.symbolicLinkCount, fixture.getSymbolicLinkCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#getUnreadableCount()}.
	 */
	@Test
	public void testGetUnreadableCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertEquals(fixture.unreadableCount, fixture.getUnreadableCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#newPathArrayList()}.
	 */
	@Test
	public void testNewPathArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPathArrayList());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#newPathSearchVisitor()}.
	 */
	@Test
	public void testNewPathSearchVisitor() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPathSearchVisitor());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#newSimpleIntegerProperty()}.
	 */
	@Test
	public void testNewSimpleIntegerProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertNotNull(fixture.newSimpleIntegerProperty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase#SubtreeSearchPhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testSubtreeSearchPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		SubtreeSearchPhase fixture = new SubtreeSearchPhase("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());
	}

}
