package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.jfxrunner.JavaFxJUnit4ClassRunner;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Test case for
 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class ThreadSafeSimpleIntegerPropertyTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#addListener(javafx.beans.value.ChangeListener)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddListenerChangeListenerOfQsuperNumber() {
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		final ChangeListener<? super Number> mockChangeListener = context.mock(ChangeListener.class,
				"mockChangeListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleIntegerProperty).addListener(mockChangeListener);
			}
		});
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42) {

			@Override
			protected SimpleIntegerProperty newSimpleIntegerProperty(int initialValue) {
				return mockSimpleIntegerProperty;
			}

		};

		fixture.addListener(mockChangeListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#addListener(javafx.beans.InvalidationListener)}.
	 */
	@Test
	public void testAddListenerInvalidationListener() {
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		final InvalidationListener mockInvalidationListener = context.mock(InvalidationListener.class,
				"mockInvalidationListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleIntegerProperty).addListener(mockInvalidationListener);
			}
		});
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42) {

			@Override
			protected SimpleIntegerProperty newSimpleIntegerProperty(int initialValue) {
				return mockSimpleIntegerProperty;
			}

		};

		fixture.addListener(mockInvalidationListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#get()}.
	 */
	@Test
	public void testGet() {
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42);

		assertTrue(42 == fixture.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#increment(int)}.
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
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42);

		fixture.addListener(changeListener);
		fixture.increment(7);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#newSimpleIntegerProperty()}.
	 */
	@Test
	public void testNewSimpleIntegerProperty() {
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty();

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleIntegerProperty);

		SimpleIntegerProperty sip = fixture.newSimpleIntegerProperty();

		assertNotNull(sip);
		assertTrue(sip instanceof SimpleIntegerProperty);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#newSimpleIntegerProperty(int)}.
	 */
	@Test
	public void testNewSimpleIntegerPropertyInt() {
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty();

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleIntegerProperty);

		SimpleIntegerProperty sip = fixture.newSimpleIntegerProperty(42);

		assertNotNull(sip);
		assertTrue(sip instanceof SimpleIntegerProperty);
		assertTrue(42 == sip.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#removeListener(javafx.beans.value.ChangeListener)}.
	 */
	@Test
	public void testRemoveListenerChangeListenerOfQsuperNumber() {
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		@SuppressWarnings("unchecked")
		final ChangeListener<? super Number> mockChangeListener = context.mock(ChangeListener.class,
				"mockChangeListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleIntegerProperty).addListener(mockChangeListener);
				oneOf(mockSimpleIntegerProperty).removeListener(mockChangeListener);
			}
		});
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42) {

			@Override
			protected SimpleIntegerProperty newSimpleIntegerProperty(int initialValue) {
				return mockSimpleIntegerProperty;
			}

		};

		fixture.addListener(mockChangeListener);
		fixture.removeListener(mockChangeListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#removeListener(javafx.beans.InvalidationListener)}.
	 */
	@Test
	public void testRemoveListenerInvalidationListener() {
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		final InvalidationListener mockInvalidationListener = context.mock(InvalidationListener.class,
				"mockInvalidationListener");

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleIntegerProperty).addListener(mockInvalidationListener);
				oneOf(mockSimpleIntegerProperty).removeListener(mockInvalidationListener);
			}
		});
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42) {

			@Override
			protected SimpleIntegerProperty newSimpleIntegerProperty(int initialValue) {
				return mockSimpleIntegerProperty;
			}

		};

		fixture.addListener(mockInvalidationListener);
		fixture.removeListener(mockInvalidationListener);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#set(int)}.
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
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42);

		fixture.addListener(changeListener);
		fixture.set(7);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#setValue(java.lang.Number)}.
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
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42);
		Number seven = 7;

		fixture.addListener(changeListener);
		fixture.setValue(seven);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#ThreadSafeSimpleIntegerProperty()}.
	 */
	@Test
	public void testThreadSafeSimpleIntegerProperty() {
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty();

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleIntegerProperty);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#ThreadSafeSimpleIntegerProperty(int)}.
	 */
	@Test
	public void testThreadSafeSimpleIntegerPropertyInt() {
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42);

		assertNotNull(fixture);
		assertTrue(fixture instanceof ThreadSafeSimpleIntegerProperty);
		assertTrue(42 == fixture.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.ThreadSafeSimpleIntegerProperty#toString()}.
	 */
	@Test
	public void testToString() {
		ThreadSafeSimpleIntegerProperty fixture = new ThreadSafeSimpleIntegerProperty(42);

//		System.out.println("ThreadSafeSimpleIntegerTest.testToString(): fixture.toString() = \"" + fixture.toString() + "\"");
		assertEquals("IntegerProperty [value: 42]", fixture.toString());
	}

}
