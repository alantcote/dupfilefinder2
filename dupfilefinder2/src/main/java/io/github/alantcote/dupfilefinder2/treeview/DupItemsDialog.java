package io.github.alantcote.dupfilefinder2.treeview;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import io.github.alantcote.dupfilefinder2.FXMLController;
import io.github.alantcote.fxutilities.javafx.scene.control.ExceptionAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class DupItemsDialog extends Dialog<Void> {
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
	public DupItemsDialog(Path aPath, Map<Path, Collection<Path>> aPathToDupCollMap,
			Collection<Collection<Path>> aDupCollections, FXMLController aController) {
		path = aPath;
		pathToDupCollMap = aPathToDupCollMap;
		dupCollections = aDupCollections;
		controller = aController;
		DialogPane thePopupPane = getDialogPane();
		ButtonType okButtonType = newOKButtonType();
		ScrollPane scrollPane = newScrollPane();

		scrollPane.setContent(makeGridPane());

		setTitle("" + pathToDupCollMap.get(path).size() + " Duplicates");
		thePopupPane.setMaxSize(1500, 800);
		thePopupPane.setContent(scrollPane);
		thePopupPane.getButtonTypes().add(okButtonType);
	}

	/**
	 * Delete a subtree from the file system.
	 * 
	 * @param aPath the subtree's root path.
	 */
	protected void doDelete(Path aPath) {
		try {
			Files.walkFileTree(aPath, newPathDeletionVisitor());
		} catch (IOException e) {
			showException(e);
		}
	}
	
	/**
	 * Make the pane that will be the main content of the dialog.
	 * 
	 * @return the pane that will be the main content of the dialog.
	 */
	protected GridPane makeGridPane() {
		GridPane gridPane = newGridPane();
		Collection<Path> memberPaths = pathToDupCollMap.get(path);

		int row = 0;

		for (Path aPath : memberPaths) {
			String pathString = aPath.toString();
			Label pathLabel = newPathLabel(pathString);
			MenuItem deleteMenuItem = newDeleteMenuItem();
			ContextMenu contextMenu = newContextMenu(deleteMenuItem);

			deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// delete the thing identified by aPath
					doDelete(aPath);

					// remove the label from the grid pane
					gridPane.getChildren().remove(pathLabel);

					// remove entries in various maps and sets
					// they're in the controller; must be done before refresh is safe

					Collection<Path> containingColl = null;

					for (Collection<Path> coll : dupCollections) {
						if (coll.contains(aPath)) {
							containingColl = coll;

							break;
						}
					}

					if (containingColl != null) {
						if (containingColl.size() < 3) {
							dupCollections.remove(containingColl);
						} else {
							containingColl.remove(aPath);
						}
					}

					controller.refreshResultAids();

					// refresh the tree view
					controller.getResultsTreeView().refresh();

				}

			});

			pathLabel.setContextMenu(contextMenu);

			gridPane.add(pathLabel, 0, row);

			++row;
		}

		return gridPane;
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
	protected MenuItem newDeleteMenuItem() {
		return new MenuItem("Delete");
	}

	/**
	 * @return a new object.
	 */
	protected GridPane newGridPane() {
		return new GridPane();
	}

	/**
	 * @return a new object.
	 */
	protected ButtonType newOKButtonType() {
		return new ButtonType("OK", ButtonData.OK_DONE);
	}

	/**
	 * @return a new object.
	 */
	protected PathDeletionVisitor newPathDeletionVisitor() {
		return new PathDeletionVisitor();
	}

	/**
	 * @param pathString the path string.
	 * @return a new object.
	 */
	protected Label newPathLabel(String pathString) {
		return new Label(pathString);
	}

	/**
	 * @return a new object.
	 */
	protected ScrollPane newScrollPane() {
		return new ScrollPane();
	}

	protected void showException(Throwable t) {
		System.err.println("Caught " + t.getMessage());
		t.printStackTrace();
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ExceptionAlert ea = new ExceptionAlert(AlertType.ERROR, t, "Caught Exception");

				ea.showAndWait();
			}
			
		});
	}
}
