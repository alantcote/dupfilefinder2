package io.github.alantcote.dupfilefinder2.treeview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import io.github.alantcote.clutilities.javafx.scene.control.PathIconFactory;
import io.github.alantcote.dupfilefinder2.FXMLController;
import io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell;

/**
 * Test case for {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell}.
 */
@RunWith(JfxRunner.class)
public class DecoratedPathTreeCellTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell#DecoratedFileTreeCell(io.github.alantcote.clutilities.javafx.scene.control.cotelab.util.javafx.tree.FileIconFactory, java.util.HashSet, java.util.ArrayList, java.util.Hashtable, io.github.alantcote.dupfilefinder2.FXMLController)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDecoratedFileTreeCell() {
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCell fixture = new DecoratedPathTreeCell(mockPathIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);

		assertNotNull(fixture);

		assertEquals(realHashSet, fixture.ancestorSet);
		assertEquals(mockArrayList, fixture.dupCollections);
		assertEquals(realHashtable, fixture.pathToDupCollMap);
		assertEquals(mockFXMLController, fixture.controller);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell#disableContextMenu()}.
	 */
	@Test
	public void testDisableContextMenu() {
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCell fixture = new DecoratedPathTreeCell(mockPathIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);

		fixture.disableContextMenu();

		assertNull(fixture.onContextMenuRequestedProperty().get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell#enableContextMenu(java.nio.file.Path)}.
	 */
	@Test
	public void testEnableContextMenu() {
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCell fixture = new DecoratedPathTreeCell(mockPathIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);
		final Path mockPath = context.mock(Path.class, "mockPath");

		fixture.enableContextMenu(mockPath);

		assertNotNull(fixture.onContextMenuRequestedProperty().get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell#newContextMenuRequestHandler(java.nio.file.Path)}.
	 */
	@Test
	public void testNewContextMenuRequestHandler() {
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCell fixture = new DecoratedPathTreeCell(mockPathIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);
		final Path mockPath = context.mock(Path.class, "mockPath");

		assertNotNull(fixture.newContextMenuRequestHandler(mockPath));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell#newPathLabel(java.lang.String)}.
	 */
	@Test
	public void testNewPathLabel() {
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCell fixture = new DecoratedPathTreeCell(mockPathIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);

		assertNotNull(fixture.newPathLabel("gotcha!"));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeCell#updateItem(java.io.File, boolean)}.
	 */
	@Test
	public void testUpdateItemFileBoolean() {
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCell fixture = new DecoratedPathTreeCell(mockPathIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);
		final Path realPath = context.mock(Path.class, "mockPath");

		fixture.updateItem(realPath, true);
	}

}
