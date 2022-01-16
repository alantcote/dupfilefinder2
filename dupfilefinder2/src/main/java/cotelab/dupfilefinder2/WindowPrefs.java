package cotelab.dupfilefinder2;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.stage.Stage;

/**
 * Persistent application preferences.
 */
public class WindowPrefs {
	public static final String KEY_WINDOW_HEIGHT = "WINDOW_HEIGHT";
	public static final String KEY_WINDOW_WIDTH = "WINDOW_WIDTH";
	public static final String KEY_WINDOW_X = "WINDOW_X";
	public static final String KEY_WINDOW_Y = "WINDOW_Y";

	protected Class<?> clazz = null;
	protected Preferences prefs = null;
	protected Stage stage = null;

	/**
	 * Constructor.
	 * 
	 * @param aStage The app's @link(Stage).
	 * @throws BackingStoreException if thrown by the underlying code.
	 */
	public WindowPrefs(Class<?> aClazz, Stage aStage) throws BackingStoreException {
		clazz = aClazz;
		stage = aStage;

		establishPreferencesNode();
		inizGeometryPrefs();
	}

	/**
	 * Set up the {@link Preferences} node, {@link #prefs}.
	 * 
	 * @throws BackingStoreException if thrown by the underlying code.
	 */
	protected void establishPreferencesNode() throws BackingStoreException {
		String path = "/" + clazz.getPackageName().replace('.', '/') + "/" + clazz.getSimpleName();
		Preferences base = userRoot();
		
		prefs = prefsNode(path, base);

		prefsSync();
	}

	/**
	 * Initialize the window geometry preferences.
	 * 
	 * Apply the height, width, x and y preferences to the window. If the
	 * preferences don't already exist in the persistent storage, the ones from the
	 * window are stored there.
	 * 
	 * Listeners are established to sense changes to the window geometry and reflect
	 * those changes into the persistent storage.
	 */
	protected void inizGeometryPrefs() {
		if (prefs.getDouble(KEY_WINDOW_HEIGHT, 0) == 0) {
			prefs.putDouble(KEY_WINDOW_HEIGHT, stage.getHeight());
		} else {
			stage.setHeight(prefs.getDouble(KEY_WINDOW_HEIGHT, 0));
		}

		stage.heightProperty().addListener(newMetricListener(KEY_WINDOW_HEIGHT));

		if (prefs.getDouble(KEY_WINDOW_WIDTH, 0) == 0) {
			prefs.putDouble(KEY_WINDOW_WIDTH, stage.getWidth());
		} else {
			stage.setWidth(prefs.getDouble(KEY_WINDOW_WIDTH, 0));
		}

		stage.widthProperty().addListener(newMetricListener(KEY_WINDOW_WIDTH));

		if (prefs.getDouble(KEY_WINDOW_X, 0) == 0) {
			prefs.putDouble(KEY_WINDOW_X, stage.getX());
		} else {
			stage.setX(prefs.getDouble(KEY_WINDOW_X, 0));
		}

		stage.xProperty().addListener(newMetricListener(KEY_WINDOW_X));

		if (prefs.getDouble(KEY_WINDOW_Y, 0) == 0) {
			prefs.putDouble(KEY_WINDOW_Y, stage.getY());
		} else {
			stage.setY(prefs.getDouble(KEY_WINDOW_Y, 0));
		}

		stage.yProperty().addListener(newMetricListener(KEY_WINDOW_Y));

		try {
			prefsSync();
		} catch (BackingStoreException e) {
			System.err.println("AppPrefs.inizGeometryPrefs(): caught . . .");
			e.printStackTrace();
			System.err.println("AppPrefs.inizGeometryPrefs(): proceeding.");
		}
	}

	/**
	 * Create a {@link MetricListener} to maintain preference info with a given key.
	 * 
	 * @param aKey the key under which to record changes.
	 * @return a new object.
	 */
	protected MetricListener newMetricListener(String aKey) {
		return new MetricListener(prefs, aKey);
	}

	/**
	 * Call <code>base.node(path)</code>.
	 * @param path the desired node's path.
	 * @param base the base node to be asked for the desired node.
	 * @return the desired node.
	 */
	protected Preferences prefsNode(String path, Preferences base) {
		return base.node(path);
	}

	/**
	 * @throws BackingStoreException
	 */
	protected void prefsSync() throws BackingStoreException {
		prefs.sync();
	}

	/**
	 * @return the user root {@link Preferences} node.
	 */
	protected Preferences userRoot() {
		return Preferences.userRoot();
	}
}
