/**
 * 
 */
package cotelab.dupfilefinder2;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import javafx.scene.control.TreeItem;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;
import net.sf.cotelab.util.javafx.tree.FileTreeView;

/**
 * A {@link FileTreeView} that uses {@link DecoratedFileTreeCell} instances.
 * 
 * @author alantcote
 *
 */
public class DecoratedFileTreeView extends FileTreeView {

	public DecoratedFileTreeView(TreeItem<File> root, FileIconFactory aFileIconFactory, HashSet<Path> anAncestorSet,
			ArrayList<Collection<Path>> aDupCollections, Hashtable<Path, Collection<Path>> aPathToDupCollMap, FXMLController aController) {
		super(root);

//		FileIconFactory fileIconFactory = new FileIconFactory();

		setCellFactory(
				new DecoratedFileTreeCellFactory(aFileIconFactory, anAncestorSet, aDupCollections, aPathToDupCollMap, aController));
	}

}
