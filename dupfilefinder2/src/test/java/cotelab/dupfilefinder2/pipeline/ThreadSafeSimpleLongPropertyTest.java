package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sf.cotelab.jfxrunner.JavaFxJUnit4ClassRunner;

/**
 * Test case for
 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class ThreadSafeSimpleLongPropertyTest {
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
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#addListener(javafx.beans.value.ChangeListener)}.
	 */
	@Test
	public void testAddListenerChangeListenerOfQsuperNumber() {
		final SimpleLongProperty mockSimpleLongProperty = context.mock(SimpleLongProperty.class,
				"mockSimpleLongProperty");
		@SuppressWarnings("unchecked")
		final ChangeListener<? super Number> mockChangeListener = context.mock(ChangeListener.class,
				"mockChangeListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleLongProperty).addListener(mockChangeListener);
			}
		});
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42) {

			@Override
			protected SimpleLongProperty newSimpleLongProperty(long initialValue) {
				return mockSimpleLongProperty;
			}

		};

		fixture.addListener(mockChangeListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#addListener(javafx.beans.InvalidationListener)}.
	 */
	@Test
	public void testAddListenerInvalidationListener() {
		final SimpleLongProperty mockSimpleLongProperty = context.mock(SimpleLongProperty.class,
				"mockSimpleLongProperty");
		final InvalidationListener mockInvalidationListener = context.mock(InvalidationListener.class,
				"mockInvalidationListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleLongProperty).addListener(mockInvalidationListener);
			}
		});
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42) {

			@Override
			protected SimpleLongProperty newSimpleLongProperty(long initialValue) {
				return mockSimpleLongProperty;
			}

		};

		fixture.addListener(mockInvalidationListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#get()}.
	 */
	@Test
	public void testGet() {
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42);

		assertTrue(42 == fixture.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#increment(long)}.
	 */
	@Test
	public void testIncrement() {
		final ChangeListener<? super Number> changeListener = new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				assertTrue(Platform.isFxApplicationThread());
				assertEquals(oldValue.intValue() + 7, newValue.intValue());
			}

		};
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42);

		fixture.addListener(changeListener);
		fixture.increment(7);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#newSimpleLongProperty()}.
	 */
	@Test
	public void testNewSimpleLongProperty() {
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty();

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleLongProperty);

		SimpleLongProperty sip = fixture.newSimpleLongProperty();

		assertNotNull(sip);
		assertTrue(sip instanceof SimpleLongProperty);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#newSimpleLongProperty(long)}.
	 */
	@Test
	public void testNewSimpleLongPropertyLong() {
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty();

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleLongProperty);

		SimpleLongProperty sip = fixture.newSimpleLongProperty(42);

		assertNotNull(sip);
		assertTrue(sip instanceof SimpleLongProperty);
		assertTrue(42 == sip.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#removeListener(javafx.beans.value.ChangeListener)}.
	 */
	@Test
	public void testRemoveListenerChangeListenerOfQsuperNumber() {
		final SimpleLongProperty mockSimpleLongProperty = context.mock(SimpleLongProperty.class,
				"mockSimpleLongProperty");
		@SuppressWarnings("unchecked")
		final ChangeListener<? super Number> mockChangeListener = context.mock(ChangeListener.class,
				"mockChangeListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleLongProperty).addListener(mockChangeListener);
				oneOf(mockSimpleLongProperty).removeListener(mockChangeListener);
			}
		});
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42) {

			@Override
			protected SimpleLongProperty newSimpleLongProperty(long initialValue) {
				return mockSimpleLongProperty;
			}

		};

		fixture.addListener(mockChangeListener);
		fixture.removeListener(mockChangeListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#removeListener(javafx.beans.InvalidationListener)}.
	 */
	@Test
	public void testRemoveListenerInvalidationListener() {
		final SimpleLongProperty mockSimpleLongProperty = context.mock(SimpleLongProperty.class,
				"mockSimpleLongProperty");
		final InvalidationListener mockInvalidationListener = context.mock(InvalidationListener.class,
				"mockInvalidationListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleLongProperty).addListener(mockInvalidationListener);
				oneOf(mockSimpleLongProperty).removeListener(mockInvalidationListener);
			}
		});
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42) {

			@Override
			protected SimpleLongProperty newSimpleLongProperty(long initialValue) {
				return mockSimpleLongProperty;
			}

		};

		fixture.addListener(mockInvalidationListener);
		fixture.removeListener(mockInvalidationListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#set(long)}.
	 */
	@Test
	public void testSet() {
		final ChangeListener<? super Number> changeListener = new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				assertTrue(Platform.isFxApplicationThread());
				assertEquals(oldValue.intValue(), 42);
				assertEquals(7, newValue.intValue());
			}

		};
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42);

		fixture.addListener(changeListener);
		fixture.set(7);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#setValue(java.lang.Number)}.
	 */
	@Test
	public void testSetValue() {
		final ChangeListener<? super Number> changeListener = new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				assertTrue(Platform.isFxApplicationThread());
				assertEquals(oldValue.intValue(), 42);
				assertEquals(7, newValue.intValue());
			}

		};
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42);
		Number seven = 7;

		fixture.addListener(changeListener);
		fixture.setValue(seven);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#ThreadSafeSimpleLongProperty()}.
	 */
	@Test
	public void testThreadSafeSimpleLongProperty() {
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty();

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleLongProperty);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleLongProperty#ThreadSafeSimpleLongProperty(long)}.
	 */
	@Test
	public void testThreadSafeSimpleLongPropertyLong() {
		ThreadSafeSimpleLongProperty fixture = new ThreadSafeSimpleLongProperty(42);

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleLongProperty);
		assertTrue(42 == fixture.get());
	}

}
