package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for {@link cotelab.dupfilefinder2.pipeline.Phase}.
 */
public class PhaseTest extends TestCaseWithJMockAndByteBuddy {
	protected class ConcretePhase extends Phase {

		public ConcretePhase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
			super(name, theInput, theOutput);
		}

	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.pipeline.Phase#call()}.
	 * 
	 * @throws Exception if thrown by the code under test.
	 */
	@Test
	public void testCall() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		assertNull(fixture.call());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#cancel(boolean)}.
	 */
	@Test
	public void testCancelBoolean() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		assertTrue(fixture.cancel(false));
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.pipeline.Phase#getInputName()}.
	 */
	@Test
	public void testGetInputName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockInput).getName();
				will(returnValue(mockSimpleStringProperty));
			}
		});

		assertEquals(mockSimpleStringProperty, fixture.getInputName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#getInputPutCount()}.
	 */
	@Test
	public void testGetInputPutCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockInput).getPutCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		assertEquals(mockSimpleIntegerProperty, fixture.getInputPutCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#getInputQueue()}.
	 */
	@Test
	public void testGetInputQueue() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.getInputQueue());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#getInputTakeCount()}.
	 */
	@Test
	public void testGetInputTakeCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockInput).getTakeCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		assertEquals(mockSimpleIntegerProperty, fixture.getInputTakeCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#getOutputName()}.
	 */
	@Test
	public void testGetOutputName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockOutput).getName();
				will(returnValue(mockSimpleStringProperty));
			}
		});

		assertEquals(mockSimpleStringProperty, fixture.getOutputName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#getOutputPutCount()}.
	 */
	@Test
	public void testGetOutputPutCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockOutput).getPutCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		assertEquals(mockSimpleIntegerProperty, fixture.getOutputPutCount());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#getOutputQueue()}.
	 */
	@Test
	public void testGetOutputQueue() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		assertEquals(mockOutput, fixture.getOutputQueue());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#getOutputTakeCount()}.
	 */
	@Test
	public void testGetOutputTakeCount() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockOutput).getTakeCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		assertEquals(mockSimpleIntegerProperty, fixture.getOutputTakeCount());
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.pipeline.Phase#getPhaseName()}.
	 */
	@Test
	public void testGetPhaseName() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		assertEquals(fixture.phaseName, fixture.getPhaseName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#newPhaseArrayList()}.
	 */
	@Test
	public void testNewPhaseArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		assertNotNull(fixture.newPhaseArrayList());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#newThread(cotelab.dupfilefinder2.pipeline.Phase)}.
	 */
	@Test
	public void testNewThread() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);
		final Phase mockPhase = context.mock(Phase.class, "mockPhase");

		assertNotNull(fixture.newThread(mockPhase));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#Phase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		assertEquals(mockInput, fixture.inputQueue);
		assertEquals(mockOutput, fixture.outputQueue);
		assertEquals("phase", fixture.phaseName.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#setInputQueue(cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testSetInputQueue() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		try {
			fixture.setInputQueue(mockInput);

			fail("An exception was expected!");
		} catch (UnsupportedOperationException e) {
			// this is the expected result
		}
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#setOutputQueue(cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
	 */
	@Test
	public void testSetOutputQueue() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		try {
			fixture.setOutputQueue(mockOutput);

			fail("An exception was expected!");
		} catch (UnsupportedOperationException e) {
			// this is the expected result
		}
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#startChildren()}.
	 */
	@Test
	public void testStartChildren() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		fixture.startChildren();
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.pipeline.Phase#threadSleep()}.
	 * 
	 * @throws InterruptedException if thrown by the code under test.
	 */
	@Test
	public void testThreadSleep() throws InterruptedException {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);
		long startStamp = new Date().getTime();

		fixture.threadSleep();

		long endStamp = new Date().getTime();
		long elapsed = endStamp - startStamp;

		assertTrue(9 < elapsed);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.Phase#waitForChildrenDone()}.
	 */
	@Test
	public void testWaitForChildrenDone() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		Phase fixture = new ConcretePhase("phase", mockInput, mockOutput);

		fixture.waitForChildrenDone();
	}

}
