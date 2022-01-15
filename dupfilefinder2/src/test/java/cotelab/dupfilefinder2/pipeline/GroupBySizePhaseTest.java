package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.sf.cotelab.jfxrunner.JavaFxJUnit4ClassRunner;

/**
 * Test case for {@link GroupBySizePhase}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class GroupBySizePhaseTest {
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#call()}.
	 * 
	 * @throws Exception if any is thrown by the test.
	 */
	@Test
	public void testCall() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		@SuppressWarnings("unchecked")
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#GroupBySizePhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupBySizePhase#newLongToPathGroupHashtable()}.
	 */
	@Test
	public void testNewLongToPathGroupHashtable() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupBySizePhase fixture = new GroupBySizePhase("phase", mockInput, mockOutput);
		Hashtable<Long, ArrayList<Path>> result = fixture.newLongToPathGroupHashtable();

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
		fixture.size2PathMap = fixture.newLongToPathGroupHashtable();

		fixture.publishResults();
	}

}