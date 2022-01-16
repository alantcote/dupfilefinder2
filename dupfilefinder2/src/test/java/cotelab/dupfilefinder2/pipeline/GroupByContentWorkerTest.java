package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

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
 * Test method for {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class GroupByContentWorkerTest {
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#call()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#fileSize(java.nio.file.Path)}.
	 */
	@Test
	public void testFileSize() {
		// don't know how to unit test around static method calls
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#getBytesComparedCount()}.
	 */
	@Test
	public void testGetBytesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final ThreadSafeSimpleLongProperty mockThreadSafeSimpleLongProperty = context
				.mock(ThreadSafeSimpleLongProperty.class, "mockThreadSafeSimpleLongProperty");

		fixture.bytesComparedCount = mockThreadSafeSimpleLongProperty;

		assertEquals(mockThreadSafeSimpleLongProperty, fixture.getBytesComparedCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#getFilesComparedCount()}.
	 */
	@Test
	public void testGetFilesComparedCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final ThreadSafeSimpleIntegerProperty mockThreadSafeSimpleIntegerProperty = context
				.mock(ThreadSafeSimpleIntegerProperty.class, "mockThreadSafeSimpleIntegerProperty");

		fixture.filesComparedCount = mockThreadSafeSimpleIntegerProperty;

		assertEquals(mockThreadSafeSimpleIntegerProperty, fixture.getFilesComparedCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#getUniqueCount()}.
	 */
	@Test
	public void testGetUniqueCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final ThreadSafeSimpleIntegerProperty mockThreadSafeSimpleIntegerProperty = context
				.mock(ThreadSafeSimpleIntegerProperty.class, "mockThreadSafeSimpleIntegerProperty");

		fixture.uniqueCount = mockThreadSafeSimpleIntegerProperty;

		assertEquals(mockThreadSafeSimpleIntegerProperty, fixture.getUniqueCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#GroupByContentWorker(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testGroupByContentWorker() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newBufferedInputStream(java.io.InputStream, int)}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newBufferedInputStreamArray(java.util.Collection)}.
	 */
	@Test
	public void testNewBufferedInputStreamArrayCollectionOfBufferedInputStream() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		final Collection<BufferedInputStream> mockCollection = new ArrayList<BufferedInputStream>();

		assertNotNull(fixture.newBufferedInputStreamArray(mockCollection));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newBufferedInputStreamArray(int)}.
	 */
	@Test
	public void testNewBufferedInputStreamArrayInt() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newBufferedInputStreamArray(42));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newBufferedInputStreamArrayList()}.
	 */
	@Test
	public void testNewBufferedInputStreamArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newBufferedInputStreamArrayList());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newBufferedInputStreamGroupArrayList()}.
	 */
	@Test
	public void testNewBufferedInputStreamGroupArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newBufferedInputStreamGroupArrayList());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newBufferedInputStreamToPathHashMap()}.
	 */
	@Test
	public void testNewBufferedInputStreamToPathHashMap() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newBufferedInputStreamToPathHashMap());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newByteBuffer()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newIntegerToBufferedInputStreamHashMultiMap()}.
	 */
	@Test
	public void testNewIntegerToBufferedInputStreamHashMultiMap() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newIntegerToBufferedInputStreamHashMultiMap());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newPathArrayList()}.
	 */
	@Test
	public void testNewPathArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPathArrayList());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newPathGroupArrayList()}.
	 */
	@Test
	public void testNewPathGroupArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPathGroupArrayList());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newPathInputStream(java.nio.file.Path)}.
	 */
	@Test
	public void testNewPathInputStream() {
		// no ability to unit-test around calls to static methods.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newPathLinkedList()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newThreadSafeSimpleIntegerProperty()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#newThreadSafeSimpleLongProperty()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#nWayCompareEqualPaths(java.util.Collection)}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#nWayCompareEqualStreams(java.util.Collection)}.
	 */
	@Test
	public void testNWayCompareEqualStreams() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		Collection<BufferedInputStream> bufferedInputStreamCollection = new ArrayList<BufferedInputStream>();

		assertNotNull(fixture.nWayCompareEqualStreams(bufferedInputStreamCollection));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#processBatch(java.util.Collection)}.
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
		ThreadSafeSimpleLongProperty bcc = new ThreadSafeSimpleLongProperty(0);

		fixture.bytesComparedCount = bcc;
		pathColl.add(mockPath);

		fixture.processBatch(pathColl);

		assertEquals(0, fixture.bytesComparedCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.GroupByContentWorker#streamsMatch(java.util.Collection)}.
	 */
	@Test
	public void testStreamsMatch() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		GroupByContentWorker fixture = new GroupByContentWorker("phase", mockInput, mockOutput);
		Collection<BufferedInputStream> bufferedInputStreamCollection = new ArrayList<BufferedInputStream>();

		assertFalse(fixture.streamsMatch(bufferedInputStreamCollection));
	}

}
