package cotelab.dupfilefinder2.pipeline;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A path visitor.
 */
public class PathSearchVisitor extends SimpleFileVisitor<Path> {
	/**
	 * The number of directory paths found.
	 */
	protected SimpleIntegerProperty directoryCount = newSimpleIntegerProperty();

	/**
	 * The number of paths that could not be accessed.
	 */
	protected SimpleIntegerProperty failedAccessCount = newSimpleIntegerProperty();

	/**
	 * A list of the paths of the regular files found.
	 */
	protected LinkedList<Path> files = newPathLinkedList();

	/**
	 * The number of files visited.
	 */
	protected int filesVisited = 0;

	/**
	 * The number of paths that didn't fit in another category.
	 */
	protected SimpleIntegerProperty otherCount = newSimpleIntegerProperty();

	/**
	 * The number of regular file paths found.
	 */
	protected SimpleIntegerProperty regularFileCount = newSimpleIntegerProperty();

	/**
	 * The number of symbolic link paths found.
	 */
	protected SimpleIntegerProperty symbolicLinkCount = newSimpleIntegerProperty();

	/**
	 * The number of paths that could not be read.
	 */
	protected SimpleIntegerProperty unreadableCount = newSimpleIntegerProperty();

	/**
	 * @return the directoryCount
	 */
	public SimpleIntegerProperty getDirectoryCount() {
		return directoryCount;
	}

	/**
	 * @return the failedAccessCount
	 */
	public SimpleIntegerProperty getFailedAccessCount() {
		return failedAccessCount;
	}

	/**
	 * @return the files
	 */
	public LinkedList<Path> getFiles() {
		return files;
	}

	/**
	 * @return the filesVisited
	 */
	public int getFilesVisited() {
		return filesVisited;
	}

	/**
	 * @return the otherCount
	 */
	public SimpleIntegerProperty getOtherCount() {
		return otherCount;
	}

	/**
	 * @return the regularFileCount
	 */
	public SimpleIntegerProperty getRegularFileCount() {
		return regularFileCount;
	}

	/**
	 * @return the symbolicLinkCount
	 */
	public SimpleIntegerProperty getSymbolicLinkCount() {
		return symbolicLinkCount;
	}

	/**
	 * @return the unreadableCount
	 */
	public SimpleIntegerProperty getUnreadableCount() {
		return unreadableCount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		if (!Files.isReadable(dir)) {
			unreadableCount.set(unreadableCount.get() + 1);

			return FileVisitResult.SKIP_SUBTREE;
		}

		directoryCount.set(directoryCount.get() + 1);

		return super.preVisitDirectory(dir, attrs);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//		if (isCancelled()) {
//			return FileVisitResult.TERMINATE;
//		}

//		if (((++filesVisited) % 1000) == 0) {
//			System.out.println("SubtreeSearchPhase.SearchVisitor.visitFile: filesVisited = " + filesVisited);
//		}

		if (Files.isReadable(file)) {
			if (attrs.isRegularFile()) {
				files.add(file);
				regularFileCount.set(regularFileCount.get() + 1);
			} else if (attrs.isDirectory()) {
				directoryCount.set(directoryCount.get() + 1);
			} else if (attrs.isSymbolicLink()) {
				symbolicLinkCount.set(symbolicLinkCount.get() + 1);
			} else {
				otherCount.set(otherCount.get() + 1);
			}

			return FileVisitResult.CONTINUE;
		} else {
			unreadableCount.set(unreadableCount.get() + 1);

			return FileVisitResult.SKIP_SUBTREE;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//		System.err.println("SubtreeSearchPhase.SearchVisitor.visitFileFailed: file = " + file);
//		System.err.println("SubtreeSearchPhase.SearchVisitor.visitFileFailed: mssg = " + exc.getClass());

		failedAccessCount.set(failedAccessCount.get() + 1);

		return FileVisitResult.SKIP_SUBTREE;
	}

	/**
	 * @return a new object.
	 */
	protected LinkedList<Path> newPathLinkedList() {
		return new LinkedList<Path>();
	}

	/**
	 * @return a new object with its value set to zero.
	 */
	protected SimpleIntegerProperty newSimpleIntegerProperty() {
		return new SimpleIntegerProperty(0);
	}

}
