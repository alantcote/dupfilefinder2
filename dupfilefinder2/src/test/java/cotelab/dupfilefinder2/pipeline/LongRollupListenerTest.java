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

import javafx.beans.property.SimpleLongProperty;
import junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for {@link LongRollupListener}.
 */
public class LongRollupListenerTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.LongRollupListener#changed(javafx.beans.value.ObservableValue, java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testChanged() {
		final ThreadSafeSimpleLongProperty mockProp = context.mock(ThreadSafeSimpleLongProperty.class, "mockProp");
		LongRollupListener fixture = new LongRollupListener(mockProp);
		final SimpleLongProperty mockSimpleLongProperty = context.mock(SimpleLongProperty.class,
				"mockSimpleLongProperty");

		context.checking(new Expectations() {
			{
				oneOf(mockProp).increment(5);
			}
		});

		fixture.changed(mockSimpleLongProperty, 10, 15);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.LongRollupListener#LongRollupListener(cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty)}.
	 */
	@Test
	public void testLongRollupListener() {
		final ThreadSafeSimpleLongProperty mockProp = context.mock(ThreadSafeSimpleLongProperty.class, "mockProp");
		LongRollupListener fixture = new LongRollupListener(mockProp);

		assertTrue(mockProp == fixture.prop);
	}

}
