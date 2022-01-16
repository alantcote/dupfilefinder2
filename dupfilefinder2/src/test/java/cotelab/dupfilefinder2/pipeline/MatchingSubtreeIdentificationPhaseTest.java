package cotelab.dupfilefinder2.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.sf.cotelab.jfxrunner.JavaFxJUnit4ClassRunner;

/**
 * Test case for
 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class MatchingSubtreeIdentificationPhaseTest {
	protected Mockery context;
	protected Sequence sequence;

	@After
	public void runAfterTests() throws Exception {
		context.assertIsSatisfied();
	}

	@Before
	public void runBeforeTests() throws Exception {
		context = new Mockery() {
			{
				setThreadingPolicy(new Synchroniser());
				setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
			}
		};

		sequence = context.sequence(getClass().getName());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#buildParent2CandidateGroupMap(java.util.Collection)}.
	 */
	@Test
	public void testBuildParent2CandidateGroupMap() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Collection<Collection<Path>> candidateGroupGroup = new ArrayList<Collection<Path>>();

		Map<Path, Collection<Collection<Path>>> result = fixture.buildParent2CandidateGroupMap(candidateGroupGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#buildSubtrees(java.util.Collection)}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#call()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#findDuplicateSets(java.util.Collection)}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#gatherInputGroups()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#getPathGroupsConsideredProperty()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#groupBySize(java.util.Collection)}.
	 */
	@Test
	public void testGroupBySize() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Collection<Collection<Path>> candidateGroupGroup = new ArrayList<Collection<Path>>();

		Map<Integer, Collection<Collection<Path>>> result = fixture.groupBySize(candidateGroupGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#MatchingSubtreeIdentificationPhase(java.lang.String, cotelab.dupfilefinder2.pipeline.PipelineQueue, cotelab.dupfilefinder2.pipeline.PipelineQueue)}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newIntegerToPathGroupGroupHashtable()}.
	 */
	@Test
	public void testNewIntegerToPathGroupGroupHashtable() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newIntegerToPathGroupGroupHashtable());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathArrayList()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathGroupArrayList()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathGroupGroupToPathGroupHashtable()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newPathToPathGroupGroupHashtable()}.
	 */
	@Test
	public void testNewPathToPathGroupGroupHashtable() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);

		assertNotNull(fixture.newPathToPathGroupGroupHashtable());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#newThreadSafeSimpleIntegerProperty()}.
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
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#nWayCompare(java.util.Map, java.util.Collection)}.
	 */
	@Test
	public void testNWayCompare() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap = fixture.newPathToPathGroupGroupHashtable();
		Collection<Path> candidateParentGroup = fixture.newPathArrayList();

		Collection<Collection<Path>> dupParentGroups = fixture.nWayCompare(parent2CandidateGroupMap,
				candidateParentGroup);

		assertTrue(dupParentGroups.isEmpty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#validateGroupsOnFileSystem(java.util.Map, java.util.Collection)}.
	 */
	@Test
	public void testValidateGroupsOnFileSystem() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap = fixture.newPathToPathGroupGroupHashtable();
		Collection<Collection<Path>> candidateParentGroup = fixture.newPathGroupArrayList();

		Collection<Collection<Path>> result = fixture.validateGroupsOnFileSystem(parent2CandidateGroupMap,
				candidateParentGroup);

		assertTrue(result.isEmpty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase#validateOnFileSystem(java.util.Map, java.util.Collection)}.
	 */
	@Test
	public void testValidateOnFileSystem() {
		final PipelineQueue mockInput = context.mock(PipelineQueue.class, "mockInput");
		final PipelineQueue mockOutput = context.mock(PipelineQueue.class, "mockOutput");
		MatchingSubtreeIdentificationPhase fixture = new MatchingSubtreeIdentificationPhase("phase", mockInput,
				mockOutput);
		Map<Path, Collection<Collection<Path>>> parent2CandidateGroupMap = fixture.newPathToPathGroupGroupHashtable();
		Collection<Path> candidateParentGroup = fixture.newPathArrayList();

		Collection<Path> result = fixture.validateOnFileSystem(parent2CandidateGroupMap, candidateParentGroup);

		assertTrue(result.isEmpty());
	}

}
