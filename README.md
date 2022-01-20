# dupfilefinder2
A remake of DupFileFinder (https://github.com/alantcote/dupfilefinder2) with more multithreading and JavaFX instead of Swing. It certainly is faster than DupFileFinder, and is arguably more robust and better tested. One big advantage is that the misleading progress bars have not been carried forward.

It's still evolving and getting debugged. Building unit test cases for the log has been a big help. Anybody know how to write good tests for methods that use a lot of (other people's) static and or final methods? How do you test an abstract class? I've been out of the loop ever since I retired, and I don't remember how to do those things.

There are a few interesting nuggets in here. How about a class that will track and restore your GUI app's window geometry? Or a function to compare N files for equality, partitioning into groups of equal files (reading each only once, approximately)? Or a JavaFX TreeView that presents a fairly normal filesystem explorer, with host system-authentic icons?

If you have JDK, Eclipse, and Maven, you may want to grab a copy of this repository and look around. If you like it, or want to help, contact me. If you have a problem with it, write an issue!
