package io.github.alantcote.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;

import org.jmock.Expectations;
import org.junit.Test;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import io.github.alantcote.dupfilefinder2.beans.property.FXThreadIntegerProperty;
import io.github.alantcote.dupfilefinder2.beans.property.FXThreadLongProperty;
import io.github.alantcote.dupfilefinder2.beans.value.IntegerRollupListener;
import io.github.alantcote.dupfilefinder2.beans.value.LongRollupListener;
import io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase;
import io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker;
import io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 * Test case for {@link GroupByContentPhase}.
 */
public class GroupByContentPhaseTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#call()}.
	 * 
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
				oneOf(mockOutput).put(mockPathArrayList);
			}
		});

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput) {
			@Override
			protected ArrayList<Path> newPathArrayList() {
				return mockPathArrayList;
			}

			@Override
			protected Void superCall() throws Exception {
				return null;
			}
		};

		assertNull(fixture.call());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#getBytesComparedCount()}.
	 */
	@Test
	public void testGetBytesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);

		SimpleLongProperty result = fixture.getBytesComparedCount();

		assertEquals(result, fixture.bytesComparedCount);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#getFilesComparedCount()}.
	 */
	@Test
	public void testGetFilesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);

		SimpleIntegerProperty result = fixture.getFilesComparedCount();

		assertEquals(result, fixture.filesComparedCount);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#getUniqueCount()}.
	 */
	@Test
	public void testGetUniqueCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);

		SimpleIntegerProperty result = fixture.getUniqueCount();

		assertEquals(result, fixture.uniqueCount);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#GroupByContentPhase(java.lang.String, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue)}.
	 */
	@Test
	public void testGroupByContentPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final GroupByContentWorker mockWorker = context.mock(GroupByContentWorker.class, "mockWorker");
		final SimpleLongProperty mockBytesComparedCount = context.mock(SimpleLongProperty.class,
				"mockBytesComparedCount");
		final LongRollupListener mockLongRollupListener = context.mock(LongRollupListener.class,
				"mockLongRollupListener");
		final SimpleIntegerProperty mockFilesComparedCount = context.mock(SimpleIntegerProperty.class,
				"mockFilesComparedCount");
		final IntegerRollupListener mockIntegerRollupListener = context.mock(IntegerRollupListener.class,
				"mockIntegerRollupListener");
		final SimpleIntegerProperty mockUniqueCount = context.mock(SimpleIntegerProperty.class, "mockUniqueCount");

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
			protected IntegerRollupListener newIntegerRollupListener(FXThreadIntegerProperty prop) {
				return mockIntegerRollupListener;
			}

			@Override
			protected LongRollupListener newLongRollupListener(FXThreadLongProperty prop) {
				return mockLongRollupListener;
			}
		};

		assertTrue((fixture != null) && (fixture instanceof GroupByContentPhase));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#newGroupByContentWorker(java.lang.String, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue)}.
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
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#newIntegerRollupListener(io.github.alantcote.dupfilefinder2.pipeline.SimpleIntegerProperty)}.
	 */
	@Test
	public void testNewIntegerRollupListener() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final FXThreadIntegerProperty mockFXThreadIntegerProperty = context.mock(FXThreadIntegerProperty.class,
				"FXThreadIntegerProperty");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);

		IntegerRollupListener result = fixture.newIntegerRollupListener(mockFXThreadIntegerProperty);

		assertTrue((result != null) && (result instanceof IntegerRollupListener));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#newLongRollupListener(io.github.alantcote.dupfilefinder2.pipeline.SimpleLongProperty)}.
	 */
	@Test
	public void testNewLongRollupListener() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final FXThreadLongProperty mockBytesComparedCount = context.mock(FXThreadLongProperty.class,
				"mockBytesComparedCount");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);

		LongRollupListener result = fixture.newLongRollupListener(mockBytesComparedCount);

		assertTrue((result != null) && (result instanceof LongRollupListener));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#newSimpleIntegerProperty()}.
	 */
	@Test
	public void testNewSimpleIntegerProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);

		SimpleIntegerProperty result = fixture.newThreadSafeSimpleIntegerProperty();

		assertTrue((result != null) && (result instanceof SimpleIntegerProperty));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentPhase#newSimpleLongProperty()}.
	 */
	@Test
	public void testNewSimpleLongProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");

		GroupByContentPhase fixture = new GroupByContentPhase("phase", mockInput, mockOutput);

		SimpleLongProperty result = fixture.newThreadSafeSimpleLongProperty();

		assertTrue((result != null) && (result instanceof SimpleLongProperty));
	}

}
