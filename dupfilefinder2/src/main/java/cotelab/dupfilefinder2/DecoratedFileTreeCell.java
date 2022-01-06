/**
 * 
 */
package cotelab.dupfilefinder2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * 
 * @author alantcote
 *
 */
public class DecoratedFileTreeCell extends FileTreeCell {
	protected HashSet<Path> ancestorSet = null;
	protected FXMLController controller = null;
	protected ArrayList<Collection<Path>> dupCollections = null;
	protected Hashtable<Path, Collection<Path>> pathToDupCollMap = null;

	/**
	 * Construct a new object.
	 * 
	 * @param aFileIconFactory a source for platform-authentic icon images that are
	 *                         appropriate to files being represented.
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

	protected void disableContextMenu() {
		onContextMenuRequestedProperty().set(null);
	}

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
					ContextMenu menu = new ContextMenu(popupMenuItem);

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

	protected void showDupCollection(Path path) {
//		System.out.println("DecoratedFileTreeCell.showDupCollection(): path = " + path);

		Collection<Path> memberPaths = pathToDupCollMap.get(path);

//		System.out.println("DecoratedFileTreeCell.showDupCollection(): memberPaths = " + memberPaths);

		GridPane gridPane = new GridPane();
		int row = 0;

		for (Path aPath : memberPaths) {
			String pathString = aPath.toString();
			Label pathLabel = new Label(pathString);
			MenuItem deleteMenuItem = new MenuItem("Delete");
			ContextMenu contextMenu = new ContextMenu(deleteMenuItem);

			deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// delete the thing identified by aPath

					try {
						Files.delete(aPath);

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

//						System.out.println("DecoratedFileTreeCell.showDupCollection(): refreshing the tree view");

						getTreeView().refresh();
					} catch (IOException e) {
						// Auto-generated catch block
						e.printStackTrace();
						return;
					}

				}

			});

			pathLabel.setContextMenu(contextMenu);

			gridPane.add(pathLabel, 0, row);

			++row;
		}

		Dialog<Void> thePopup = new Dialog<>();
		DialogPane thePopupPane = thePopup.getDialogPane();
		ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		ScrollPane scrollPane = new ScrollPane();
		
		scrollPane.setContent(gridPane);

		thePopup.setTitle("" + memberPaths.size() + " Duplicates");
		thePopupPane.setMaxSize(1500, 800);
		thePopupPane.setContent(scrollPane);
		thePopupPane.getButtonTypes().add(okButtonType);

		thePopup.showAndWait();
	}

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
					setTextFill(Color.BLUE);
					enableContextMenu(path);
				} else if (ancestorSet.contains(path)) {
					setTextFill(Color.CYAN);
				} else {
					setTextFill(Color.BLACK);
				}
			}
		}
	}

}
