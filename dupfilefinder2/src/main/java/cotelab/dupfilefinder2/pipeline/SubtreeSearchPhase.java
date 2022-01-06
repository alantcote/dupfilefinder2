/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A {@link Phase} designed to enumerate the files in designated subtrees of the
 * file system. The input is a single collection containing pathnames of files
 * and directories to be treated as roots of subtrees to be enumerated. The
 * output is a collection of the enumerated regular file pathnames.
 * 
 * @author alantcote
 *
 */
public class SubtreeSearchPhase extends Phase {
	protected class SearchVisitor extends SimpleFileVisitor<Path> {

		protected int filesVisited = 0;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			if (!Files.isReadable(dir)) {
				unreadableCount.set(unreadableCount.get() + 1);

				return FileVisitResult.SKIP_SUBTREE;
			}

			directoryCount.set(directoryCount.get() + 1);

			return super.preVisitDirectory(dir, attrs);
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if (isCancelled()) {
				return FileVisitResult.TERMINATE;
			}

//			if (((++filesVisited) % 1000) == 0) {
//				System.out.println("SubtreeSearchPhase.SearchVisitor.visitFile: filesVisited = " + filesVisited);
//			}

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

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//			System.err.println("SubtreeSearchPhase.SearchVisitor.visitFileFailed: file = " + file);
//			System.err.println("SubtreeSearchPhase.SearchVisitor.visitFileFailed: mssg = " + exc.getClass());

			failedAccessCount.set(failedAccessCount.get() + 1);

			return FileVisitResult.SKIP_SUBTREE;
		}

	}

	public static void main(String[] args) {
		PipelineQueue input = new PipelineQueue(5, "Input Queue");
		PipelineQueue output = new PipelineQueue(5, "Output Queue");
		SubtreeSearchPhase ssp = new SubtreeSearchPhase("Demo SubtreeSearchPhase", input, output);
		LinkedList<Path> searchRoots = new LinkedList<Path>();
		FileSystem fileSystem = FileSystems.getDefault();
		Iterable<Path> rootDirs = fileSystem.getRootDirectories();

		for (Path root : rootDirs) {
			searchRoots.add(root);

//			System.out.println("SubtreeSearchPhase.main(): root = " + root);
		}

		try {
			input.put(searchRoots);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		Date startStamp = new Date();

		try {
			ssp.call();
		} catch (Exception e) {
			e.printStackTrace();
		}

//		Date endStamp = new Date();
//		long elapsed = endStamp.getTime() - startStamp.getTime();
//		long eHours = elapsed / (60 * 60 * 1000);
//		long eMinutes = (elapsed / (60 * 1000)) % 60;
//		long eSeconds = (elapsed / 1000) % 60;
//		long eMillis = elapsed % 1000;
//		String elapsedString = "" + eHours + ":" + eMinutes + ":" + eSeconds + "." + eMillis;
		@SuppressWarnings("unused")
		Collection<Path> paths = null;

		try {
			paths = output.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		System.out.println("SubtreeSearchPhase.main(): startStamp = " + startStamp);
//		System.out.println("SubtreeSearchPhase.main(): input.takeCount = " + input.takeCount);
//		System.out.println("SubtreeSearchPhase.main(): output.putCount = " + output.putCount);
//		System.out.println("SubtreeSearchPhase.main(): ssp.directoryCount = " + ssp.directoryCount);
//		System.out.println("SubtreeSearchPhase.main(): ssp.otherCount = " + ssp.otherCount);
//		System.out.println("SubtreeSearchPhase.main(): ssp.regularFileCount = " + ssp.regularFileCount);
//		System.out.println("SubtreeSearchPhase.main(): ssp.symbolicLinkCount = " + ssp.symbolicLinkCount);
//		System.out.println("SubtreeSearchPhase.main(): ssp.unreadableCount = " + ssp.unreadableCount);
//		System.out.println("SubtreeSearchPhase.main(): ssp.failedAccessCount = " + ssp.failedAccessCount);
//		System.out.println("SubtreeSearchPhase.main(): paths.size() = " + paths.size());
//		System.out.println("SubtreeSearchPhase.main(): endStamp = " + endStamp);
//		System.out.println("SubtreeSearchPhase.main(): elapsedString = " + elapsedString);
	}

	protected LinkedList<Path> files = new LinkedList<Path>();
	protected SimpleIntegerProperty directoryCount = new SimpleIntegerProperty(0);
	protected SimpleIntegerProperty failedAccessCount = new SimpleIntegerProperty(0);
	protected SimpleIntegerProperty otherCount = new SimpleIntegerProperty(0);
	protected SimpleIntegerProperty regularFileCount = new SimpleIntegerProperty(0);
	protected SimpleIntegerProperty symbolicLinkCount = new SimpleIntegerProperty(0);
	protected SimpleIntegerProperty unreadableCount = new SimpleIntegerProperty(0);

	/**
	 * Construct a new object.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public SubtreeSearchPhase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super(name, theInput, theOutput);
	}

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

	@Override
	protected Void call() throws Exception {
		Collection<Path> packet;

		while (null != (packet = inputQueue.poll(5, TimeUnit.SECONDS))) {
			if (isCancelled()) {
				break;
			}

			for (Path aPath : packet) {
				if (isCancelled()) {
					break;
				}

				Files.walkFileTree(aPath, new SearchVisitor());
			}
		}

		if (!isCancelled()) {
			outputQueue.put(files);
		}

		return null;
	}

}
