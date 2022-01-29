package io.github.alantcote.dupfilefinder2.pipeline;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;

import io.github.alantcote.dupfilefinder2.beans.property.FXThreadIntegerProperty;
import io.github.alantcote.dupfilefinder2.pipeline.phase.Phase;
import io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue;
import javafx.beans.property.SimpleIntegerProperty;

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
 */
public class MatchingSubtreeIdentificationPhase extends Phase {
	/**
	 * The number of path groups considered.
	 */
	protected FXThreadIntegerProperty pathGroupsConsideredProperty = newThreadSafeSimpleIntegerProperty();

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
	public SimpleIntegerProperty getPathGroupsConsideredProperty() {
		return pathGroupsConsideredProperty;
	}

	/**
	 * Build a map from parent to child groups that each contain at least one child
	 * of the parent.
	 * 
	 * @param candidateGroupGroup a collection of child groups.
	 * @return the map.
	 */
	protected MultiValuedMap<Path, Collection<Path>> buildParent2CandidateGroupMap(
			Collection<Collection<Path>> candidateGroupGroup) {
		MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap = newPathToPathGroupMultiValuedMap();

		for (Collection<Path> candidateGroup : candidateGroupGroup) {
			for (Path candidate : candidateGroup) {
				if (isCancelled()) {
					return parent2CandidateGroupMap;
				}

				Path parent = candidate.getParent();

				parent2CandidateGroupMap.put(parent, candidateGroup);
			}

			pathGroupsConsideredProperty.increment(1);
		}

		return parent2CandidateGroupMap;
	}

