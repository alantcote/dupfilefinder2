package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link GroupByContentPhase}.
 */
public class GroupByContentPhaseTest {
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#call()}.
	 * @throws Exception 
	 */
	@Test
	public void testCall() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		@SuppressWarnings("unchecked")
		final ArrayList<Path> mockPathArrayList = context.mock(ArrayList.class, "mockPathArrayList");

		context.checking(new Expectations() {
			{
				// worker 0
				oneOf(mockOutput).put(mockPathArrayList);
			}
		});

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput) {
			@Override
			protected Void superCall() throws Exception {
				return null;
			}

			@Override
			protected ArrayList<Path> newPathArrayList() {
				return mockPathArrayList;
			}
		};
		
		assertNull(fixture.call());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getBytesComparedCount()}.
	 */
	@Test
	public void testGetBytesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		ThreadSafeSimpleLongProperty result = fixture.getBytesComparedCount();
		
		assertEquals(result, fixture.bytesComparedCount);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getFilesComparedCount()}.
	 */
	@Test
	public void testGetFilesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		ThreadSafeSimpleIntegerProperty result = fixture.getFilesComparedCount();
		
		assertEquals(result, fixture.filesComparedCount);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#getUniqueCount()}.
	 */
	@Test
	public void testGetUniqueCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		ThreadSafeSimpleIntegerProperty result = fixture.getUniqueCount();
		
		assertEquals(result, fixture.uniqueCount);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#GroupByContentPhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testGroupByContentPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final GroupByContentWorker mockWorker = context.mock(GroupByContentWorker.class, "mockWorker");
		final ThreadSafeSimpleLongProperty mockBytesComparedCount = context.mock(ThreadSafeSimpleLongProperty.class,
				"mockBytesComparedCount");
		final LongRollupListener mockLongRollupListener = context.mock(LongRollupListener.class,
				"mockLongRollupListener");
		final ThreadSafeSimpleIntegerProperty mockFilesComparedCount = context
				.mock(ThreadSafeSimpleIntegerProperty.class, "mockFilesComparedCount");
		final IntegerRollupListener mockIntegerRollupListener = context.mock(IntegerRollupListener.class,
				"mockIntegerRollupListener");
		final ThreadSafeSimpleIntegerProperty mockUniqueCount = context.mock(ThreadSafeSimpleIntegerProperty.class,
				"mockUniqueCount");

		context.checking(new Expectations() {
			{
				// worker 0
				oneOf(mockWorker).getBytesComparedCount();
				will(returnValue(mockBytesComparedCount));

				oneOf(mockBytesComparedCount).addListener(mockLongRollupListener);

				oneOf(mockWorker).getFilesComparedCount();
				will(returnValue(mockFilesComparedCount));

				oneOf(mockFilesComparedCount).addListener(mockIntegerRollupListener);

				oneOf(mockWorker).getUniqueCount();
				will(returnValue(mockUniqueCount));

				oneOf(mockUniqueCount).addListener(mockIntegerRollupListener);

				// worker 1
				oneOf(mockWorker).getBytesComparedCount();
				will(returnValue(mockBytesComparedCount));

				oneOf(mockBytesComparedCount).addListener(mockLongRollupListener);

				oneOf(mockWorker).getFilesComparedCount();
				will(returnValue(mockFilesComparedCount));

				oneOf(mockFilesComparedCount).addListener(mockIntegerRollupListener);

				oneOf(mockWorker).getUniqueCount();
				will(returnValue(mockUniqueCount));

				oneOf(mockUniqueCount).addListener(mockIntegerRollupListener);

				// worker 2
				oneOf(mockWorker).getBytesComparedCount();
				will(returnValue(mockBytesComparedCount));

				oneOf(mockBytesComparedCount).addListener(mockLongRollupListener);

				oneOf(mockWorker).getFilesComparedCount();
				will(returnValue(mockFilesComparedCount));

				oneOf(mockFilesComparedCount).addListener(mockIntegerRollupListener);

				oneOf(mockWorker).getUniqueCount();
				will(returnValue(mockUniqueCount));

				oneOf(mockUniqueCount).addListener(mockIntegerRollupListener);

				// worker 3
				oneOf(mockWorker).getBytesComparedCount();
				will(returnValue(mockBytesComparedCount));

				oneOf(mockBytesComparedCount).addListener(mockLongRollupListener);

				oneOf(mockWorker).getFilesComparedCount();
				will(returnValue(mockFilesComparedCount));

				oneOf(mockFilesComparedCount).addListener(mockIntegerRollupListener);

				oneOf(mockWorker).getUniqueCount();
				will(returnValue(mockUniqueCount));

				oneOf(mockUniqueCount).addListener(mockIntegerRollupListener);

				// worker 4
				oneOf(mockWorker).getBytesComparedCount();
				will(returnValue(mockBytesComparedCount));

				oneOf(mockBytesComparedCount).addListener(mockLongRollupListener);

				oneOf(mockWorker).getFilesComparedCount();
				will(returnValue(mockFilesComparedCount));

				oneOf(mockFilesComparedCount).addListener(mockIntegerRollupListener);

				oneOf(mockWorker).getUniqueCount();
				will(returnValue(mockUniqueCount));

				oneOf(mockUniqueCount).addListener(mockIntegerRollupListener);
			}
		});

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput) {
			@Override
			protected GroupByContentWorker newGroupByContentWorker(String name, PipelineQueue input,
					PipelineQueue output) {
				return mockWorker;
			}

			@Override
			protected IntegerRollupListener newIntegerRollupListener(ThreadSafeSimpleIntegerProperty prop) {
				return mockIntegerRollupListener;
			}

			@Override
			protected LongRollupListener newLongRollupListener(ThreadSafeSimpleLongProperty prop) {
				return mockLongRollupListener;
			}
		};

		assertTrue((fixture != null) && (fixture instanceof GroupByContentPhase));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#newGroupByContentWorker(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testNewGroupByContentWorker() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		GroupByContentWorker result = fixture.newGroupByContentWorker("worker", mockInput, mockOutput);
		
		assertTrue((result != null) && (result instanceof GroupByContentWorker));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#newIntegerRollupListener(cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty)}.
	 */
	@Test
	public void testNewIntegerRollupListener() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final ThreadSafeSimpleIntegerProperty mockIntegerComparedCount = context.mock(ThreadSafeSimpleIntegerProperty.class,
				"mockIntegerComparedCount");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		IntegerRollupListener result = fixture.newIntegerRollupListener(mockIntegerComparedCount);
		
		assertTrue((result != null) && (result instanceof IntegerRollupListener));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#newLongRollupListener(cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty)}.
	 */
	@Test
	public void testNewLongRollupListener() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final ThreadSafeSimpleLongProperty mockBytesComparedCount = context.mock(ThreadSafeSimpleLongProperty.class,
				"mockBytesComparedCount");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		LongRollupListener result = fixture.newLongRollupListener(mockBytesComparedCount);
		
		assertTrue((result != null) && (result instanceof LongRollupListener));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#newThreadSafeSimpleIntegerProperty()}.
	 */
	@Test
	public void testNewThreadSafeSimpleIntegerProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		ThreadSafeSimpleIntegerProperty result = fixture.newThreadSafeSimpleIntegerProperty();
		
		assertTrue((result != null) && (result instanceof ThreadSafeSimpleIntegerProperty));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentPhase#newThreadSafeSimpleLongProperty()}.
	 */
	@Test
	public void testNewThreadSafeSimpleLongProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);
		
		ThreadSafeSimpleLongProperty result = fixture.newThreadSafeSimpleLongProperty();
		
		assertTrue((result != null) && (result instanceof ThreadSafeSimpleLongProperty));
	}

}
