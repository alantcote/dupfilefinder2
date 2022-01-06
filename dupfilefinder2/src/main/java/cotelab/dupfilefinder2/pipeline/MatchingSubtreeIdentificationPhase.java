/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 * A {@link Phase} designed to identify matching subtrees. The input is a
 * sequence of collections of pathnames to be examined. The output is ...
 * <ul>
 * <li>A copy of the input.</li>
 * <li>A sequence of collections of matching subtree directory pathnames.</li>
 * </ul>
 * A pair of "matching subtrees" is a pair of directories for which the
 * following are true:
 * <ul>
 * <li>The subtrees have identical structure.</li>
 * <li>Each of the regular files in one subtree matches the corresponding
 * regular file in the other subtree.</li>
 * </ul>
 * 
 * @author alantcote
 *
 */
public class MatchingSubtreeIdentificationPhase extends Phase {
	protected ThreadSafeSimpleIntegerProperty pathGroupsConsideredProperty =
			new ThreadSafeSimpleIntegerProperty(); 

	/**
	 * Construct a new object.
	 * 
	 * @param name      a name for this object.
	 * @param theInput  the input queue.
	 * @param theOutput the output queue.
	 */
	public MatchingSubtreeIdentificationPhase(String name, PipelineQueue theInput, PipelineQueue theOutput) {
		super(name, theInput, theOutput);
	}

	/**
	 * @return the pathGroupsConsideredProperty
	 */
	public ThreadSafeSimpleIntegerProperty getPathGroupsConsideredProperty() {
		return pathGroupsConsideredProperty;
	}

	@Override
	protected Void call() throws Exception {
		// gather input and capture the information
		Collection<Collection<Path>> groups = gatherInputGroups();

		System.out.println("MatchingSubtreeIdentificationPhase.call(): " + groups.size() + " input groups");

		if (isCancelled()) {
			return null;
		}

		// recursively seek duplicate parents
		Collection<Collection<Path>> parentGroups = new ArrayList<Collection<Path>>();

		if (groups.size() > 1) {
			System.out.println("MatchingSubtreeIdentificationPhase.call(): calling findDupParentGroups()");

			parentGroups = findDupParentGroups(groups);
		}

		if (isCancelled()) {
			return null;
		}

		System.out.println(
				"MatchingSubtreeIdentificationPhase.call(): publishing " + parentGroups.size() + " parent groups");

		// publish the duplicate parent groups
		for (Collection<Path> group : parentGroups) {
			if (isCancelled()) {
				return null;
			}

			outputQueue.put(group);
		}

		System.out.println("MatchingSubtreeIdentificationPhase.call(): method completed");

		return null;
	}

	/**
	 * Find parents of duplicates identified in groups that are, in turn,
	 * duplicates, and so forth, recursively.
	 * 
	 * @param parentGroups groups of parents of entries in groups that are
	 *                     duplicates. Note that these parents are, by definition,
	 *                     directories.
	 * @param groups       groups of known duplicates to consider
	 * @return
	 */
	protected Collection<Collection<Path>> findDupParentGroups(Collection<Collection<Path>> groups) {
		// map parents to children
		Hashtable<Path, Collection<Collection<Path>>> parent2KidGroups = new Hashtable<Path, Collection<Collection<Path>>>();
		Collection<Collection<Path>> matchingParentsGroups = new ArrayList<Collection<Path>>();

		System.out
				.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): groups.size() = " + groups.size());

		if (groups.size() < 2) {
			return matchingParentsGroups;
		}

		if (isCancelled()) {
			return matchingParentsGroups;
		}

		System.out.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): building parent2Kid");

		for (Collection<Path> pathColl : groups) {
			for (Path kid : pathColl) {
				if (isCancelled()) {
					return matchingParentsGroups;
				}

				System.out
						.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): finding parent of " + kid);

				Path parent = kid.getParent();

				System.out.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): parent = " + parent);

				Collection<Collection<Path>> kidsOfParent = parent2KidGroups.get(parent);

				if (kidsOfParent == null) {
					kidsOfParent = new ArrayList<Collection<Path>>();
					parent2KidGroups.put(parent, kidsOfParent);
				}

				kidsOfParent.add(pathColl);
			}
		}

		if (isCancelled()) {
			return matchingParentsGroups;
		}

