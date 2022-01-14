package cotelab.dupfilefinder2;

import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import net.sf.cotelab.jfxrunner.JavaFxJUnit4ClassRunner;

/**
 * Test case for {@link NumberPropToLabelBinder}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class NumberPropToLabelBinderTest {
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
	 * {@link cotelab.dupfilefinder2.NumberPropToLabelBinder#changed(javafx.beans.value.ObservableValue, java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testChanged() {
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		NumberPropToLabelBinder fixture = new NumberPropToLabelBinder(mockLabel, mockFXMLController) {
			@Override
			protected void updateInFXThread(Label field, Number newValue) {
				assertTrue(mockLabel == field);
				assertTrue(42 == newValue.intValue());
			}
		};

		fixture.changed(mockSimpleIntegerProperty, 7, 42);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.NumberPropToLabelBinder#NumberPropToLabelBinder(javafx.scene.control.Label, cotelab.dupfilefinder2.FXMLController)}.
	 */
	@Test
	public void testNumberPropToLabelBinder() {
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		NumberPropToLabelBinder fixture = new NumberPropToLabelBinder(mockLabel, mockFXMLController);

		assertTrue(mockLabel == fixture.label);
		assertTrue(mockFXMLController == fixture.controller);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.NumberPropToLabelBinder#platformRunLater(java.lang.Runnable)}.
	 */
	@Test
	public void testPlatformRunLater() {
		// I know of no way to test whether Platform.runLater() actually was called.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.NumberPropToLabelBinder#updateInFXThread(javafx.scene.control.Label, java.lang.Number)}.
	 */
	@Test
	public void testUpdateInFXThread() {
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		NumberPropToLabelBinder fixture = new NumberPropToLabelBinder(mockLabel, mockFXMLController) {

			@Override
			protected void platformRunLater(Runnable aRunnable) {
				assertTrue(null != aRunnable);
			}

		};

		fixture.updateInFXThread(mockLabel, 42);
	}

}
