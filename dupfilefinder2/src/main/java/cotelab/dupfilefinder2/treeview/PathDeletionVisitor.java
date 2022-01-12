package cotelab.dupfilefinder2.treeview;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PathDeletionVisitor extends SimpleFileVisitor<Path> {

	public PathDeletionVisitor() {
		super();
	}

	/**
	 * {@inheritDoc} This method deletes the visited directory.
	 */
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
		if (e == null) {
			doDelete(dir);

			return FileVisitResult.CONTINUE;
		} else {
			// directory iteration failed
			throw e;
		}
	}

	/**
	 * {@inheritDoc} This method deletes the visited file.
	 */
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		doDelete(file);

		return FileVisitResult.CONTINUE;
	}

	protected void doDelete(Path aPath) throws IOException {
		Files.delete(aPath);
	}
}
