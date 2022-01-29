package io.github.alantcote.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.MultiValuedMap;
import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase;
import io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;

/**
 * Test case for
 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase}.
 */
@RunWith(JfxRunner.class)
public class MatchingSubtreeIdentificationPhaseTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#buildParent2CandidateGroupMap(java.util.Collection)}.
	 */
	@Test
	public void testBuildParent2CandidateGroupMap() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Collection<Collection<Path>> candidateGroupGroup = new ArrayList<Collection<Path>>();

		MultiValuedMap<Path, Collection<Path>> result = fixture.buildParent2CandidateGroupMap(candidateGroupGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#buildSubtrees(java.util.Collection)}.
	 */
	@Test
	public void testBuildSubtrees() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Collection<Collection<Path>> candidateGroupGroup = new ArrayList<Collection<Path>>();

		Collection<Collection<Path>> result = fixture.buildSubtrees(candidateGroupGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#call()}.
	 * 
	 * @throws Exception if thrown by the code under test.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCall() throws Exception {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		final Collection<Collection<Path>> groups = new ArrayList<Collection<Path>>();
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput) {

			@Override
			protected Collection<Collection<Path>> buildSubtrees(Collection<Collection<Path>> candidateGroups) {
				return groups;
			}

			@Override
			protected Collection<Collection<Path>> gatherInputGroups() {
				return groups;
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockOutput).put(with(any(Collection.class)));
			}
		});

		assertNull(fixture.call());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#findDuplicateSets(java.util.Collection)}.
	 */
	@Test
	public void testFindDuplicateSets() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Collection<Collection<Path>> candidateGroupGroup = new ArrayList<Collection<Path>>();

		Collection<Collection<Path>> result = fixture.findDuplicateSets(candidateGroupGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#gatherInputGroups()}.
	 * 
	 * @throws InterruptedException if thrown by the code under test.
	 */
	@Test
	public void testGatherInputGroups() throws InterruptedException {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		context.checking(new Expectations() {
			{
				oneOf(mockInput).take();
				will(returnValue(new ArrayList<Path>()));
			}
		});

		Collection<Collection<Path>> result = fixture.gatherInputGroups();

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#getPathGroupsConsideredProperty()}.
	 */
	@Test
	public void testGetPathGroupsConsideredProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertEquals(fixture.pathGroupsConsideredProperty, fixture.getPathGroupsConsideredProperty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#groupBySize(java.util.Collection)}.
	 */
	@Test
	public void testGroupBySize() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Collection<Collection<Path>> candidateGroupGroup = new ArrayList<Collection<Path>>();

		MultiValuedMap<Integer, Collection<Path>> result = fixture.groupBySize(candidateGroupGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#MatchingSubtreeIdentificationPhase(java.lang.String, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue, io.github.alantcote.dupfilefinder2.pipeline.queueing.PipelineQueue)}.
	 */
	@Test
	public void testMatchingSubtreeIdentificationPhase() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertEquals("phase", fixture.getPhaseName().get());
		assertEquals(mockInput, fixture.getInputQueue());
		assertEquals(mockOutput, fixture.getOutputQueue());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newIntegerToPathGroupMultiValuedMap()}.
	 */
	@Test
	public void testNewIntegerToPathGroupGroupHashtable() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newIntegerToPathGroupMultiValuedMap());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathArrayList()}.
	 */
	@Test
	public void testNewPathArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newPathArrayList());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathGroupArrayList()}.
	 */
	@Test
	public void testNewPathGroupArrayList() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newPathGroupArrayList());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathGroupGroupToPathGroupHashtable()}.
	 */
	@Test
	public void testNewPathGroupGroupToPathGroupHashtable() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newPathGroupGroupToPathGroupHashtable());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathToPathGroupMultiValuedMap()}.
	 */
	@Test
	public void testNewPathToPathGroupGroupHashtable() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newPathToPathGroupMultiValuedMap());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newThreadSafeSimpleIntegerProperty()}.
	 */
	@Test
	public void testNewThreadSafeSimpleIntegerProperty() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newThreadSafeSimpleIntegerProperty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#nWayCompare(java.util.Map, java.util.Collection)}.
	 */
	@Test
	public void testNWayCompare() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap = fixture.newPathToPathGroupMultiValuedMap();
		Collection<Path> candidateParentGroup = fixture.newPathArrayList();

		Collection<Collection<Path>> dupParentGroups = fixture.nWayCompare(parent2CandidateGroupMap,
				candidateParentGroup);

		assertTrue(dupParentGroups.isEmpty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#validateGroupsOnFileSystem(java.util.Map, java.util.Collection)}.
	 */
	@Test
	public void testValidateGroupsOnFileSystem() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap = fixture.newPathToPathGroupMultiValuedMap();
		Collection<Collection<Path>> candidateParentGroup = fixture.newPathGroupArrayList();

		Collection<Collection<Path>> result = fixture.validateGroupsOnFileSystem(parent2CandidateGroupMap,
				candidateParentGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#validateOnFileSystem(java.util.Map, java.util.Collection)}.
	 */
	@Test
	public void testValidateOnFileSystem() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		MultiValuedMap<Path, Collection<Path>> parent2CandidateGroupMap = fixture.newPathToPathGroupMultiValuedMap();
		Collection<Path> candidateParentGroup = fixture.newPathArrayList();

		Collection<Path> result = fixture.validateOnFileSystem(parent2CandidateGroupMap, candidateParentGroup);

		assertTrue(result.isEmpty());
	}

}