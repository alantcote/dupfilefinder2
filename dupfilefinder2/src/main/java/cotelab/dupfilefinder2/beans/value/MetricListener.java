package cotelab.dupfilefinder2.beans.value;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A {@link ChangeListener} that applies changes to a {@link Preferences}
 * preference with a given key.
 */
public class MetricListener implements ChangeListener<Number> {
	/**
	 * The preference key.
	 */
	protected String key = null;

	/**
	 * The {@link Preferences} node.
	 */
	protected Preferences prefs = null;

	/**
	 * Construct a new object.
	 * 
	 * @param aPrefs the {@link Preferences} node to update.
	 * @param aKey   the preference key.
	 */
	public MetricListener(Preferences aPrefs, String aKey) {
		prefs = aPrefs;
		key = aKey;
	}

	/**
	 * {@inheritDoc} This method updates the preference to reflect the new value.
	 */
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		prefsPutDouble(newValue.doubleValue());

		prefsSync();
	}

	/**
	 * Put a preference in {@link #prefs};
	 * 
	 * @param newValue the new value of the preference.
	 */
	protected void prefsPutDouble(double newValue) {
		prefs.putDouble(key, newValue);
	}

	/**
	 * Call <code>prefs.sync()</code>.
	 */
	protected void prefsSync() {
		try {
			prefs.sync();
		} catch (BackingStoreException e) {
			System.err.println("AppPrefs.MetricListener.prefsSync(): caught:" + e.getMessage());
			e.printStackTrace();
			System.err.println("AppPrefs.MetricListener.prefsSync(): proceeding anyway.");
		}
	}
}
