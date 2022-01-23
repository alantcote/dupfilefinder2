package cotelab.dupfilefinder2.treeview;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import cotelab.dupfilefinder2.FXMLController;
import io.github.alantcote.clutilities.javafx.scene.control.FileIconFactory;
import io.github.alantcote.clutilities.javafx.scene.control.FileTreeView;
import javafx.scene.control.TreeItem;

/**
 * A {@link FileTreeView} that uses {@link DecoratedFileTreeCell} instances.
 */
public class DecoratedFileTreeView extends FileTreeView {
	/**
	 * Construct a new object.
	 * 
	 * @param root              the root tree item.
	 * @param aFileIconFactory  a source for system-authentic icons.
	 * @param anAncestorSet     a set of ancestors.
	 * @param aDupCollections   a collection of groups of matching files.
	 * @param aPathToDupCollMap a map from path to group of matching files.
	 * @param aController       the controller.
	 */
	public DecoratedFileTreeView(TreeItem<File> root, FileIconFactory aFileIconFactory, Set<Path> anAncestorSet,
			Collection<Collection<Path>> aDupCollections, Map<Path, Collection<Path>> aPathToDupCollMap,
			FXMLController aController) {
		super(root);

		setCellFactory(new DecoratedFileTreeCellFactory(aFileIconFactory, anAncestorSet, aDupCollections,
				aPathToDupCollMap, aController));
	}

}
