/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

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
	protected ThreadSafeSimpleIntegerProperty pathGroupsConsideredProperty = new ThreadSafeSimpleIntegerProperty();

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
	
	/**
	 * Build a map from parent to child groups that each contain at least one child
	 * of the parent.
	 * 
	 * @param candidateGroupGroup a collection of child groups.
	 * @return the map.
	 */
	protected Map<Path, Collection<Collection<Path>>> buildParent2CandidateGroupMap(
			Collection<Collection<Path>> candidateGroupGroup) {
		Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap = new Hashtable<Path, Collection<Collection<Path>>>();

		for (Collection<Path> candidateGroup : candidateGroupGroup) {
			for (Path candidate : candidateGroup) {
				if (isCancelled()) {
					return parent2CandidateGroupMap;
				}

				Path parent = candidate.getParent();

				Collection<Collection<Path>> aGroupGroup = parent2CandidateGroupMap.get(parent);

				if (aGroupGroup == null) {
					aGroupGroup = new ArrayList<Collection<Path>>();

					parent2CandidateGroupMap.put(parent, aGroupGroup);
				}

				aGroupGroup.add(candidateGroup);
			}
			
			pathGroupsConsideredProperty.increment(1);
		}

		return parent2CandidateGroupMap;
	}

	/**
	 * Find all the subtrees that match one another. We build the subtrees layer
	 * by layer, from the bottom up.
	 * @param candidateGroups a collection of groups of matching paths.
	 * @return a collection of groups of matching subtrees.
	 */
	protected Collection<Collection<Path>> buildSubtrees(Collection<Collection<Path>> candidateGroups) {
		Collection<Collection<Path>> subtreeGroups = new ArrayList<Collection<Path>>();
		Collection<Collection<Path>> duplicateSetGroup;
		
		System.out.println("MatchingSubtreeIdentificationPhase.buildSubtrees(): entry");
		
		while ((duplicateSetGroup = findDuplicateSets(candidateGroups)).size() > 1) {
			// not sure the right criterion is being used here
			if (isCancelled()) {
				return subtreeGroups;
			}
			
			System.out.println("MatchingSubtreeIdentificationPhase.buildSubtrees(): duplicateSetGroup.size() = " + duplicateSetGroup.size());
			
			for (Collection<Path> duplicateSet : duplicateSetGroup) {
				System.out.println("MatchingSubtreeIdentificationPhase.buildSubtrees(): duplicateSet = " + duplicateSet);
			}
			
			subtreeGroups.addAll(duplicateSetGroup);
			
			candidateGroups = duplicateSetGroup;
		}
		
		System.out.println("MatchingSubtreeIdentificationPhase.buildSubtrees(): method completed.");
		
		return subtreeGroups;
	}

	@Override
	protected Void call() throws Exception {
		try {
			// gather input and capture the information
			Collection<Collection<Path>> groups = gatherInputGroups();

			System.out.println("MatchingSubtreeIdentificationPhase.call(): " + groups.size() + " input groups");

			if (isCancelled()) {
				return null;
			}

			Collection<Collection<Path>> subtreeGroups = buildSubtrees(groups);

			System.out.println("MatchingSubtreeIdentificationPhase.call(): publishing" + subtreeGroups.size() + " subtree groups");
			
			// publish results
			for (Collection<Path> subtreeGroup : subtreeGroups) {
				if (isCancelled()) {
					return null;
				}
				
				outputQueue.put(subtreeGroup);
			}
		} catch (Exception e) {
			System.err.println("MatchingSubtreeIdentificationPhase.call(): caught " + e.getMessage());
			e.printStackTrace();
		}

		outputQueue.put(new ArrayList<Path>()); // EOF convention

		System.out.println("MatchingSubtreeIdentificationPhase.call(): method completed");

		return null;
	}

	/**
	 * Get a single level of matching parents of groups of matching paths.
	 * 
	 * @param candidateGroups a collection of groups of matching paths.
	 * @return the collection of groups of matching parents.
	 */
	protected Collection<Collection<Path>> findDuplicateSets(Collection<Collection<Path>> candidateGroups) {
		Collection<Collection<Path>> duplicateSetGroup = new ArrayList<Collection<Path>>();
		
		System.out.println("MatchingSubtreeIdentificationPhase.findDuplicateSets(): entry");
		
		Map<Integer, Collection<Collection<Path>>> sizeToGroupMap = groupBySize(candidateGroups);

		for (Integer size : sizeToGroupMap.keySet()) {
			if (isCancelled()) {
				return duplicateSetGroup;
			}
			
			System.out.println("MatchingSubtreeIdentificationPhase.findDuplicateSets(): size = " + size);

			Collection<Collection<Path>> equalSizedGroups = sizeToGroupMap.get(size);
			Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap = buildParent2CandidateGroupMap(
					equalSizedGroups);
			Collection<Collection<Path>> dupParentGroups = nWayCompare(parent2CandidateGroupMap,
					parent2CandidateGroupMap.keySet());
			
			System.out.println("MatchingSubtreeIdentificationPhase.findDuplicateSets(): dupParentGroups.size() = " + dupParentGroups.size());
			
			Collection<Collection<Path>> validatedDupParentGroups = validateGroupsOnFileSystem(parent2CandidateGroupMap,
					dupParentGroups);
			
			System.out.println("MatchingSubtreeIdentificationPhase.findDuplicateSets(): validatedDupParentGroups.size() = " + validatedDupParentGroups.size());

			duplicateSetGroup.addAll(validatedDupParentGroups);
		}
		
		System.out.println("MatchingSubtreeIdentificationPhase.findDuplicateSets(): method completed.");

		return duplicateSetGroup;
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
				if (isCancelled() || (inputGroup.isEmpty())) { // EOF convention
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
	 * Build a map from size of candidate group to candidate group.
	 * 
	 * @param candidateGroups The candidate groups.
	 * @return The map.
	 */
	protected Map<Integer, Collection<Collection<Path>>> groupBySize(Collection<Collection<Path>> candidateGroups) {
		Map<Integer, Collection<Collection<Path>>> sizeToGroupMap = new Hashtable<Integer, Collection<Collection<Path>>>();

		for (Collection<Path> candidateGroup : candidateGroups) {
			if (isCancelled()) {
				return sizeToGroupMap;
			}

			Integer size = candidateGroup.size();

			Collection<Collection<Path>> collections = sizeToGroupMap.get(size);

			if (collections == null) {
				collections = new ArrayList<Collection<Path>>();

				sizeToGroupMap.put(size, collections);
			}

			collections.add(candidateGroup);
		}

		return sizeToGroupMap;
	}

	/**
	 * Find the collections of parents that share child candidate groups from a
	 * collection of candidate parents.
	 * 
	 * @param parent2CandidateGroupMap a map from parent to candidate groups. each
	 *                                 of which contains at least one entry that is
	 *                                 a child of the parent.
	 * @param candidateParentGroup     the candidate parents.
	 * @return
	 */
	protected Collection<Collection<Path>> nWayCompare(Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap,
			Collection<Path> candidateParentGroup) {
		Collection<Collection<Path>> dupParentGroups = new ArrayList<Collection<Path>>();

		// invert parent2CandidateGroupMap, yielding map of candidate group groups to
		// parents
		Map<Collection<Collection<Path>>, Collection<Path>> childGroupGroupToParentGroup = new Hashtable<Collection<Collection<Path>>, Collection<Path>>();

		for (Path parent : candidateParentGroup) {
			Collection<Collection<Path>> childGroupGroup = parent2CandidateGroupMap.get(parent);
			Collection<Path> parentGroup = childGroupGroupToParentGroup.get(childGroupGroup);

			if (isCancelled()) {
				return dupParentGroups;
			}

			if (parentGroup == null) {
				parentGroup = new ArrayList<Path>();
				childGroupGroupToParentGroup.put(childGroupGroup, parentGroup);
			}

			parentGroup.add(parent);
		}

// each child group group in childGroupGroupToParentGroup.keySet() has the parents identified in the corresponding parent group; those parents match
// this assumes that hashcodes of collections do not reflect the order of members of those collections. we shall see.

		for (Collection<Collection<Path>> childGroupGroup : childGroupGroupToParentGroup.keySet()) {
			Collection<Path> dupParents = childGroupGroupToParentGroup.get(childGroupGroup);

			if (isCancelled()) {
				return dupParentGroups;
			}

			if (dupParents.size() > 1) {
				dupParentGroups.add(dupParents);
			}
		}

		return dupParentGroups;
	}

	/**
	 * Check that each candidate parent has the right number of directory entries.
	 * 
	 * @param parent2CandidateGroupMap a map from parent to candidate groups.
	 * @param candidateParentGroups    the parent groups to check.
	 * @return the subset of candidateParentGroups that conform.
	 */
	protected Collection<Collection<Path>> validateGroupsOnFileSystem(
			Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap,
			Collection<Collection<Path>> candidateParentGroups) {
		Collection<Collection<Path>> validatedDupParentGroups = new ArrayList<Collection<Path>>();

		for (Collection<Path> parentGroup : candidateParentGroups) {
			if (isCancelled()) {
				return validatedDupParentGroups;
			}

			if (parentGroup.size() > 1) {
				Collection<Path> validatedWithFileSystem = validateOnFileSystem(parent2CandidateGroupMap, parentGroup);

				if (validatedWithFileSystem.size() > 1) {
					validatedDupParentGroups.add(validatedWithFileSystem);
				}
			}

		}

		return validatedDupParentGroups;
	}

	/**
	 * Get the subset of a candidate parent group for which each parent has the same
	 * number of directory entries as each of the parents has child groups.
	 * 
	 * @param parent2CandidateGroupMap a map from parent to candidate groups. each
	 *                                 of which contains at least one entry that is
	 *                                 a child of the parent.
	 * @param candidateParentGroup     a group of parents.
	 * @return the subset.
	 */
	protected Collection<Path> validateOnFileSystem(Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap,
			Collection<Path> candidateParentGroup) {
		Collection<Path> validatedParentGroup = new ArrayList<Path>();
		Path sampleParent = (Path) candidateParentGroup.toArray()[0];
		Collection<Collection<Path>> sampleGroups = parent2CandidateGroupMap.get(sampleParent);
		@SuppressWarnings("unchecked")
		Collection<Path> sampleGroup = (Collection<Path>) sampleGroups.toArray()[0];
		int desiredSize = sampleGroup.size();
		Collection<Path> badParents = new ArrayList<Path>();

		for (Path parent : candidateParentGroup) {
			if (isCancelled()) {
				return validatedParentGroup;
			}

			String[] dirListing = parent.toFile().list();
			int actualSize = dirListing.length;

			if (actualSize != desiredSize) {
				badParents.add(parent);
			}
		}

		validatedParentGroup.addAll(candidateParentGroup);
		validatedParentGroup.removeAll(badParents);

		return validatedParentGroup;
	}

}