	/**
	 * Find all the subtrees that match one another. We build the subtrees layer by
	 * layer, from the bottom up.
	 * 
	 * @param candidateGroups a collection of groups of matching paths.
	 * @return a collection of groups of matching subtrees.
	 */
	protected Collection<Collection<Path>> buildSubtrees(Collection<Collection<Path>> candidateGroups) {
		Collection<Collection<Path>> subtreeGroups = newPathGroupArrayList();
		Collection<Collection<Path>> duplicateSetGroup;

		while ((duplicateSetGroup = findDuplicateSets(candidateGroups)).size() > 1) {
			// not sure the right criterion is being used here
			if (isCancelled()) {
				return subtreeGroups;
			}

			subtreeGroups.addAll(duplicateSetGroup);

			candidateGroups = duplicateSetGroup;
		}

		return subtreeGroups;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Void call() throws Exception {
		try {
			// gather input and capture the information
			Collection<Collection<Path>> groups = gatherInputGroups();

			if (isCancelled()) {
				return null;
			}

			Collection<Collection<Path>> subtreeGroups = buildSubtrees(groups);

			// publish results
			for (Collection<Path> subtreeGroup : subtreeGroups) {
				if (isCancelled()) {
					return null;
				}

				outputQueue.put(subtreeGroup);
			}
		} catch (Exception e) {
			showException(e);
		}

		outputQueue.put(newPathArrayList()); // EOF convention

		return null;
	}

	/**
	 * Get a single level of matching parents of groups of matching paths.
	 * 
	 * @param candidateGroups a collection of groups of matching paths.
	 * @return the collection of groups of matching parents.
	 */
	protected Collection<Collection<Path>> findDuplicateSets(Collection<Collection<Path>> candidateGroups) {
		Collection<Collection<Path>> duplicateSetGroup = newPathGroupArrayList();

		MultiValuedMap<Integer, Collection<Path>> sizeToGroupMap = groupBySize(candidateGroups);

		for (Integer size : sizeToGroupMap.keySet()) {
			if (isCancelled()) {
				return duplicateSetGroup;
			}

			Collection<Collection<Path>> equalSizedGroups = sizeToGroupMap.get(size);
			MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap = buildParent2CandidateGroupMap(
					equalSizedGroups);
			Collection<Collection<Path>> dupParentGroups = nWayCompare(parent2CandidateGroupMap,
					parent2CandidateGroupMap.keySet());

			Collection<Collection<Path>> validatedDupParentGroups = validateGroupsOnFileSystem(parent2CandidateGroupMap,
					dupParentGroups);

			duplicateSetGroup.addAll(validatedDupParentGroups);
		}

		return duplicateSetGroup;
	}

	/**
	 * Collect all the input groups, passing them on to output, and recording them.
	 * Relying on an empty input group to signal end of file.
	 */
	protected Collection<Collection<Path>> gatherInputGroups() {
		Collection<Path> inputGroup = null;
		Collection<Collection<Path>> groups = newPathGroupArrayList();

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
	protected MultiValuedMap<Integer, Collection<Path>> groupBySize(Collection<Collection<Path>> candidateGroups) {
		MultiValuedMap<Integer, Collection<Path>> sizeToGroupMap = newIntegerToPathGroupMultiValuedMap();

		for (Collection<Path> candidateGroup : candidateGroups) {
			if (isCancelled()) {
				return sizeToGroupMap;
			}

			Integer size = candidateGroup.size();

			sizeToGroupMap.put(size, candidateGroup);
		}

		return sizeToGroupMap;
	}

	/**
	 * @return a new object.
	 */
	protected MultiValuedMap<Integer, Collection<Path>> newIntegerToPathGroupMultiValuedMap() {
		return MultiMapUtils.newListValuedHashMap();
	}

	/**
	 * @return a new object.
	 */
	protected ArrayList<Path> newPathArrayList() {
		return new ArrayList<Path>();
	}

	/**
	 * @return a new object.
	 */
	protected Collection<Collection<Path>> newPathGroupArrayList() {
		return new ArrayList<Collection<Path>>();
	}

	/**
	 * @return a new object.
	 */
	protected Map<Collection<Collection<Path>>, Collection<Path>> newPathGroupGroupToPathGroupHashtable() {
		return new Hashtable<Collection<Collection<Path>>, Collection<Path>>();
	}

	/**
	 * @return a new object.
	 */
	protected MultiValuedMap<Path, Collection<Path>> newPathToPathGroupMultiValuedMap() {
		return MultiMapUtils.newListValuedHashMap();
	}

	/**
	 * @return a new object.
	 */
	protected FXThreadIntegerProperty newThreadSafeSimpleIntegerProperty() {
		return new FXThreadIntegerProperty();
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
	protected Collection<Collection<Path>> nWayCompare(MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap,
			Collection<Path> candidateParentGroup) {
		Collection<Collection<Path>> dupParentGroups = newPathGroupArrayList();

		// invert parent2CandidateGroupMap, yielding map of candidate group groups to
		// parents
		Map<Collection<Collection<Path>>, Collection<Path>> childGroupGroupToParentGroup = newPathGroupGroupToPathGroupHashtable();

		for (Path parent : candidateParentGroup) {
			Collection<Collection<Path>> childGroupGroup = parent2CandidateGroupMap.get(parent);
			Collection<Path> parentGroup = childGroupGroupToParentGroup.get(childGroupGroup);

			if (isCancelled()) {
				return dupParentGroups;
			}

			if (parentGroup == null) {
				parentGroup = newPathArrayList();
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
			MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap,
			Collection<Collection<Path>> candidateParentGroups) {
		Collection<Collection<Path>> validatedDupParentGroups = newPathGroupArrayList();

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
	protected Collection<Path> validateOnFileSystem(MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap,
			Collection<Path> candidateParentGroup) {
		Collection<Path> validatedParentGroup = newPathArrayList();
		
		if (!candidateParentGroup.isEmpty()) {
			Path sampleParent = (Path) candidateParentGroup.toArray()[0];
			Collection<Collection<Path>> sampleGroups = parent2CandidateGroupMap.get(sampleParent);
			@SuppressWarnings("unchecked")
			Collection<Path> sampleGroup = (Collection<Path>) sampleGroups.toArray()[0];
			int desiredSize = sampleGroup.size();
			Collection<Path> badParents = newPathArrayList();

			for (Path parent : candidateParentGroup) {
				if (isCancelled()) {
					return validatedParentGroup;
				}

				String[] dirListing = parent.toFile().list();
				int actualSize = (dirListing == null) ? 0 : dirListing.length;

				if (actualSize != desiredSize) {
					badParents.add(parent);
				}
			}

			validatedParentGroup.addAll(candidateParentGroup);
			validatedParentGroup.removeAll(badParents);
		}

		return validatedParentGroup;
	}

}
