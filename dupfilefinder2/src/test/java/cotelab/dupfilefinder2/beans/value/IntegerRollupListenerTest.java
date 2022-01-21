package cotelab.dupfilefinder2.beans.value;

import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.junit.Test;

import cotelab.dupfilefinder2.beans.property.FXThreadIntegerProperty;
import cotelab.dupfilefinder2.beans.value.IntegerRollupListener;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Test case for {@link IntegerRollupListener}.
 */
public class IntegerRollupListenerTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.beans.value.IntegerRollupListener#changed(javafx.beans.value.ObservableValue, java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testChanged() {
		final FXThreadIntegerProperty mockProp = context.mock(FXThreadIntegerProperty.class, "mockProp");
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
	 * {@link cotelab.dupfilefinder2.beans.value.IntegerRollupListener#IntegerRollupListener(cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty)}.
	 */
	@Test
	public void testIntegerRollupListener() {
		final FXThreadIntegerProperty mockProp = context.mock(FXThreadIntegerProperty.class, "mockProp");
		IntegerRollupListener fixture = new IntegerRollupListener(mockProp);

		assertTrue(mockProp == fixture.prop);
	}

}
