package cotelab.dupfilefinder2.treeview;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import cotelab.dupfilefinder2.DupFileFinder2;
import cotelab.dupfilefinder2.FXMLController;
import io.github.alantcote.clutilities.javafx.scene.control.FileIconFactory;
import io.github.alantcote.clutilities.javafx.scene.control.FileTreeCell;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * A {@link FileTreeCell} decorated to present {@link DupFileFinder2} results.
 */
public class DecoratedFileTreeCell extends FileTreeCell {
	/**
	 * The ancestor set.
	 */
	protected Set<Path> ancestorSet = null;

	/**
	 * The controller.
	 */
	protected FXMLController controller = null;

	/**
	 * The collection of duplicate path groups.
	 */
	protected Collection<Collection<Path>> dupCollections = null;

	/**
	 * The map from path to duplicate paths.
	 */
	protected Map<Path, Collection<Path>> pathToDupCollMap = null;

	/**
	 * Construct a new object.
	 * 
	 * @param aFileIconFactory  a factory for icons.
	 * @param ancestorSet2      a set of ancestors.
	 * @param aDupCollections   a collection of duplicate path groups.
	 * @param aPathToDupCollMap a map from paths to duplicate paths.
	 * @param aController       a controller.
	 */
	public DecoratedFileTreeCell(FileIconFactory aFileIconFactory, Set<Path> ancestorSet2,
			Collection<Collection<Path>> aDupCollections, Map<Path, Collection<Path>> aPathToDupCollMap,
			FXMLController aController) {
		super(aFileIconFactory);

		ancestorSet = ancestorSet2;
		dupCollections = aDupCollections;
		pathToDupCollMap = aPathToDupCollMap;
		controller = aController;
	}

	/**
	 * Disable the context menu.
	 */
	protected void disableContextMenu() {
		onContextMenuRequestedProperty().set(null);
	}

	/**
	 * Enable the context menu.
	 * 
	 * @param path
	 */
	protected void enableContextMenu(Path path) {
		onContextMenuRequestedProperty().set(newContextMenuRequestHandler(path));
	}

	/**
	 * @param path
	 * @return
	 */
	protected CellContextMenuRequestHandler newContextMenuRequestHandler(Path path) {
		return new CellContextMenuRequestHandler(path, pathToDupCollMap, dupCollections, controller);
	}

	/**
	 * @param pathString the path string.
	 * @return a new object.
	 */
	protected Label newPathLabel(String pathString) {
		return new Label(pathString);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateItem(File item, boolean empty) {
		super.updateItem(item, empty);

		disableContextMenu();

		if (!empty) {
			if (item != null) {
				Path path = item.toPath();

				if (pathToDupCollMap.containsKey(path)) {
					setTextFill(Color.RED);
					enableContextMenu(path);
				} else if (ancestorSet.contains(path)) {
					setTextFill(Color.PURPLE);
				} else {
					setTextFill(Color.BLACK);
				}
			}
		}
	}

}
