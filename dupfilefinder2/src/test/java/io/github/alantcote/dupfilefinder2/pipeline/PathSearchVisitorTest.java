package io.github.alantcote.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Test;

import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor;

/**
 * Test case for {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor}.
 */
public class PathSearchVisitorTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getDirectoryCount()}.
	 */
	@Test
	public void testGetDirectoryCount() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.directoryCount, fixture.getDirectoryCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getFailedAccessCount()}.
	 */
	@Test
	public void testGetFailedAccessCount() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.failedAccessCount, fixture.getFailedAccessCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getFiles()}.
	 */
	@Test
	public void testGetFiles() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.files, fixture.getFiles());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getFilesVisited()}.
	 */
	@Test
	public void testGetFilesVisited() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.filesVisited, fixture.getFilesVisited());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getOtherCount()}.
	 */
	@Test
	public void testGetOtherCount() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.otherCount, fixture.getOtherCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getRegularFileCount()}.
	 */
	@Test
	public void testGetRegularFileCount() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.regularFileCount, fixture.getRegularFileCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getSymbolicLinkCount()}.
	 */
	@Test
	public void testGetSymbolicLinkCount() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.symbolicLinkCount, fixture.getSymbolicLinkCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#getUnreadableCount()}.
	 */
	@Test
	public void testGetUnreadableCount() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertEquals(fixture.unreadableCount, fixture.getUnreadableCount());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#isReadableFile(java.nio.file.Path)}.
	 */
	@Test
	public void testIsReadableFile() {
		// gotta learn how to test code that calls static methods
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#newPathLinkedList()}.
	 */
	@Test
	public void testNewPathLinkedList() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertNotNull(fixture.newPathLinkedList());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#newSimpleIntegerProperty()}.
	 */
	@Test
	public void testNewSimpleIntegerProperty() {
		PathSearchVisitor fixture = new PathSearchVisitor();

		assertNotNull(fixture.newSimpleIntegerProperty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#preVisitDirectory(java.nio.file.Path, java.nio.file.attribute.BasicFileAttributes)}.
	 * 
	 * @throws IOException if it is thrown by the code under test.
	 */
	@Test
	public void testPreVisitDirectoryPathBasicFileAttributes() throws IOException {
		PathSearchVisitor fixture = new PathSearchVisitor() {

			@Override
			protected boolean isReadableFile(Path file) {
				return false;
			}

		};
		final Path mockPath = context.mock(Path.class, "mockPath");
		final BasicFileAttributes mockBasicFileAttributes = context.mock(BasicFileAttributes.class,
				"mockBasicFileAttributes");

		assertEquals(FileVisitResult.SKIP_SUBTREE, fixture.preVisitDirectory(mockPath, mockBasicFileAttributes));
		assertEquals(1, fixture.unreadableCount.get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#visitFileFailed(java.nio.file.Path, java.io.IOException)}.
	 * 
	 * @throws IOException if it is thrown by the code under test.
	 */
	@Test
	public void testVisitFileFailedPathIOException() throws IOException {
		PathSearchVisitor fixture = new PathSearchVisitor() {

			@Override
			protected boolean isReadableFile(Path file) {
				return false;
			}

		};
		final Path mockPath = context.mock(Path.class, "mockPath");
		final IOException mockIOException = context.mock(IOException.class, "mockIOException");

		assertEquals(FileVisitResult.CONTINUE, fixture.visitFileFailed(mockPath, mockIOException));
		assertEquals(1, fixture.failedAccessCount.get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.PathSearchVisitor#visitFile(java.nio.file.Path, java.nio.file.attribute.BasicFileAttributes)}.
	 * 
	 * @throws IOException if it is thrown by the code under test.
	 */
	@Test
	public void testVisitFilePathBasicFileAttributes() throws IOException {
		PathSearchVisitor fixture = new PathSearchVisitor() {

			@Override
			protected boolean isReadableFile(Path file) {
				return false;
			}

		};
		final Path mockPath = context.mock(Path.class, "mockPath");
		final BasicFileAttributes mockBasicFileAttributes = context.mock(BasicFileAttributes.class,
				"mockBasicFileAttributes");

		assertEquals(FileVisitResult.CONTINUE, fixture.visitFile(mockPath, mockBasicFileAttributes));
		assertEquals(1, fixture.unreadableCount.get());
	}

}
