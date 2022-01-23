package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.MultiValuedMap;
import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.dupfilefinder2.pipeline.queueing.PipelineQueue;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for {@link GroupBySizePhase}.
 */
@RunWith(JfxRunner.class)
public class GroupBySizePhaseTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#call()}.
	 * 
	 * @throws Exception if any is thrown by the test.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCall() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final Collection<Path> mockPathGroup = context.mock(Collection.class, "mockPathGroup");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput) {
			@Override
			protected void publishResults() {
				// dummied out
			}
		};

		context.checking(new Expectations() {
			{
				oneOf(mockInput).take();
				will(returnValue(mockPathGroup)); // immediate EOF

				oneOf(mockPathGroup).isEmpty();
				will(returnValue(true));

				oneOf(mockOutput).put(with(any(Collection.class)));
			}
		});

		fixture.call();
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#fileSize(java.nio.file.Path)}.
	 */
	@Test
	public void testFileSize() {
		// don't know of a good way to test around calls to static methods.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getFilesMeasuredCount()}.
	 */
	@Test
	public void testGetFilesMeasuredCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);

		assertEquals(fixture.filesMeasuredCount, fixture.getFilesMeasuredCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getSizeCount()}.
	 */
	@Test
	public void testGetSizeCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);

		assertEquals(fixture.sizeCount, fixture.getSizeCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getUniqueCount()}.
	 */
	@Test
	public void testGetUniqueCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);

		assertEquals(fixture.uniqueCount, fixture.getUniqueCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#getUnmeasurableCount()}.
	 */
	@Test
	public void testGetUnmeasurableCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);

		assertEquals(fixture.unmeasurableCount, fixture.getUnmeasurableCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#GroupBySizePhase(java.lang.String, cotelab.dupfilefinder2.pipeline.queueing.PipelineQueue, cotelab.dupfilefinder2.pipeline.queueing.PipelineQueue)}.
	 */
	@Test
	public void testGroupBySizePhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);

		assertEquals("phase", fixture.getPhaseName().get());
		assertEquals(mockInput, fixture.getInputQueue());
		assertEquals(mockOutput, fixture.getOutputQueue());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#newMultiValuedMapLongPath()}.
	 */
	@Test
	public void testNewMultiValuedMapLongPath() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);
		MultiValuedMap<Long, Path> result = fixture.newMultiValuedMapLongPath();

		assertNotNull(result);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#processBatch(java.util.Collection)}.
	 */
	@Test
	public void testProcessBatch() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);
		Collection<Path> batch = new ArrayList<Path>();

		fixture.processBatch(batch);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#publishResults()}.
	 */
	@Test
	public void testPublishResults() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);
		fixture.size2PathMap = fixture.newMultiValuedMapLongPath();

		fixture.publishResults();
	}

}
