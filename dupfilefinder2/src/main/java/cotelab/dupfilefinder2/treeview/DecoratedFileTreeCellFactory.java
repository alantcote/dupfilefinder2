package cotelab.dupfilefinder2.treeview;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import cotelab.dupfilefinder2.FXMLController;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;
import net.sf.cotelab.util.javafx.tree.FileTreeCell;
import net.sf.cotelab.util.javafx.tree.FileTreeCellFactory;

/**
 * A {@link FileTreeCellFactory} that supplies {@link DecoratedFileTreeCell} in
 * place of {@link FileTreeCell} instances.
 */
public class DecoratedFileTreeCellFactory extends FileTreeCellFactory {
	/**
	 * The set of ancestors.
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
	 * A map from path to a group of paths of duplicate files.
	 */
	protected Map<Path, Collection<Path>> pathToDupCollMap = null;

	/**
	 * Construct a new object.
	 * 
	 * @param aFileIconFactory  a source for system-authentic icons.
	 * @param anAncestorSet     a set of ancestors.
	 * @param aDupCollections   a collection of duplicate path groups.
	 * @param aPathToDupCollMap a map from path to a group of paths of duplicate
	 *                          files.
	 * @param aController       the controller.
	 */
	public DecoratedFileTreeCellFactory(FileIconFactory aFileIconFactory, Set<Path> anAncestorSet,
			Collection<Collection<Path>> aDupCollections, Map<Path, Collection<Path>> aPathToDupCollMap,
			FXMLController aController) {
		super(aFileIconFactory);

		ancestorSet = anAncestorSet;
		dupCollections = aDupCollections;
		pathToDupCollMap = aPathToDupCollMap;
		controller = aController;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeCell<File> call(TreeView<File> param) {
		return new DecoratedFileTreeCell(fileIconFactory, ancestorSet, dupCollections, pathToDupCollMap, controller);
	}

}
