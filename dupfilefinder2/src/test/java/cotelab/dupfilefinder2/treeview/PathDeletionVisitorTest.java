package cotelab.dupfilefinder2.treeview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for {@link cotelab.dupfilefinder2.treeview.PathDeletionVisitor}.
 */
public class PathDeletionVisitorTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.PathDeletionVisitor#doDelete(java.nio.file.Path)}.
	 */
	@Test
	public void testDoDelete() {
		// somebody please tell me how to do a usable unit test around static method
		// calls.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.PathDeletionVisitor#PathDeletionVisitor()}.
	 */
	@Test
	public void testPathDeletionVisitor() {
		PathDeletionVisitor fixture = new PathDeletionVisitor() {

			@Override
			protected void doDelete(Path aPath) throws IOException {
				// ABSOLUTELY don't want this called in a test!
			}

		};

		assertNotNull(fixture);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.PathDeletionVisitor#postVisitDirectory(java.nio.file.Path, java.io.IOException)}.
	 * 
	 * @throws IOException if thrown by the code under test.
	 */
	@Test
	public void testPostVisitDirectoryPathIOException() throws IOException {
		PathDeletionVisitor fixture = new PathDeletionVisitor() {

			@Override
			protected void doDelete(Path aPath) throws IOException {
				// ABSOLUTELY don't want this called in a test!
			}

		};
		final Path mockPath = context.mock(Path.class, "mockPath");

		assertEquals(FileVisitResult.CONTINUE, fixture.postVisitDirectory(mockPath, null));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.treeview.PathDeletionVisitor#visitFile(java.nio.file.Path, java.nio.file.attribute.BasicFileAttributes)}.
	 * 
	 * @throws IOException if thrown by the code under test.
	 */
	@Test
	public void testVisitFilePathBasicFileAttributes() throws IOException {
		PathDeletionVisitor fixture = new PathDeletionVisitor() {

			@Override
			protected void doDelete(Path aPath) throws IOException {
				// ABSOLUTELY don't want this called in a test!
			}

		};
		final Path mockPath = context.mock(Path.class, "mockPath");
		final BasicFileAttributes mockBasicFileAttributes = context.mock(BasicFileAttributes.class,
				"mockBasicFileAttributes");

		assertEquals(FileVisitResult.CONTINUE, fixture.visitFile(mockPath, mockBasicFileAttributes));
	}

}
