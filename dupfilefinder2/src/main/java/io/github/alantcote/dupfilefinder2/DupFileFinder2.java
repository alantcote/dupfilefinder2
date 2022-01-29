package io.github.alantcote.dupfilefinder2;

import java.io.IOException;
import java.util.prefs.BackingStoreException;

import io.github.alantcote.clutilities.javafx.windowprefs.WindowPrefs;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main class.
 */
public class DupFileFinder2 extends Application {
	/**
	 * The {@link Stage}.
	 */
	private static Stage stage;
	/**
	 * The program entry point.
	 * 
	 * @param args the command-line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Create a new {@link Parent} by loading fxml.
	 * 
	 * @param fxml the fxml to load.
	 * @return the new object.
	 * @throws IOException if any is thrown by the underlying code.
	 */
	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(DupFileFinder2.class.getResource("/fxml/" + fxml + ".fxml"));
		return fxmlLoader.load();
	}

	/**
	 * Set the root node.
	 * 
	 * @param fxml the fxml to be used to assemble the root node.
	 * @throws IOException if any is thrown by the underlying code.
	 */
	static void setRoot(String fxml) throws IOException {
		setRoot(fxml, "dupfilefinder2");
	}

	/**
	 * Set the root node and window title.
	 * 
	 * @param fxml  the fxml to be used to assemble the root node.
	 * @param title the window title.
	 * @throws IOException if any is thrown by the underlying code.
	 */
	static void setRoot(String fxml, String title) throws IOException {
		Scene scene = new Scene(loadFXML(fxml));
		stage.setTitle(title);
		stage.setScene(scene);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}

		});

		stage.show();
	}

	protected WindowPrefs windowPrefs = null;

	/**
	 * {@inheritDoc}
	 * @throws BackingStoreException if thrown.
	 */
	@Override
	public void start(Stage s) throws IOException, BackingStoreException {
		stage = s;
		setRoot("primary", "dupfilefinder2");

		windowPrefs = new WindowPrefs(getClass(), stage);
	}

}
