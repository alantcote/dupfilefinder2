package cotelab.dupfilefinder2.treeview;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import cotelab.dupfilefinder2.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowDuplicatesMenuItemEventHandler implements EventHandler<ActionEvent> {
	/**
	 * The controller.
	 */
	protected FXMLController controller = null;

	/**
	 * The collection of duplicate path groups.
	 */
	protected Collection<Collection<Path>> dupCollections = null;

	/**
	 * The path of interest.
	 */
	protected Path path;

	/**
	 * The map from path to duplicate paths.
	 */
	protected Map<Path, Collection<Path>> pathToDupCollMap = null;

	/**
	 * Construct a new object.
	 * 
	 * @param aPath             a path of interest.
	 * @param aPathToDupCollMap a map from path to duplicate paths.
	 * @param aDupCollections   a collection of duplicate path groups.
	 * @param aController       a controller.
	 */
	public ShowDuplicatesMenuItemEventHandler(Path aPath, Map<Path, Collection<Path>> aPathToDupCollMap,
			Collection<Collection<Path>> aDupCollections, FXMLController aController) {
		path = aPath;
		pathToDupCollMap = aPathToDupCollMap;
		dupCollections = aDupCollections;
		controller = aController;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionEvent event) {
		showDupCollection(path);
	}

	/**
	 * Create a new object.
	 * 
	 * @return the new object.
	 */
	protected DupItemsDialog newDupItemsDialog() {
		return new DupItemsDialog(path, pathToDupCollMap, dupCollections, controller);
	}

	/**
	 * Display a dialog listing the duplicate paths for a given path.
	 * 
	 * @param path the given path.
	 */
	protected void showDupCollection(Path path) {
		DupItemsDialog thePopup = newDupItemsDialog();

		thePopup.showAndWait();
	}
}