//		System.out.println(
//				"MatchingSubtreeIdentificationPhase.findDupParentGroups(): bucketing parents by nbr kid groups");
//
//		Hashtable<Integer, Collection<Path>> nbrKidGroups2ParentGroup = new Hashtable<Integer, Collection<Path>>();
//
//		for (Path parentPath : parent2KidGroups.keySet()) {
//			Collection<Collection<Path>> kidGroups = parent2KidGroups.get(parentPath);
//			Integer nbrKidGroups = kidGroups.size();
//			Collection<Path> parents = nbrKidGroups2ParentGroup.get(nbrKidGroups);
//
//			if (isCancelled()) {
//				return matchingParentsGroups;
//			}
//
//			if (parents == null) {
//				parents = new ArrayList<Path>();
//
//				nbrKidGroups2ParentGroup.put(nbrKidGroups, parents);
//			}
//
//			if (!parents.contains(parentPath)) {
//				parents.add(parentPath);
//			}
//		}
//
//		if (isCancelled()) {
//			return matchingParentsGroups;
//		}
//
//		System.out.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): making bucketList");
//
//		Collection<Collection<Path>> bucketList = new ArrayList<Collection<Path>>();
//
//		for (Integer nbrKids : nbrKidGroups2ParentGroup.keySet()) {
//			Collection<Path> parentGroup = nbrKidGroups2ParentGroup.get(nbrKids);
//
//			bucketList.add(parentGroup);
//		}
//
//		if (isCancelled()) {
//			return matchingParentsGroups;
//		}
		
		// make bucketList, a list containing one list that contains all the parents

		System.out.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): making initial bucket list");
		
		Collection<Collection<Path>> bucketList = new ArrayList<Collection<Path>>();
		ArrayList<Path> initialBucket = new ArrayList<Path>();
		
		for (Path parent : parent2KidGroups.keySet()) {
			initialBucket.add(parent);
		}
		
		bucketList.add(initialBucket);

		if (isCancelled()) {
			return matchingParentsGroups;
		}

		System.out.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): calling matchKidGroupParents()");

		bucketList = matchKidGroupParents(parent2KidGroups, bucketList);

		if (isCancelled()) {
			return matchingParentsGroups;
		}

		Collection<Collection<Path>> newBucketList = new ArrayList<Collection<Path>>();
		
		for (Collection<Path> bucket : bucketList) {
			if (bucket.size() > 1) {
				newBucketList.add(bucket);
			}
		}

		if (isCancelled()) {
			return matchingParentsGroups;
		}

		System.out
				.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): calling checkDuplicateDirGroups()");

		bucketList = checkDuplicateDirGroups(parent2KidGroups, newBucketList);

		matchingParentsGroups.addAll(bucketList);
		
		for (Collection<Path> bucket : bucketList) {
			if (bucket.size() > 1) {
				matchingParentsGroups.add(bucket);
			}
		}

		if (isCancelled()) {
			return matchingParentsGroups;
		}

		if (matchingParentsGroups.size() > 1) {
			// build the tree upward. some of the newly-found groups may have parents in common
			System.out.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): recursing");

			bucketList = findDupParentGroups(matchingParentsGroups);

			if (isCancelled()) {
				return matchingParentsGroups;
			}

			// accumulate the groups of 2 or more into the matching parents group
			for (Collection<Path> group : bucketList) {
				if (group.size() > 1) {
					matchingParentsGroups.add(group);
				}
			}
		}

		System.out.println("MatchingSubtreeIdentificationPhase.findDupParentGroups(): method completed");

		return matchingParentsGroups;
	}

	/**
	 * Collect all the input groups, passing them on to output, and recording them.
	 * Relying on an empty input group to signal end of file.
	 */
	protected Collection<Collection<Path>> gatherInputGroups() {
		Collection<Path> inputGroup = null;
		ArrayList<Collection<Path>> groups = new ArrayList<Collection<Path>>();

		// assuming there will be at least one input group
		try {
			inputGroup = inputQueue.take();
		} catch (InterruptedException e1) {
//			// Auto-generated catch block
//			e1.printStackTrace();
		}

		try {
			do {
				if (isCancelled() || (inputGroup.size() == 0)) {
					break;
				}

				try {
					outputQueue.put(inputGroup);
				} catch (InterruptedException e) {
					// Auto-generated catch block
//				e.printStackTrace();
				}

				if (isCancelled()) {
					break;
				}

				groups.add(inputGroup);
			} while ((inputGroup = inputQueue.take()) != null);
		} catch (InterruptedException e) {
			// Auto-generated catch block
//			e.printStackTrace();
		}

		return groups;
	}

	/**
	 * Find parent groups from a bucket list that have the same number of directory
	 * entries per parent as the parent has child groups.
	 * 
	 * @param parent2KidGroups a map from parent to child groups.
	 * @param bucketList       the bucket list.
	 * @return the parent groups. Each parent group has at least two members.
	 */
	Collection<Collection<Path>> checkDuplicateDirGroups(Hashtable<Path, Collection<Collection<Path>>> parent2KidGroups,
			Collection<Collection<Path>> bucketList) {
		Collection<Collection<Path>> checkedParentGroups = new ArrayList<Collection<Path>>();

		for (Collection<Path> parentGroup : bucketList) {
			Path badParent = null;

			for (Path parent : parentGroup) {
				int nbrDirEntriesExpected = parent2KidGroups.get(parent).size();
				int nbrDirEntriesFound = parent.toFile().list().length;

				if (isCancelled()) {
					return checkedParentGroups;
				}

				if (nbrDirEntriesExpected != nbrDirEntriesFound) {
					badParent = parent;

					break;
				}
			}

			if (badParent != null) {
				parentGroup.remove(badParent);

				if (parentGroup.size() < 2) {
					bucketList.remove(parentGroup);
				}

				if (isCancelled()) {
					return checkedParentGroups;
				}

				checkedParentGroups = checkDuplicateDirGroups(parent2KidGroups, bucketList);

				break;
			}
		}

		checkedParentGroups.addAll(bucketList);

		return checkedParentGroups;
	}

	/**
	 * Match up the child groups of the parents in the bucket list.
	 * 
	 * @param parent2KidGroups a map from parent to child groups.
	 * @param bucketList       the bucket list.
	 * @return a list of groups of parents such that the child groups of the parents
	 *         in each group match. Each such group of parents has at least two
	 *         members.
	 */
	Collection<Collection<Path>> matchKidGroupParents(Hashtable<Path, Collection<Collection<Path>>> parent2KidGroups,
			Collection<Collection<Path>> bucketList) {
		Collection<Collection<Path>> result = new ArrayList<Collection<Path>>();
		Hashtable<Collection<Collection<Path>>, Collection<Path>> buckets = new Hashtable<Collection<Collection<Path>>, Collection<Path>>();
		
		System.out.println("MatchingSubtreeIdentificationPhase.matchKidGroupParents(): bucketList = " + bucketList);

		for (Collection<Path> parentGroup : bucketList) {
			if (isCancelled()) {
				return result;
			}
			
			buckets.clear();

			for (Path parent : parentGroup) {
				Collection<Path> parents = buckets.get(parent2KidGroups.get(parent));

				if (parents == null) {
					parents = new ArrayList<Path>();

					buckets.put(parent2KidGroups.get(parent), parents);
				}

				parents.add(parent);

				if (isCancelled()) {
					return result;
				}
				
				pathGroupsConsideredProperty.increment(parent2KidGroups.get(parent).size());
			}

			System.out.println("MatchingSubtreeIdentificationPhase.matchKidGroupParents(): buckets.size() = " + buckets.size());

			if (buckets.size() > 1) {
				// happy day! this parentGroup checks out
				result.add(parentGroup);
			} else {
				Collection<Collection<Path>> newBucketList = new ArrayList<Collection<Path>>();

				for (Collection<Collection<Path>> key : buckets.keySet()) {
					Collection<Path> bucket = buckets.get(key);

					if (bucket.size() > 1) {
						newBucketList.add(bucket);
					}
				}

				System.out.println("MatchingSubtreeIdentificationPhase.matchKidGroupParents(): calling matchKidGroupParents()");

				Collection<Collection<Path>> recursionResult = matchKidGroupParents(parent2KidGroups, newBucketList);

				System.out.println("MatchingSubtreeIdentificationPhase.matchKidGroupParents(): recursionResult = " + recursionResult);

				result.addAll(recursionResult);
			}
		}
		
		System.out.println("MatchingSubtreeIdentificationPhase.matchKidGroupParents(): method completed");

		return result;
	}

}
