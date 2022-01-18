package cotelab.dupfilefinder2.treeview;

import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Expectations;
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

/**
 * Test case for {@link cotelab.dupfilefinder2.treeview.DupItemsDialog}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class DupItemsDialogTest {
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
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#doDelete(java.nio.file.Path)}.
	 */
	@Test
	public void testDoDelete() {
		// still haven't learned how to unit test around a static method call.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#DupItemsDialog(java.nio.file.Path, java.util.Map, java.util.Collection, cotelab.dupfilefinder2.FXMLController)}.
	 */
	@Test
	public void testDupItemsDialog() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#makeGridPane()}.
	 */
	@Test
	public void testMakeGridPane() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		// note that the DupItemsDialog constructor calls makeGridPane()
		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#newContextMenu(javafx.scene.control.MenuItem)}.
	 */
	@Test
	public void testNewContextMenu() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture.newContextMenu(fixture.newDeleteMenuItem()));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#newDeleteMenuItem()}.
	 */
	@Test
	public void testNewDeleteMenuItem() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture.newDeleteMenuItem());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#newGridPane()}.
	 */
	@Test
	public void testNewGridPane() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture.newGridPane());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#newOKButtonType()}.
	 */
	@Test
	public void testNewOKButtonType() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture.newOKButtonType());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#newPathDeletionVisitor()}.
	 */
	@Test
	public void testNewPathDeletionVisitor() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture.newPathDeletionVisitor());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#newPathLabel(java.lang.String)}.
	 */
	@Test
	public void testNewPathLabel() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture.newPathLabel("/usr/lib"));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.DupItemsDialog#newScrollPane()}.
	 */
	@Test
	public void testNewScrollPane() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final ArrayList<Path> realArrayList = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realArrayList));
			}
		});

		DupItemsDialog fixture = new DupItemsDialog(mockPath, mockMap, mockCollection, mockFXMLController);

		assertNotNull(fixture.newScrollPane());
	}

}
