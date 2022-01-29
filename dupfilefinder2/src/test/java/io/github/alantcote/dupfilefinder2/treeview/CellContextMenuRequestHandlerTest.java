package io.github.alantcote.dupfilefinder2.treeview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import io.github.alantcote.dupfilefinder2.FXMLController;
import io.github.alantcote.dupfilefinder2.treeview.CellContextMenuRequestHandler;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

/**
 * Test case for
 * {@link io.github.alantcote.dupfilefinder2.treeview.CellContextMenuRequestHandler}.
 */
@RunWith(JfxRunner.class)
public class CellContextMenuRequestHandlerTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.CellContextMenuRequestHandler#CellContextMenuRequestHandler(java.nio.file.Path, java.util.Map, java.util.Collection, io.github.alantcote.dupfilefinder2.FXMLController)}.
	 */
	@Test
	public void testCellContextMenuRequestHandler() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		CellContextMenuRequestHandler fixture = new CellContextMenuRequestHandler(mockPath, mockMap, mockCollection,
				mockFXMLController);

		assertNotNull(fixture);

		assertEquals(mockPath, fixture.path);
		assertEquals(mockMap, fixture.pathToDupCollMap);
		assertEquals(mockCollection, fixture.dupCollections);
		assertEquals(mockFXMLController, fixture.controller);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.CellContextMenuRequestHandler#handle(javafx.scene.input.ContextMenuEvent)}.
	 */
	@Test
	public void testHandle() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		CellContextMenuRequestHandler fixture = new CellContextMenuRequestHandler(mockPath, mockMap, mockCollection,
				mockFXMLController);
		final ContextMenuEvent mockContextMenuEvent = context.mock(ContextMenuEvent.class, "mockContextMenuEvent");

		context.checking(new Expectations() {
			{
				oneOf(mockContextMenuEvent).getSource();
				will(returnValue(mockPath));
			}
		});

		fixture.handle(mockContextMenuEvent);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.CellContextMenuRequestHandler#newContextMenu(javafx.scene.control.MenuItem)}.
	 */
	@Test
	public void testNewContextMenu() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		CellContextMenuRequestHandler fixture = new CellContextMenuRequestHandler(mockPath, mockMap, mockCollection,
				mockFXMLController);
		final MenuItem mockMenuItem = context.mock(MenuItem.class, "mockMenuItem");

		assertNotNull(fixture.newContextMenu(mockMenuItem));
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.CellContextMenuRequestHandler#newMenuItem()}.
	 */
	@Test
	public void testNewMenuItem() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		CellContextMenuRequestHandler fixture = new CellContextMenuRequestHandler(mockPath, mockMap, mockCollection,
				mockFXMLController);

		assertNotNull(fixture.newMenuItem());
	}

}
