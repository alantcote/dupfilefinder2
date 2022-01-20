package cotelab.dupfilefinder2.treeview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.dupfilefinder2.FXMLController;
import cotelab.jfxrunner.JavaFxJUnit4ClassRunner;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.scene.control.TreeView;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;

/**
 * Test case for
 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCellFactory}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class DecoratedFileTreeCellFactoryTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCellFactory#call(javafx.scene.control.TreeView)}.
	 */
	@Test
	public void testCall() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCellFactory fixture = new DecoratedFileTreeCellFactory(mockFileIconFactory, realHashSet,
				mockArrayList, realHashtable, mockFXMLController);
		@SuppressWarnings("unchecked")
		final TreeView<File> mockTreeView = context.mock(TreeView.class, "mockTreeView");

		assertNotNull(fixture.call(mockTreeView));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCellFactory#DecoratedFileTreeCellFactory(net.sf.cotelab.util.javafx.tree.FileIconFactory, java.util.HashSet, java.util.ArrayList, java.util.Hashtable, cotelab.dupfilefinder2.FXMLController)}.
	 */
	@Test
	public void testDecoratedFileTreeCellFactory() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCellFactory fixture = new DecoratedFileTreeCellFactory(mockFileIconFactory, realHashSet,
				mockArrayList, realHashtable, mockFXMLController);

		assertNotNull(fixture);

		assertEquals(realHashSet, fixture.ancestorSet);
		assertEquals(mockArrayList, fixture.dupCollections);
		assertEquals(realHashtable, fixture.pathToDupCollMap);
		assertEquals(mockFXMLController, fixture.controller);
	}

}
