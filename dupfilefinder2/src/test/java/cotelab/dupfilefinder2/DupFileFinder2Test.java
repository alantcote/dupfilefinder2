package cotelab.dupfilefinder2;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.jfxrunner.JavaFxJUnit4ClassRunner;

/**
 * Test case for {@link DupFileFinder2}.
 * 
 * This class was generated automatically; assuming it is correct.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class DupFileFinder2Test {
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
	 * {@link cotelab.dupfilefinder2.DupFileFinder2#main(java.lang.String[])}.
	 */
	@Test
	public void testMain() {
		// I don't know of a reliable way to unit-test static methods.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.DupFileFinder2#setRoot(java.lang.String)}.
	 */
	@Test
	public void testSetRootString() {
		// I don't know of a reliable way to unit-test static methods.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.DupFileFinder2#setRoot(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetRootStringString() {
		// I don't know of a reliable way to unit-test static methods.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.DupFileFinder2#start(javafx.stage.Stage)}.
	 */
	@Test
	public void testStartStage() {
		// I don't know of a reliable way to unit-test static methods.
	}

}
