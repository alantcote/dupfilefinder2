package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Test case for {@link IntegerRollupListener}.
 */
public class IntegerRollupListenerTest {
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
	 * {@link cotelab.dupfilefinder2.pipeline.IntegerRollupListener#changed(javafx.beans.value.ObservableValue, java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testChanged() {
		final ThreadSafeSimpleIntegerProperty mockProp = context.mock(ThreadSafeSimpleIntegerProperty.class,
				"mockProp");
		IntegerRollupListener fixture = new IntegerRollupListener(mockProp);
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");

		context.checking(new Expectations() {
			{
				oneOf(mockProp).increment(5);
			}
		});

		fixture.changed(mockSimpleIntegerProperty, 10, 15);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.IntegerRollupListener#IntegerRollupListener(cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty)}.
	 */
	@Test
	public void testIntegerRollupListener() {
		final ThreadSafeSimpleIntegerProperty mockProp = context.mock(ThreadSafeSimpleIntegerProperty.class,
				"mockProp");
		IntegerRollupListener fixture = new IntegerRollupListener(mockProp);

		assertTrue(mockProp == fixture.prop);
	}

}
