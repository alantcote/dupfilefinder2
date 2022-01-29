package io.github.alantcote.dupfilefinder2.treeview;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import io.github.alantcote.dupfilefinder2.FXMLController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

/**
 * A handler for a context menu request from a {@link DecoratedPathTreeCell}.
 */
public class CellContextMenuRequestHandler implements EventHandler<ContextMenuEvent> {
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
	public CellContextMenuRequestHandler(Path aPath, Map<Path, Collection<Path>> aPathToDupCollMap,
			Collection<Collection<Path>> aDupCollections, FXMLController aController) {
		path = aPath;
		pathToDupCollMap = aPathToDupCollMap;
		dupCollections = aDupCollections;
		controller = aController;
	}

	@Override
	public void handle(ContextMenuEvent event) {
		Object sourceObj = event.getSource();

		if (sourceObj instanceof Node) {
			Node source = (Node) sourceObj;
			double x = event.getScreenX();
			double y = event.getScreenY();
			MenuItem popupMenuItem = newMenuItem();
			ContextMenu menu = newContextMenu(popupMenuItem);

			popupMenuItem.onActionProperty()
					.set(new ShowDuplicatesMenuItemEventHandler(path, pathToDupCollMap, dupCollections, controller));

			menu.show(source, x, y);
		}
	}

	/**
	 * @param menuItem the first menu item.
	 * @return a new object.
	 */
	protected ContextMenu newContextMenu(MenuItem menuItem) {
		return new ContextMenu(menuItem);
	}

	/**
	 * @return a new object.
	 */
	protected MenuItem newMenuItem() {
		return new MenuItem("Show Duplicates");
	}
}
