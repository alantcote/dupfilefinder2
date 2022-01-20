package cotelab.dupfilefinder2.treeview;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.dupfilefinder2.FXMLController;
import cotelab.jfxrunner.JavaFxJUnit4ClassRunner;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.scene.control.TreeItem;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;

/**
 * Test case for {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeView}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class DecoratedFileTreeViewTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeView#DecoratedFileTreeView(javafx.scene.control.TreeItem, net.sf.cotelab.util.javafx.tree.FileIconFactory, java.util.HashSet, java.util.ArrayList, java.util.Hashtable, cotelab.dupfilefinder2.FXMLController)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDecoratedFileTreeView() {
		final TreeItem<File> realTreeItem = new TreeItem<File>();
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeView fixture = new DecoratedFileTreeView(realTreeItem, mockFileIconFactory, realHashSet,
				mockArrayList, realHashtable, mockFXMLController);

		assertNotNull(fixture);
	}

}
