/**
 * 
 */
package cotelab.dupfilefinder2;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;
import net.sf.cotelab.util.javafx.tree.FileTreeCell;

/**
 * A {@link FileTreeCell} decorated to present {@link DupFileFinder2} results.
 */
public class DecoratedFileTreeCell extends FileTreeCell {
	/**
	 * The ancestor set.
	 */
	protected HashSet<Path> ancestorSet = null;
	
	/**
	 * The controller.
	 */
	protected FXMLController controller = null;
	
	/**
	 * The collection of duplicate path groups.
	 */
	protected ArrayList<Collection<Path>> dupCollections = null;
	
	/**
	 * The map from path to duplicate paths.
	 */
	protected Hashtable<Path, Collection<Path>> pathToDupCollMap = null;

	/**
	 * Construct a new object.
	 * @param aFileIconFactory a factory for icons.
	 * @param anAncestorSet a set of ancestors.
	 * @param aDupCollections a collection of duplicate path groups.
	 * @param aPathToDupCollMap a map from paths to duplicate paths.
	 * @param aController a controller.
	 */
	public DecoratedFileTreeCell(FileIconFactory aFileIconFactory, HashSet<Path> anAncestorSet,
			ArrayList<Collection<Path>> aDupCollections, Hashtable<Path, Collection<Path>> aPathToDupCollMap,
			FXMLController aController) {
		super(aFileIconFactory);

		ancestorSet = anAncestorSet;
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
	 * Delete a subtree from the file system.
	 * @param aPath the subtree's root path.
	 */
	protected void doDelete(Path aPath) {
		try {
			Files.walkFileTree(aPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if (e == null) {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					} else {
						// directory iteration failed
						throw e;
					}
				}
			});
		} catch (IOException e) {
			System.err.println("DecoratedFileTreeCell.doDelete(): caught" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Enable the context menu.
	 * @param path
	 */
	protected void enableContextMenu(Path path) {
		onContextMenuRequestedProperty().set(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {
				Object sourceObj = event.getSource();

				if (sourceObj instanceof Node) {
					Node source = (Node) sourceObj;
					double x = event.getScreenX();
					double y = event.getScreenY();
					MenuItem popupMenuItem = new MenuItem("Show Duplicates");
					ContextMenu menu = newContextMenu(popupMenuItem);

					popupMenuItem.onActionProperty().set(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							showDupCollection(path);
						}

					});

					menu.show(source, x, y);
				}
			}

		});
	}

	/**
	 * Display a dialog listing the duplicate paths for a given path.
	 * @param path the given path.
	 */
	protected void showDupCollection(Path path) {
//		System.out.println("DecoratedFileTreeCell.showDupCollection(): path = " + path);

		Collection<Path> memberPaths = pathToDupCollMap.get(path);

//		System.out.println("DecoratedFileTreeCell.showDupCollection(): memberPaths = " + memberPaths);

		GridPane gridPane = newGridPane();
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

//					Files.delete(aPath);
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

//					System.out.println("DecoratedFileTreeCell.showDupCollection(): refreshing the tree view");

					getTreeView().refresh();

				}

			});

			pathLabel.setContextMenu(contextMenu);

			gridPane.add(pathLabel, 0, row);

			++row;
		}

		Dialog<Void> thePopup = newDialog();
		DialogPane thePopupPane = thePopup.getDialogPane();
		ButtonType okButtonType = newOKButtonType();
		ScrollPane scrollPane = newScrollPane();

		scrollPane.setContent(gridPane);

		thePopup.setTitle("" + memberPaths.size() + " Duplicates");
		thePopupPane.setMaxSize(1500, 800);
		thePopupPane.setContent(scrollPane);
		thePopupPane.getButtonTypes().add(okButtonType);

		thePopup.showAndWait();
	}

	/**
	 * @return a new object.
	 */
	protected ScrollPane newScrollPane() {
		return new ScrollPane();
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
	protected Dialog<Void> newDialog() {
		return new Dialog<>();
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
	 * @param pathString the path string.
	 * @return a new object.
	 */
	protected Label newPathLabel(String pathString) {
		return new Label(pathString);
	}

	/**
	 * @return a new object.
	 */
	protected GridPane newGridPane() {
		return new GridPane();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateItem(File item, boolean empty) {
		super.updateItem(item, empty);

//		System.out.println("DecoratedFileTreeCell.updateItem(): item = " + item);
//		System.out.println("DecoratedFileTreeCell.updateItem(): empty = " + empty);

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
