package cotelab.dupfilefinder2;

import static org.junit.Assert.*;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.jfxrunner.JavaFxJUnit4ClassRunner;
import javafx.scene.control.Label;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;

/**
 * Test case for {@link NumberPropToLabelBinder}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class StatePropToLabelBinderTest {
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
	 * Test method for {@link cotelab.dupfilefinder2.StatePropToLabelBinder#StatePropToLabelBinder(javafx.scene.control.Label, cotelab.dupfilefinder2.FXMLController)}.
	 */
	@Test
	public void testStatePropToLabelBinder() {
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		StatePropToLabelBinder fixture = new StatePropToLabelBinder(mockLabel, mockFXMLController);

		assertTrue(mockLabel == fixture.label);
		assertTrue(mockFXMLController == fixture.controller);
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.StatePropToLabelBinder#changed(javafx.beans.value.ObservableValue, javafx.concurrent.Worker.State, javafx.concurrent.Worker.State)}.
	 */
	@Test
	public void testChanged() {
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		@SuppressWarnings("unchecked")
		final ObservableValue<State> mockObservableValue_State = context.mock(ObservableValue.class, "mockObservableValue_State");
		StatePropToLabelBinder fixture = new StatePropToLabelBinder(mockLabel, mockFXMLController){
			@Override
			protected void updateInFXThread(Label field, State newValue) {
				assertTrue(mockLabel == field);
				assertTrue(State.SUCCEEDED == newValue);
			}
		};

		fixture.changed(mockObservableValue_State, State.RUNNING, State.SUCCEEDED);
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.StatePropToLabelBinder#updateInFXThread(javafx.scene.control.Label, javafx.concurrent.Worker.State)}.
	 */
	@Test
	public void testUpdateInFXThread() {
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		StatePropToLabelBinder fixture = new StatePropToLabelBinder(mockLabel, mockFXMLController){

			@Override
			protected void platformRunLater(Runnable aRunnable) {
				assertTrue(null != aRunnable);
			}

		};

		fixture.updateInFXThread(mockLabel, State.SUCCEEDED);
	}

}
