package io.github.alantcote.dupfilefinder2.pipeline.queueing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue;
import javafx.beans.property.SimpleStringProperty;

/**
 * Test case for {@link PipelineQueue}.
 */
public class PipelineQueueTest extends TestCaseWithJMockAndByteBuddy {
	public static final int QUEUE_CAPACITY = 42;
	public static final String QUEUE_NAME = "test queue name";

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue#getName()}.
	 */
	@Test
	public void testGetName() {
		PipelineQueue fixture = new PipelineQueue(QUEUE_CAPACITY, QUEUE_NAME);

		assertEquals(QUEUE_NAME, fixture.getName().get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue#newSimpleStringProperty()}.
	 */
	@Test
	public void testNewSimpleStringProperty() {
		PipelineQueue fixture = new PipelineQueue(QUEUE_CAPACITY, QUEUE_NAME);
		SimpleStringProperty result = fixture.newSimpleStringProperty();

		assertTrue((result != null) && (result instanceof SimpleStringProperty));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue#PipelineQueue(int, java.lang.String)}.
	 */
	@Test
	public void testPipelineQueue() {
		PipelineQueue fixture = new PipelineQueue(QUEUE_CAPACITY, QUEUE_NAME);

		assertEquals(QUEUE_NAME, fixture.name.get());
	}

}
