package io.github.alantcote.dupfilefinder2.beans.value;

import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.junit.Test;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import io.github.alantcote.dupfilefinder2.beans.property.FXThreadLongProperty;
import io.github.alantcote.dupfilefinder2.beans.value.LongRollupListener;
import javafx.beans.property.SimpleLongProperty;

/**
 * Test case for {@link LongRollupListener}.
 */
public class LongRollupListenerTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.beans.value.LongRollupListener#changed(javafx.beans.value.ObservableValue, java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testChanged() {
		final FXThreadLongProperty mockProp = context.mock(FXThreadLongProperty.class, "mockProp");
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
	 * {@link io.github.alantcote.dupfilefinder2.beans.value.LongRollupListener#LongRollupListener(io.github.alantcote.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty)}.
	 */
	@Test
	public void testLongRollupListener() {
		final FXThreadLongProperty mockProp = context.mock(FXThreadLongProperty.class, "mockProp");
		LongRollupListener fixture = new LongRollupListener(mockProp);

		assertTrue(mockProp == fixture.prop);
	}

}