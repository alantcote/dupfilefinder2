package cotelab.dupfilefinder2.treeview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.dupfilefinder2.FXMLController;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import io.github.alantcote.clutilities.javafx.scene.control.PathIconFactory;
import javafx.scene.control.TreeView;

/**
 * Test case for
 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCellFactory}.
 */
@RunWith(JfxRunner.class)
public class DecoratedFileTreeCellFactoryTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCellFactory#call(javafx.scene.control.TreeView)}.
	 */
	@Test
	public void testCall() {
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCellFactory fixture = new DecoratedPathTreeCellFactory(mockPathIconFactory, realHashSet,
				mockArrayList, realHashtable, mockFXMLController);
		@SuppressWarnings("unchecked")
		final TreeView<Path> mockTreeView = context.mock(TreeView.class, "mockTreeView");

		assertNotNull(fixture.call(mockTreeView));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCellFactory#DecoratedFileTreeCellFactory(io.github.alantcote.clutilities.javafx.scene.control.cotelab.util.javafx.tree.FileIconFactory, java.util.HashSet, java.util.ArrayList, java.util.Hashtable, cotelab.dupfilefinder2.FXMLController)}.
	 */
	@Test
	public void testDecoratedFileTreeCellFactory() {
		final PathIconFactory mockFileIconFactory = context.mock(PathIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeCellFactory fixture = new DecoratedPathTreeCellFactory(mockFileIconFactory, realHashSet,
				mockArrayList, realHashtable, mockFXMLController);

		assertNotNull(fixture);

		assertEquals(realHashSet, fixture.ancestorSet);
		assertEquals(mockArrayList, fixture.dupCollections);
		assertEquals(realHashtable, fixture.pathToDupCollMap);
		assertEquals(mockFXMLController, fixture.controller);
	}

}
