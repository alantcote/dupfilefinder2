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

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;
import net.sf.cotelab.util.javafx.tree.FileTreeCell;
import net.sf.cotelab.util.javafx.tree.FileTreeCellFactory;

/**
 * A {@link FileTreeCellFactory} that supplies {@link DecoratedFileTreeCell} in
 * place of {@link FileTreeCell} instances.
 * 
 * @author alantcote
 *
 */
public class DecoratedFileTreeCellFactory extends FileTreeCellFactory {
	protected HashSet<Path> ancestorSet = null;
	protected FXMLController controller = null;
	protected ArrayList<Collection<Path>> dupCollections = null;
	protected Hashtable<Path, Collection<Path>> pathToDupCollMap = null;

	/**
	 * Construct a new object
	 */
	public DecoratedFileTreeCellFactory(FileIconFactory aFileIconFactory, HashSet<Path> anAncestorSet,
			ArrayList<Collection<Path>> aDupCollections, Hashtable<Path, Collection<Path>> aPathToDupCollMap, FXMLController aController) {
		super(aFileIconFactory);

		ancestorSet = anAncestorSet;
		dupCollections = aDupCollections;
		pathToDupCollMap = aPathToDupCollMap;
		controller = aController;
	}

	@Override
	public TreeCell<File> call(TreeView<File> param) {
//		System.out.println("DecoratedFileTreeCellFactory.call(): hello, world!");
		
		return new DecoratedFileTreeCell(fileIconFactory, ancestorSet, dupCollections, pathToDupCollMap, controller);
	}

}
