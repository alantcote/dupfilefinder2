package io.github.alantcote.dupfilefinder2.treeview;

import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import de.saxsys.mvvmfx.testingutils.jfxrunner.TestInJfxThread;
import io.github.alantcote.dupfilefinder2.FXMLController;
import io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog}.
 */
@RunWith(JfxRunner.class)
public class DupItemsDialogTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#doDelete(java.nio.file.Path)}.
	 */
	@Test
	public void testDoDelete() {
		// still haven't learned how to unit test around a static method call.
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#DupItemsDialog(java.nio.file.Path, java.util.Map, java.util.Collection, io.github.alantcote.dupfilefinder2.FXMLController)}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#makeGridPane()}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#newContextMenu(javafx.scene.control.MenuItem)}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#newDeleteMenuItem()}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#newGridPane()}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#newOKButtonType()}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#newPathDeletionVisitor()}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#newPathLabel(java.lang.String)}.
	 */
	@Test
	@TestInJfxThread
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
	 * {@link io.github.alantcote.dupfilefinder2.treeview.DupItemsDialog#newScrollPane()}.
	 */
	@Test
	@TestInJfxThread
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
