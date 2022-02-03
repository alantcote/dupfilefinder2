package io.github.alantcote.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import io.github.alantcote.dupfilefinder2.beans.property.FXThreadIntegerProperty;
import io.github.alantcote.dupfilefinder2.beans.property.FXThreadLongProperty;
import io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker;
import io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.application.Platform;

/**
 * Test method for {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker}.
 */
@RunWith(JfxRunner.class)
public class GroupByContentWorkerTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#call()}.
	 * 
	 * @throws Exception if thrown by the code under test.
	 */
	@Test
	public void testCall() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		ArrayList<Path> batch = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockInput).take();
				will(returnValue(batch));
			}
		});

		assertNull(fixture.call());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#fileSize(java.nio.file.Path)}.
	 */
	@Test
	public void testFileSize() {
		// don't know how to unit test around static method calls
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#getBytesComparedCount()}.
	 */
	@Test
	public void testGetBytesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final FXThreadLongProperty mockFXThreadLongProperty = context.mock(FXThreadLongProperty.class,
				"mockFXThreadLongProperty");

		fixture.bytesComparedCount = mockFXThreadLongProperty;

		assertEquals(mockFXThreadLongProperty, fixture.getBytesComparedCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#getFilesComparedCount()}.
	 */
	@Test
	public void testGetFilesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final FXThreadIntegerProperty mockFXThreadIntegerProperty = context.mock(FXThreadIntegerProperty.class,
				"mockFXThreadIntegerProperty");

		fixture.filesComparedCount = mockFXThreadIntegerProperty;

		assertEquals(mockFXThreadIntegerProperty, fixture.getFilesComparedCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#getUniqueCount()}.
	 */
	@Test
	public void testGetUniqueCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final FXThreadIntegerProperty mockFXThreadIntegerProperty = context.mock(FXThreadIntegerProperty.class,
				"mockFXThreadIntegerProperty");

		fixture.uniqueCount = mockFXThreadIntegerProperty;

		assertEquals(mockFXThreadIntegerProperty, fixture.getUniqueCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#GroupByContentWorker(java.lang.String, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue)}.
	 */
	@Test
	public void testGroupByContentWorker() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.getInputQueue());
		assertEquals(mockOutput, fixture.getOutputQueue());
		assertEquals("phase", fixture.getPhaseName().get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newBufferedInputStream(java.io.InputStream, int)}.
	 * 
	 * @throws IOException if the code under test throws one.
	 */
	@Test
	public void testNewBufferedInputStream() throws IOException {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final InputStream mockInputStream = context.mock(InputStream.class, "mockInputStream");

		assertNotNull(fixture.newBufferedInputStream(mockInputStream, 42));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newInputStreamArray(java.util.Collection)}.
	 */
	@Test
	public void testNewBufferedInputStreamArrayCollectionOfBufferedInputStream() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final Collection<InputStream> mockCollection = new ArrayList<InputStream>();

		assertNotNull(fixture.newArrayOfInputStream(mockCollection));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newInputStreamArray(int)}.
	 */
	@Test
	public void testNewBufferedInputStreamArrayInt() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newInputStreamArray(42));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newInputStreamCollection()}.
	 */
	@Test
	public void testNewBufferedInputStreamArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newInputStreamCollection());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newInputStreamGroupCollection()}.
	 */
	@Test
	public void testNewBufferedInputStreamGroupArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newInputStreamGroupCollection());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newInputStreamToPathMap()}.
	 */
	@Test
	public void testNewBufferedInputStreamToPathHashMap() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newInputStreamToPathMap());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newByteBuffer()}.
	 */
	@Test
	public void testNewByteBuffer() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newByteBuffer());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newIntegerToInputStreamMultiValuedMap()}.
	 */
	@Test
	public void testNewIntegerToBufferedInputStreamHashMultiMap() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newIntegerToInputStreamMultiValuedMap());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newPathLinkedList()}.
	 */
	@Test
	public void testNewPathArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPathLinkedList());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newPathGroupCollection()}.
	 */
	@Test
	public void testNewPathGroupArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPathGroupCollection());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newPathInputStream(java.nio.file.Path)}.
	 */
	@Test
	public void testNewPathInputStream() {
		// no ability to unit-test around calls to static methods.
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newPathLinkedList()}.
	 */
	@Test
	public void testNewPathLinkedList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPathLinkedList());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newThreadSafeSimpleIntegerProperty()}.
	 */
	@Test
	public void testNewThreadSafeSimpleIntegerProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newThreadSafeSimpleIntegerProperty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#newThreadSafeSimpleLongProperty()}.
	 */
	@Test
	public void testNewThreadSafeSimpleLongProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newThreadSafeSimpleLongProperty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#nWayCompareEqualPaths(java.util.Collection)}.
	 */
	@Test
	public void testNWayCompareEqualPaths() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		Collection<Path> pathCollection = new ArrayList<Path>();

		assertNotNull(fixture.nWayCompareEqualPaths(pathCollection));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#nWayCompareEqualStreams(java.util.Collection)}.
	 */
	@Test
	public void testNWayCompareEqualStreams() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		Collection<InputStream> InputStreamCollection = new ArrayList<InputStream>();

		assertNotNull(fixture.nWayCompareEqualStreams(InputStreamCollection));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#processBatch(java.util.Collection)}.
	 * 
	 * @throws Exception if thrown by code under test.
	 */
	@Test
	public void testProcessBatch() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput) {

			@Override
			protected long fileSize(Path path) throws IOException {
				return 1;
			}

		};
		Collection<Path> pathColl = new ArrayList<Path>();
		final Path mockPath = context.mock(Path.class, "mockPath");
		FXThreadLongProperty bcc = new FXThreadLongProperty(0);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				fixture.bytesComparedCount = bcc;
				pathColl.add(mockPath);

				try {
					fixture.processBatch(pathColl);
				} catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
					assertNull(e.getMessage());
				}

				assertEquals(1, fixture.bytesComparedCount.get());
			}
			
		});

	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.GroupByContentWorker#streamsMatch(java.util.Collection)}.
	 */
	@Test
	public void testStreamsMatch() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		Collection<InputStream> inputStreamCollection = new ArrayList<InputStream>();

		assertFalse(fixture.streamsMatch(inputStreamCollection));
	}

}
