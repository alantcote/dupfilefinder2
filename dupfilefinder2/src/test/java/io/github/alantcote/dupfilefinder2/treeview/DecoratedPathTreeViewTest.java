package io.github.alantcote.dupfilefinder2.treeview;

import static org.junit.Assert.assertNotNull;

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
import io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeView;
import javafx.scene.control.TreeItem;

/**
 * Test case for {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeView}.
 */
@RunWith(JfxRunner.class)
public class DecoratedPathTreeViewTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DecoratedPathTreeView#DecoratedFileTreeView(javafx.scene.control.TreeItem, io.github.alantcote.clutilities.javafx.scene.control.cotelab.util.javafx.tree.FileIconFactory, java.util.HashSet, java.util.ArrayList, java.util.Hashtable, io.github.alantcote.dupfilefinder2.FXMLController)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDecoratedFileTreeView() {
		final TreeItem<Path> realTreeItem = new TreeItem<Path>();
		final PathIconFactory mockPathIconFactory = context.mock(PathIconFactory.class, "mockPathIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedPathTreeView fixture = new DecoratedPathTreeView(realTreeItem, mockPathIconFactory, realHashSet,
				mockArrayList, realHashtable, mockFXMLController);

		assertNotNull(fixture);
	}

}
