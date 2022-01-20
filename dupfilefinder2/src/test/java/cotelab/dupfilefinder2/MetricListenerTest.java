/**
 * 
 */
package cotelab.dupfilefinder2;

import static org.junit.Assert.assertEquals;

import java.util.prefs.Preferences;

import org.junit.Test;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Test case for {@link MetricListener}.
 */
public class MetricListenerTest extends TestCaseWithJMockAndByteBuddy {
	public static final String KEY = "preference";
	public static final Preferences NODE = Preferences.userRoot();

	/**
	 * Test method for {@link cotelab.dupfilefinder2.MetricListener#MetricListener(java.util.prefs.Preferences, java.lang.String)}.
	 */
	@Test
	public void testMetricListener() {
		MetricListener fixture = new MetricListener(NODE, KEY);
		
		assertEquals(NODE, fixture.prefs);
		assertEquals(KEY, fixture.key);
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.MetricListener#changed(javafx.beans.value.ObservableValue, java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testChanged() {
		final SimpleIntegerProperty ppdCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty psCount = new SimpleIntegerProperty(0);
		MetricListener fixture = new MetricListener(NODE, KEY) {
			@Override
			protected void prefsPutDouble(double newValue) {
				ppdCount.set(1 + ppdCount.get());
			}

			@Override
			protected void prefsSync() {
				psCount.set(1 + psCount.get());
			}
		};
		
		fixture.changed(null, 0, 1);
		
		assertEquals(1, ppdCount.get());
		assertEquals(1, psCount.get());
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.MetricListener#prefsPutDouble(double)}.
	 */
	@Test
	public void testPrefsPutDouble() {
		// haven't managed to make a mock of Preferences, so punt.
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.MetricListener#prefsSync()}.
	 */
	@Test
	public void testPrefsSync() {
		// haven't managed to make a mock of Preferences, so punt.
	}

}
