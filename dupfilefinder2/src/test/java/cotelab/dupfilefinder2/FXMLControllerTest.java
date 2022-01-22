package cotelab.dupfilefinder2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import cotelab.dupfilefinder2.javafx.animation.ElapsedTimeTracker;
import cotelab.dupfilefinder2.pipeline.Pipeline;
import cotelab.dupfilefinder2.pipeline.queueing.PipelineQueue;
import cotelab.jfxrunner.JavaFxJUnit4ClassRunner;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import cotelab.util.javafx.scene.control.FileIconFactory;
import cotelab.util.javafx.scene.control.FileTreeView;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * Test case for {@link FXMLController}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class FXMLControllerTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#addAncestors(java.nio.file.Path)}.
	 */
	@Test
	public void testAddAncestors() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		final Path mockParentPath = context.mock(Path.class, "mockParentPath");
		FXMLController fixture = new FXMLController();

		context.checking(new Expectations() {
			{
				oneOf(mockPath).getParent();
				will(returnValue(mockParentPath));

				oneOf(mockParentPath).getParent();
				will(returnValue(null));
			}
		});

		fixture.addAncestors(mockPath);

		assertEquals(1, fixture.ancestorSet.size());
		assertTrue(fixture.ancestorSet.contains(mockParentPath));
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#assembleResults()}.
	 */
	@Test
	public void testAssembleResults() {
		final SimpleIntegerProperty callCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			public void refreshResultAids() {
				callCount.set(1 + callCount.get());
			}

			@Override
			protected void collectResultsFromPipeline() {
				callCount.set(1 + callCount.get());
			}

		};

		fixture.assembleResults();

		assertEquals(2, callCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#assembleResultsTreeView()}.
	 */
	@Test
	public void testAssembleResultsTreeView() {
		final FileIconFactory mockFileIconFactory = context.mock(FileIconFactory.class, "mockFileIconFactory");
		final SimpleIntegerProperty callCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected FileIconFactory newFileIconFactory() {
				callCount.set(1 + callCount.get());

				return mockFileIconFactory;
			}

		};

		fixture.assembleResultsTreeView();

		assertEquals(1, callCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#bind(javafx.beans.property.ReadOnlyObjectProperty, javafx.scene.control.Label)}.
	 */
	@Test
	public void testBindReadOnlyObjectPropertyOfStateLabel() {
		@SuppressWarnings("unchecked")
		final ReadOnlyObjectProperty<State> mockReadOnlyObjectProperty = context.mock(ReadOnlyObjectProperty.class,
				"mockReadOnlyObjectProperty");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		final StringBinding mockStringBinding = context.mock(StringBinding.class, "mockStringBinding");
		FXMLController fixture = new FXMLController();

		context.checking(new Expectations() {
			{
				oneOf(mockReadOnlyObjectProperty).asString();
				will(returnValue(mockStringBinding));
				
				oneOf(mockStringBinding).addListener(with(any(InvalidationListener.class)));
			}
		});

		fixture.bind(mockReadOnlyObjectProperty, mockLabel);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#bind(javafx.beans.property.SimpleIntegerProperty, javafx.scene.control.Label)}.
	 */
	@Test
	public void testBindSimpleIntegerPropertyLabel() {
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		final Label mockLabel = context.mock(Label.class, "mockLabel");
		FXMLController fixture = new FXMLController();

		context.checking(new Expectations() {
			{
				oneOf(mockSimpleIntegerProperty).asString("%,d");
			}
		});

		fixture.bind(mockSimpleIntegerProperty, mockLabel);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#buildAncestorSet()}.
	 */
	@Test
	public void testBuildAncestorSet() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		final ArrayList<Collection<Path>> dupCollections = new ArrayList<Collection<Path>>();
		Collection<Path> pathGroup = new ArrayList<Path>();
		final SimpleIntegerProperty callCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {
			protected void addAncestors(Path path) {
				assertEquals(mockPath, path);

				callCount.set(1 + callCount.get());
			}
		};

		pathGroup.add(mockPath);
		dupCollections.add(pathGroup);
		fixture.dupCollections = dupCollections;

		fixture.buildAncestorSet();

		assertEquals(1, callCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#buildPathToDupCollMap()}.
	 */
	@Test
	public void testBuildPathToDupCollMap() {
		final Path mockPath = context.mock(Path.class, "mockPath");
		final ArrayList<Collection<Path>> dupCollections = new ArrayList<Collection<Path>>();
		Collection<Path> pathGroup = new ArrayList<Path>();
		FXMLController fixture = new FXMLController();

		pathGroup.add(mockPath);
		dupCollections.add(pathGroup);
		fixture.dupCollections = dupCollections;

		fixture.buildPathToDupCollMap();

		assertEquals(1, fixture.pathToDupCollMap.size());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#collectResultsFromPipeline()}.
	 */
	@Test
	public void testCollectResultsFromPipeline() {
		final PipelineQueue mockPipelineQueue = context.mock(PipelineQueue.class, "mockPipelineQueue");
		final Path mockPath = context.mock(Path.class, "mockPath");
		Collection<Path> pathGroup = new ArrayList<Path>();
		FXMLController fixture = new FXMLController();

		context.checking(new Expectations() {
			{
				oneOf(mockPipelineQueue).poll();
				will(returnValue(pathGroup));

				oneOf(mockPipelineQueue).poll();
				will(returnValue(null));
			}
		});

		fixture.output = mockPipelineQueue;
		pathGroup.add(mockPath);

		fixture.collectResultsFromPipeline();

		assertTrue(fixture.dupCollections.isEmpty());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#createFileTreeView()}.
	 */
	@Test
	public void testCreateFileTreeView() {
		FXMLController fixture = new FXMLController();
		FileTreeView ftv = fixture.createFileTreeView();

		assertNotNull(ftv);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#getResultsTreeView()}.
	 */
	@Test
	public void testGetResultsTreeView() {
		FXMLController fixture = new FXMLController();
		final FileTreeView mockFileTreeView = context.mock(FileTreeView.class, "mockFileTreeView");

		fixture.resultsTreeView = mockFileTreeView;

		TreeView<File> actual = fixture.getResultsTreeView();

		assertEquals(mockFileTreeView, actual);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#initialize(java.net.URL, java.util.ResourceBundle)}.
	 */
	@Test
	public void testInitialize() {
		final SimpleIntegerProperty startHeapMonitorCallCount = new SimpleIntegerProperty(0);
		BorderPane rootPane = new BorderPane();
		MenuItem menuItem = new MenuItem();
		Button button = new Button();
		FXMLController fixture = new FXMLController() {

			@Override
			protected ElapsedTimeTracker newElapsedTimeTracker(Label aLabel) {
				return null;
			}

			@Override
			protected void startHeapTracker() {
				startHeapMonitorCallCount.set(1 + startHeapMonitorCallCount.get());
			}

		};

		fixture.rootPane = rootPane;
		fixture.fileCloseMenuItem = menuItem;
		fixture.helpUsageMenuItem = menuItem;
		fixture.helpAboutMenuItem = menuItem;
		fixture.startButton = button;
		fixture.cancelButton = button;

		fixture.initialize(null, null);

		assertEquals(1, startHeapMonitorCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#newPathHashSet()}.
	 */
	@Test
	public void testNewPathHashSet() {
		FXMLController fixture = new FXMLController();
		HashSet<Path> result = fixture.newPathHashSet();

		assertNotNull(result);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#refreshResultAids()}.
	 */
	@Test
	public void testRefreshResultAids() {
		final SimpleIntegerProperty buildAncestorSetCallCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty buildPathToDupCollMapCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void buildAncestorSet() {
				buildAncestorSetCallCount.set(1 + buildAncestorSetCallCount.get());
			}

			@Override
			protected void buildPathToDupCollMap() {
				buildPathToDupCollMapCallCount.set(1 + buildPathToDupCollMapCallCount.get());
			}
		};

		fixture.refreshResultAids();

		assertEquals(1, buildAncestorSetCallCount.get());
		assertEquals(1, buildPathToDupCollMapCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setupBrowserLauncher()}.
	 */
	@Test
	public void testSetupBrowserLauncher() {
		BorderPane rootPane = new BorderPane();
		FXMLController fixture = new FXMLController();

		fixture.rootPane = rootPane;

		fixture.setupBrowserLauncher();

		assertNotNull(fixture.browserLauncher);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpGroupByContent2MatchingSubtreeIdentificationQueueListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpGroupByContent2MatchingSubtreeIdentificationQueueListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindCallCount.set(1 + bindCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getGBC2MSIQueueName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).getGBC2MSIQueuePutCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getGBC2MSIQueueTakeCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		fixture.gbc2msiQueueName = label;
		fixture.gbc2msiPutCount = label;
		fixture.gbc2msiTakeCount = label;

		fixture.setUpGroupByContent2MatchingSubtreeIdentificationQueueListeners(mockPipeline);

		assertEquals(2, bindCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpGroupByContentPhaseListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpGroupByContentPhaseListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		@SuppressWarnings("unchecked")
		final ReadOnlyObjectProperty<State> mockReadOnlyObjectProperty = context.mock(ReadOnlyObjectProperty.class,
				"mockReadOnlyObjectProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		final SimpleLongProperty mockSimpleLongProperty = context.mock(SimpleLongProperty.class,
				"mockSimpleLongProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindTSSIPCallCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty bindSLPCallCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty bindSOPCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(ReadOnlyObjectProperty<State> roop, Label label) {
				bindSOPCallCount.set(1 + bindSOPCallCount.get());
			}

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindTSSIPCallCount.set(1 + bindTSSIPCallCount.get());
			}

			@Override
			protected void bind(SimpleLongProperty tsslp, Label label) {
				bindSLPCallCount.set(1 + bindSLPCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getGBCPhaseName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).gbcStateProperty();
				will(returnValue(mockReadOnlyObjectProperty));

				oneOf(mockPipeline).getGBCUniqueCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getGBCFilesComparedCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getGBCBytesComparedCount();
				will(returnValue(mockSimpleLongProperty));
			}
		});

		fixture.gbcState = label;
		fixture.gbcName = label;
		fixture.gbcUniqueCount = label;
		fixture.gbcBytesCompared = label;

		fixture.setUpGroupByContentPhaseListeners(mockPipeline);

		assertEquals(2, bindTSSIPCallCount.get());
		assertEquals(1, bindSLPCallCount.get());
		assertEquals(1, bindSOPCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpGroupBySize2GroupByContentQueueListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpGroupBySize2GroupByContentQueueListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindCallCount.set(1 + bindCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getGBS2GBCQueueName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).getGBS2GBCQueuePutCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getGBS2GBCQueueTakeCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		fixture.gbs2gbcQueueName = label;
		fixture.gbs2gbcPutCount = label;
		fixture.gbs2gbcTakeCount = label;

		fixture.setUpGroupBySize2GroupByContentQueueListeners(mockPipeline);

		assertEquals(2, bindCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpGroupBySizePhaseListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpGroupBySizePhaseListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		@SuppressWarnings("unchecked")
		final ReadOnlyObjectProperty<State> mockReadOnlyObjectProperty = context.mock(ReadOnlyObjectProperty.class,
				"mockReadOnlyObjectProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindSIPCallCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty bindSOPCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(ReadOnlyObjectProperty<State> roop, Label label) {
				bindSOPCallCount.set(1 + bindSOPCallCount.get());
			}

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindSIPCallCount.set(1 + bindSIPCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getGBSPhaseName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).gbsStateProperty();
				will(returnValue(mockReadOnlyObjectProperty));

				oneOf(mockPipeline).getGBSFilesMeasuredCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getGBSSizeCount();
				will(returnValue(mockSimpleIntegerProperty));
				oneOf(mockPipeline).getGBSUniqueCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getGBSUnmeasurableCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		fixture.gbsState = label;
		fixture.gbsName = label;
		fixture.gbsUniqueCount = label;
		fixture.gbsFilesMeasuredCount = label;
		fixture.gbsSizeCount = label;
		fixture.gbsUnmeasurableCount = label;

		fixture.setUpGroupBySizePhaseListeners(mockPipeline);

		assertEquals(4, bindSIPCallCount.get());
		assertEquals(1, bindSOPCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpMatchingSubtreeIdentificationPhaseListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpMatchingSubtreeIdentificationPhaseListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		@SuppressWarnings("unchecked")
		final ReadOnlyObjectProperty<State> mockReadOnlyObjectProperty = context.mock(ReadOnlyObjectProperty.class,
				"mockReadOnlyObjectProperty");
		final SimpleIntegerProperty mockThreadSafeSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindSIPCallCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty bindSOPCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(ReadOnlyObjectProperty<State> roop, Label label) {
				bindSOPCallCount.set(1 + bindSOPCallCount.get());
			}

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindSIPCallCount.set(1 + bindSIPCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getMSIPhaseName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).msiStateProperty();
				will(returnValue(mockReadOnlyObjectProperty));

				oneOf(mockPipeline).getMSIPathGroupsConsideredProperty();
				will(returnValue(mockThreadSafeSimpleIntegerProperty));
			}
		});

		fixture.msiState = label;
		fixture.msiName = label;
		fixture.msiPathGroupsConsidered = label;

		fixture.setUpMatchingSubtreeIdentificationPhaseListeners(mockPipeline);

		assertEquals(1, bindSIPCallCount.get());
		assertEquals(1, bindSOPCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpPipeline()}.
	 */
	@Test
	public void testSetUpPipeline() {
		FXMLController fixture = new FXMLController() {
			@Override
			protected void setUpPipelineListeners(Pipeline line) {
				// just a stub
			}
		};
		fixture.subtreeSelectionTreeView = fixture.createFileTreeView();
		fixture.subtreeSelectionTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		Pipeline pipeline = fixture.setUpPipeline();

		assertNotNull(pipeline);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpPipelineInputQueueListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpPipelineInputQueueListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindCallCount.set(1 + bindCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getInputName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).getInputPutCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getInputTakeCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		fixture.pipelineInputQueueName = label;
		fixture.pipelineInputPutCount = label;
		fixture.pipelineInputTakeCount = label;

		fixture.setUpPipelineInputQueueListeners(mockPipeline);

		assertEquals(2, bindCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpPipelineListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpPipelineListeners() {
		Label label = new Label();
		final SimpleIntegerProperty setUpCallCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty bindSOPCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(ReadOnlyObjectProperty<State> roop, Label label) {
				bindSOPCallCount.set(1 + bindSOPCallCount.get());
			}

			@Override
			protected void setUpGroupByContent2MatchingSubtreeIdentificationQueueListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpGroupByContentPhaseListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpGroupBySize2GroupByContentQueueListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpGroupBySizePhaseListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpMatchingSubtreeIdentificationPhaseListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpPipelineInputQueueListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpPipelineOutputQueueListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpSubtreeSearch2GroupBySizeQueueListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

			@Override
			protected void setUpSubtreeSearchPhaseListeners(Pipeline line) {
				setUpCallCount.set(1 + setUpCallCount.get());
			}

		};

		fixture.pipelineName = label;
		fixture.pipelineState = label;

		// semi-integration test to sidestep weird jmock behavior
		fixture.subtreeSelectionTreeView = fixture.createFileTreeView();
		fixture.subtreeSelectionTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		fixture.pipeline = fixture.setUpPipeline();

//		fixture.setUpPipelineListeners(mockPipeline);
		fixture.setUpPipelineListeners(fixture.pipeline);

		assertEquals(18, setUpCallCount.get());
		assertEquals(2, bindSOPCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpPipelineOutputQueueListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpPipelineOutputQueueListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindCallCount.set(1 + bindCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getOutputName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).getOutputPutCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getOutputTakeCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		fixture.pipelineOutputQueueName = label;
		fixture.pipelineOutputPutCount = label;
		fixture.pipelineOutputTakeCount = label;

		fixture.setUpPipelineOutputQueueListeners(mockPipeline);

		assertEquals(2, bindCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpSubtreeSearch2GroupBySizeQueueListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpSubtreeSearch2GroupBySizeQueueListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindCallCount.set(1 + bindCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getSS2GBSQueueName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).getSS2GBSQueuePutCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getSS2GBSQueueTakeCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		fixture.ss2gbsQueueName = label;
		fixture.ss2gbsPutCount = label;
		fixture.ss2gbsTakeCount = label;

		fixture.setUpSubtreeSearch2GroupBySizeQueueListeners(mockPipeline);

		assertEquals(2, bindCallCount.get());
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#setUpSubtreeSearchPhaseListeners(cotelab.dupfilefinder2.pipeline.Pipeline)}.
	 */
	@Test
	public void testSetUpSubtreeSearchPhaseListeners() {
		final Pipeline mockPipeline = context.mock(Pipeline.class, "mockPipeline");
		final SimpleStringProperty mockSimpleStringProperty = context.mock(SimpleStringProperty.class,
				"mockSimpleStringProperty");
		@SuppressWarnings("unchecked")
		final ReadOnlyObjectProperty<State> mockReadOnlyObjectProperty = context.mock(ReadOnlyObjectProperty.class,
				"mockReadOnlyObjectProperty");
		final SimpleIntegerProperty mockSimpleIntegerProperty = context.mock(SimpleIntegerProperty.class,
				"mockSimpleIntegerProperty");
		Label label = new Label();
		final SimpleIntegerProperty bindSIPCallCount = new SimpleIntegerProperty(0);
		final SimpleIntegerProperty bindSOPCallCount = new SimpleIntegerProperty(0);
		FXMLController fixture = new FXMLController() {

			@Override
			protected void bind(ReadOnlyObjectProperty<State> roop, Label label) {
				bindSOPCallCount.set(1 + bindSOPCallCount.get());
			}

			@Override
			protected void bind(SimpleIntegerProperty sip, Label label) {
				bindSIPCallCount.set(1 + bindSIPCallCount.get());
			}

		};

		context.checking(new Expectations() {
			{
				oneOf(mockPipeline).getSSPPhaseName();
				will(returnValue(mockSimpleStringProperty));

				oneOf(mockSimpleStringProperty).addListener(with(any(InvalidationListener.class)));

				oneOf(mockPipeline).sspStateProperty();
				will(returnValue(mockReadOnlyObjectProperty));

				oneOf(mockPipeline).getSSPDirectoryCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getSSPFailedAccessCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getSSPOtherCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getSSPRegularFileCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getSSPSymbolicLinkCount();
				will(returnValue(mockSimpleIntegerProperty));

				oneOf(mockPipeline).getSSPUnreadableCount();
				will(returnValue(mockSimpleIntegerProperty));
			}
		});

		fixture.sspName = label;
		fixture.sspState = label;
		fixture.sspDirectoryCount = label;
		fixture.sspFailedAccessCount = label;
		fixture.sspOtherCount = label;
		fixture.sspRegularFileCount = label;
		fixture.sspSymbolicLinkCount = label;
		fixture.sspUnreadableCount = label;

		fixture.setUpSubtreeSearchPhaseListeners(mockPipeline);

		assertEquals(6, bindSIPCallCount.get());
		assertEquals(1, bindSOPCallCount.get());
	}

	/**
	 * Test method for {@link cotelab.dupfilefinder2.FXMLController#showResults()}.
	 */
	@Test
	public void testShowResults() {
		// don't know how to write a meaningful unit test of this method
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#startHeapTracker()}.
	 */
	@Test
	public void testStartHeapMonitor() {
		// don't know how to make a meaningful test of this method
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#startPhase(cotelab.dupfilefinder2.pipeline.Phase)}.
	 */
	@Test
	public void testStartPhase() {
		// don't know how to write a meaningful unit test of this method
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.FXMLController#updateInFXThread(javafx.scene.control.Label, javafx.concurrent.Worker.State)}.
	 */
	@Test
	public void testUpdateInFXThread() {
		// don't know how to make a meaningful test of this
	}

}
