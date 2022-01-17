package cotelab.dupfilefinder2.treeview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import net.sf.cotelab.jfxrunner.JavaFxJUnit4ClassRunner;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;

/**
 * Test case for {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCell}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class DecoratedFileTreeCellTest {
	protected Mockery context;

	protected Sequence sequence;

	@After
	public void runAfterTests() throws Exception {
		context.assertIsSatisfied();
	}

	@Before
	public void runBeforeTests() throws Exception {
		context = new Mockery() {
			{
				setThreadingPolicy(new Synchroniser());
				setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
			}
		};

		sequence = context.sequence(getClass().getName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCell#DecoratedFileTreeCell(net.sf.cotelab.util.javafx.tree.FileIconFactory, java.util.HashSet, java.util.ArrayList, java.util.Hashtable, cotelab.dupfilefinder2.FXMLController)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDecoratedFileTreeCell() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCell fixture = new DecoratedFileTreeCell(mockFileIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);

		assertNotNull(fixture);

		assertEquals(realHashSet, fixture.ancestorSet);
		assertEquals(mockArrayList, fixture.dupCollections);
		assertEquals(realHashtable, fixture.pathToDupCollMap);
		assertEquals(mockFXMLController, fixture.controller);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCell#disableContextMenu()}.
	 */
	@Test
	public void testDisableContextMenu() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCell fixture = new DecoratedFileTreeCell(mockFileIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);

		fixture.disableContextMenu();

		assertNull(fixture.onContextMenuRequestedProperty().get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCell#enableContextMenu(java.nio.file.Path)}.
	 */
	@Test
	public void testEnableContextMenu() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCell fixture = new DecoratedFileTreeCell(mockFileIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);
		final Path mockPath = context.mock(Path.class, "mockPath");

		fixture.enableContextMenu(mockPath);

		assertNotNull(fixture.onContextMenuRequestedProperty().get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCell#newContextMenuRequestHandler(java.nio.file.Path)}.
	 */
	@Test
	public void testNewContextMenuRequestHandler() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCell fixture = new DecoratedFileTreeCell(mockFileIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);
		final Path mockPath = context.mock(Path.class, "mockPath");

		assertNotNull(fixture.newContextMenuRequestHandler(mockPath));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCell#newPathLabel(java.lang.String)}.
	 */
	@Test
	public void testNewPathLabel() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCell fixture = new DecoratedFileTreeCell(mockFileIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);

		assertNotNull(fixture.newPathLabel("gotcha!"));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DecoratedFileTreeCell#updateItem(java.io.File, boolean)}.
	 */
	@Test
	public void testUpdateItemFileBoolean() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final HashSet<Path> realHashSet = new HashSet<Path>();
		@SuppressWarnings("unchecked")
		final ArrayList<Collection<Path>> mockArrayList = context.mock(ArrayList.class, "mockArrayList");
		final Hashtable<Path, Collection<Path>> realHashtable = new Hashtable<Path, Collection<Path>>();
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		DecoratedFileTreeCell fixture = new DecoratedFileTreeCell(mockFileIconFactory, realHashSet, mockArrayList,
				realHashtable, mockFXMLController);
		final File realFile = new File(".");

		fixture.updateItem(realFile, true);
	}

}