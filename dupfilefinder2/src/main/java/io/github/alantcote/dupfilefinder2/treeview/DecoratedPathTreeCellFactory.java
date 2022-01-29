package io.github.alantcote.dupfilefinder2.treeview;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import io.github.alantcote.clutilities.javafx.scene.control.PathIconFactory;
import io.github.alantcote.clutilities.javafx.scene.control.PathTreeCell;
import io.github.alantcote.clutilities.javafx.util.callback.PathTreeCellFactory;
import io.github.alantcote.dupfilefinder2.FXMLController;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;

/**
 * A {@link PathTreeCellFactory} that supplies {@link DecoratedPathTreeCell} in
 * place of {@link PathTreeCell} instances.
 */
public class DecoratedPathTreeCellFactory extends PathTreeCellFactory {
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
	 * @param aPathIconFactory  a source for system-authentic icons.
	 * @param anAncestorSet     a set of ancestors.
	 * @param aDupCollections   a collection of duplicate path groups.
	 * @param aPathToDupCollMap a map from path to a group of paths of duplicate
	 *                          files.
	 * @param aController       the controller.
	 */
	public DecoratedPathTreeCellFactory(PathIconFactory aPathIconFactory, Set<Path> anAncestorSet,
			Collection<Collection<Path>> aDupCollections, Map<Path, Collection<Path>> aPathToDupCollMap,
			FXMLController aController) {
		super(aPathIconFactory);

		ancestorSet = anAncestorSet;
		dupCollections = aDupCollections;
		pathToDupCollMap = aPathToDupCollMap;
		controller = aController;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeCell<Path> call(TreeView<Path> param) {
		return new DecoratedPathTreeCell(pathIconFactory, ancestorSet, dupCollections, pathToDupCollMap, controller);
	}

}
