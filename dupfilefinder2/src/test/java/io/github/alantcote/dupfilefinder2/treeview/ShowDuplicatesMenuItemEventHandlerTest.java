package io.github.alantcote.dupfilefinder2.treeview;

import static org.junit.Assert.assertEquals;
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
import io.github.alantcote.dupfilefinder2.treeview.ShowDuplicatesMenuItemEventHandler;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;

/**
 * Test case for
 * {@link io.github.alantcote.dupfilefinder2.treeview.ShowDuplicatesMenuItemEventHandler}.
 */
@RunWith(JfxRunner.class)
public class ShowDuplicatesMenuItemEventHandlerTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.ShowDuplicatesMenuItemEventHandler#handle(javafx.event.ActionEvent)}.
	 */
	@Test
	public void testHandle() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		final SimpleIntegerProperty sdcCount = new SimpleIntegerProperty(0);
		ShowDuplicatesMenuItemEventHandler fixture = new ShowDuplicatesMenuItemEventHandler(mockPath, mockMap,
				mockCollection, mockFXMLController) {

			@Override
			protected void showDupCollection(Path path) {
				sdcCount.set(1 + sdcCount.get());
			}

		};
		final ActionEvent mockActionEvent = context.mock(ActionEvent.class, "mockActionEvent");

		fixture.handle(mockActionEvent);

		assertEquals(1, sdcCount.get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.ShowDuplicatesMenuItemEventHandler#newDupItemsDialog()}.
	 */
	@Test
	@TestInJfxThread
	public void testNewDupItemsDialog() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		ShowDuplicatesMenuItemEventHandler fixture = new ShowDuplicatesMenuItemEventHandler(mockPath, mockMap,
				mockCollection, mockFXMLController);
		final Collection<Path> realCollection = new ArrayList<Path>();

		context.checking(new Expectations() {
			{
				oneOf(mockMap).get(mockPath);
				will(returnValue(realCollection));

				oneOf(mockMap).get(mockPath);
				will(returnValue(realCollection));
			}
		});

		assertNotNull(fixture.newDupItemsDialog());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.ShowDuplicatesMenuItemEventHandler#showDupCollection(java.nio.file.Path)}.
	 */
	@Test
	public void testShowDupCollection() {
		// I know of no way to intercept a call to a final method.
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.treeview.ShowDuplicatesMenuItemEventHandler#ShowDuplicatesMenuItemEventHandler(java.nio.file.Path, java.util.Map, java.util.Collection, io.github.alantcote.dupfilefinder2.FXMLController)}.
	 */
	@Test
	public void testShowDuplicatesMenuItemEventHandler() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		@SuppressWarnings("unchecked")
		final Map<Path, Collection<Path>> mockMap = context.mock(Map.class, "mockMap");
		@SuppressWarnings("unchecked")
		final Collection<Collection<Path>> mockCollection = context.mock(Collection.class, "mockCollection");
		final FXMLController mockFXMLController = context.mock(FXMLController.class, "mockFXMLController");
		ShowDuplicatesMenuItemEventHandler fixture = new ShowDuplicatesMenuItemEventHandler(mockPath, mockMap,
				mockCollection, mockFXMLController);

		assertNotNull(fixture);

		assertEquals(mockPath, fixture.path);
		assertEquals(mockMap, fixture.pathToDupCollMap);
		assertEquals(mockCollection, fixture.dupCollections);
		assertEquals(mockFXMLController, fixture.controller);
	}

}
