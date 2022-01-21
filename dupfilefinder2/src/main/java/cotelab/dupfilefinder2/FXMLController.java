package cotelab.dupfilefinder2;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ResourceBundle;

import cotelab.dupfilefinder2.pipeline.GroupByContentPhase;
import cotelab.dupfilefinder2.pipeline.GroupBySizePhase;
import cotelab.dupfilefinder2.pipeline.MatchingSubtreeIdentificationPhase;
import cotelab.dupfilefinder2.pipeline.Pipeline;
import cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase;
import cotelab.dupfilefinder2.pipeline.phase.Phase;
import cotelab.dupfilefinder2.pipeline.queueing.PipelineQueue;
import cotelab.dupfilefinder2.treeview.DecoratedFileTreeView;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import net.sf.cotelab.util.javafx.tree.FileIconFactory;
import net.sf.cotelab.util.javafx.tree.FileTreeItem;
import net.sf.cotelab.util.javafx.tree.FileTreeView;

/**
 * The GUI controller.
 */
public class FXMLController implements Initializable {
	/**
	 * The name of the resource used as Help>About dialog content.
	 */
	public static final String HELP_ABOUT_RESOURCE = "helpAbout.html";

	/**
	 * The URL of the web page used as Help>Usage content.
	 */
	public static final String HELP_USAGE_URL = "https://github.com/alantcote/dupfilefinder2/wiki/Usage";

	/**
	 * The ancestor set.
	 */
	protected HashSet<Path> ancestorSet = newPathHashSet();

	/**
	 * The popup browser launcher.
	 */
	protected PopupBrowserLauncher browserLauncher = null;

	/**
	 * The cancel button.
	 */
	@FXML
	protected Button cancelButton;

	/**
	 * The collection of duplicate file groups.
	 */
	protected ArrayList<Collection<Path>> dupCollections = null;

	/**
	 * The elapsed time display.
	 */
	@FXML
	protected Label elapsedTime;

	/**
	 * Tracker for elapsed time during a scan.
	 */
	protected ElapsedTimeTracker elapsedTimeTracker;

	/**
	 * The File>Close menu item.
	 */
	@FXML
	protected MenuItem fileCloseMenuItem;

	/**
	 * The {@link GroupByContentPhase} to {@link MatchingSubtreeIdentificationPhase}
	 * queue put count field.
	 */
	@FXML
	protected Label gbc2msiPutCount;

	/**
	 * The {@link GroupByContentPhase} to {@link MatchingSubtreeIdentificationPhase}
	 * queue name field.
	 */
	@FXML
	protected Label gbc2msiQueueName;

	/**
	 * The {@link GroupByContentPhase} to {@link MatchingSubtreeIdentificationPhase}
	 * queue take count field.
	 */
	@FXML
	protected Label gbc2msiTakeCount;

	/**
	 * The {@link GroupByContentPhase} bytes compared count field.
	 */
	@FXML
	protected Label gbcBytesCompared;

	/**
	 * The {@link GroupByContentPhase} files compared count field.
	 */
	@FXML
	protected Label gbcFilesCompared;

	/**
	 * The {@link GroupByContentPhase} name field.
	 */
	@FXML
	protected Label gbcName;

	/**
	 * The {@link GroupByContentPhase} state field.
	 */
	@FXML
	protected Label gbcState;

	/**
	 * The {@link GroupByContentPhase} unique files count field.
	 */
	@FXML
	protected Label gbcUniqueCount;

	/**
	 * The {@link GroupBySizePhase} to {@link GroupByContentPhase} queue put count
	 * field.
	 */
	@FXML
	protected Label gbs2gbcPutCount;

	/**
	 * The {@link GroupBySizePhase} to {@link GroupByContentPhase} queue name field.
	 */
	@FXML
	protected Label gbs2gbcQueueName;

	/**
	 * The {@link GroupBySizePhase} to {@link GroupByContentPhase} queue take count
	 * field.
	 */
	@FXML
	protected Label gbs2gbcTakeCount;

	/**
	 * The {@link GroupBySizePhase} files measured count field.
	 */
	@FXML
	protected Label gbsFilesMeasuredCount;

	/**
	 * The {@link GroupBySizePhase} name field.
	 */
	@FXML
	protected Label gbsName;

	/**
	 * The {@link GroupBySizePhase} file size count field.
	 */
	@FXML
	protected Label gbsSizeCount;

	/**
	 * The {@link GroupBySizePhase} state field.
	 */
	@FXML
	protected Label gbsState;

	/**
	 * The {@link GroupBySizePhase} unique files count field.
	 */
	@FXML
	protected Label gbsUniqueCount;

	/**
	 * The {@link GroupBySizePhase} unmeasurable files count field.
	 */
	@FXML
	protected Label gbsUnmeasurableCount;

	/**
	 * The heap message field.
	 */
	@FXML
	protected Label heapMessage;

	/**
	 * The heap progress bar.
	 */
	@FXML
	protected ProgressBar heapProgressBar;

	/**
	 * The Help>About menu item.
	 */
	@FXML
	protected MenuItem helpAboutMenuItem;

	/**
	 * The Help>Usage menu item.
	 */
	@FXML
	protected MenuItem helpUsageMenuItem;

	/**
	 * The instance of {@link HostServices}.
	 */
	protected HostServices hostServices = null;

	/**
	 * The pipeline input queue.
	 */
	protected PipelineQueue input = null;

	/**
	 * The {@link MatchingSubtreeIdentificationPhase} name field.
	 */
	@FXML
	protected Label msiName;

	/**
	 * The {@link MatchingSubtreeIdentificationPhase} path groups considered count
	 * field.
	 */
	@FXML
	protected Label msiPathGroupsConsidered;

	/**
	 * The {@link MatchingSubtreeIdentificationPhase} state field.
	 */
	@FXML
	protected Label msiState;

	/**
	 * The pipeline output queue.
	 */
	protected PipelineQueue output = null;

	/**
	 * The path to duplicate paths map.
	 */
	protected Hashtable<Path, Collection<Path>> pathToDupCollMap = new Hashtable<Path, Collection<Path>>();

	/**
	 * The pipeline.
	 */
	protected Pipeline pipeline;

	/**
	 * The pipeline input queue put count field.
	 */
	@FXML
	protected Label pipelineInputPutCount;

	/**
	 * The pipeline input queue name field.
	 */
	@FXML
	protected Label pipelineInputQueueName;

	/**
	 * The pipeline input queue take count field.
	 */
	@FXML
	protected Label pipelineInputTakeCount;

	/**
	 * The pipeline name field.
	 */
	@FXML
	protected Label pipelineName;

	/**
	 * The pipeline output queue put count field.
	 */
	@FXML
	protected Label pipelineOutputPutCount;

	/**
	 * The pipeline output queue name field.
	 */
	@FXML
	protected Label pipelineOutputQueueName;

	/**
	 * The pipeline output queue take count field.
	 */
	@FXML
	protected Label pipelineOutputTakeCount;

	/**
	 * The pipeline state field.
	 */
	@FXML
	protected Label pipelineState;

	/**
	 * The placeholder subtree selection tree view.
	 */
	@FXML
	protected TreeView<String> placeHolderTreeView;

	/**
	 * The placeholder results tree view.
	 */
	@FXML
	protected TreeView<File> resultsTreeView;

	/**
	 * The root pane.
	 */
	@FXML
	protected BorderPane rootPane;

	/**
	 * The {@link SubtreeSearchPhase} to {@link GroupBySizePhase} queue put count
	 * field.
	 */
	@FXML
	protected Label ss2gbsPutCount;

	/**
	 * The {@link SubtreeSearchPhase} to {@link GroupBySizePhase} queue name field.
	 */
	@FXML
	protected Label ss2gbsQueueName;

	/**
	 * The {@link SubtreeSearchPhase} to {@link GroupBySizePhase} queue take count
	 * field.
	 */
	@FXML
	protected Label ss2gbsTakeCount;

	/**
	 * The {@link SubtreeSearchPhase} directory count field.
	 */
	@FXML
	protected Label sspDirectoryCount;

	/**
	 * The {@link SubtreeSearchPhase} failed access count field.
	 */
	@FXML
	protected Label sspFailedAccessCount;

	/**
	 * The {@link SubtreeSearchPhase} name field.
	 */
	@FXML
	protected Label sspName;

	/**
	 * The {@link SubtreeSearchPhase} other count field.
	 */
	@FXML
	protected Label sspOtherCount;

	/**
	 * The {@link SubtreeSearchPhase} regular file count field.
	 */
	@FXML
	protected Label sspRegularFileCount;

	/**
	 * The {@link SubtreeSearchPhase} state field.
	 */
	@FXML
	protected Label sspState;

	/**
	 * The {@link SubtreeSearchPhase} symbolic link count field.
	 */
	@FXML
	protected Label sspSymbolicLinkCount;

	/**
	 * The {@link SubtreeSearchPhase} unreadable count field.
	 */
	@FXML
	protected Label sspUnreadableCount;

	/**
	 * The start button.
	 */
	@FXML
	protected Button startButton;

	/**
	 * The subtree selection tree view.
	 */
	protected FileTreeView subtreeSelectionTreeView;

	/**
	 * @return the resultsTreeView
	 */
	public TreeView<File> getResultsTreeView() {
		return resultsTreeView;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		subtreeSelectionTreeView = createFileTreeView();
		subtreeSelectionTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		rootPane.setLeft(subtreeSelectionTreeView);

		startHeapTracker();
		elapsedTimeTracker = newElapsedTimeTracker(elapsedTime);

		fileCloseMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		helpUsageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setupBrowserLauncher();

				browserLauncher.popup(HELP_USAGE_URL);
			}
		});

		helpAboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				URL url = getClass().getResource(HELP_ABOUT_RESOURCE);

				if (url != null) {
					setupBrowserLauncher();

					browserLauncher.openWebViewDialog(url.toExternalForm(), 300, 400);
				}
			}
		});

		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				elapsedTimeTracker.beginTracking();

				pipeline = setUpPipeline();
				startPhase(pipeline);

				cancelButton.setDisable(false);
				startButton.setDisable(true);
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pipeline.cancel(true);

				elapsedTimeTracker.stopTracking();
				cancelButton.setDisable(true);
				startButton.setDisable(false);
			}
		});
	}

	/**
	 * Refresh the result aids.
	 */
	public void refreshResultAids() {
		buildPathToDupCollMap();
		buildAncestorSet();
	}

	/**
	 * Add the ancestors of a given path to the ancestor set.
	 * 
	 * @param path the given path.
	 */
	protected void addAncestors(Path path) {
		Path parent = path.getParent();

		if (parent != null) {
			if (!ancestorSet.contains(parent)) {
				ancestorSet.add(parent);

				addAncestors(parent);
			}
		}
	}

	/**
	 * Gather up the pipeline results.
	 */
	protected void assembleResults() {
		collectResultsFromPipeline();
		refreshResultAids();
	}

	/**
	 * Assemble the results display.
	 */
	protected void assembleResultsTreeView() {
		FileIconFactory fileIconFactory = newFileIconFactory();

		resultsTreeView = newResultsTreeView(fileIconFactory);
		resultsTreeView.setShowRoot(false);
	}

	/**
	 * Set up a listener to keep a display field updated to reflect changes to a
	 * property.
	 * 
	 * @param roop  the property.
	 * @param label the display field.
	 */
	protected void bind(ReadOnlyObjectProperty<State> roop, Label label) {
//		roop.addListener(new StatePropToLabelBinder(label, this));
		label.textProperty().bind(roop.asString());
	}

	/**
	 * Set up a listener to keep a display field updated to reflect changes to a
	 * property.
	 * 
	 * @param sip   the property.
	 * @param label the display field.
	 */
	protected void bind(SimpleIntegerProperty sip, Label label) {
		label.textProperty().bind(sip.asString("%,d"));
	}

	/**
	 * Set up a listener to keep a display field updated to reflect changes to a
	 * property.
	 * 
	 * @param tsslp the property.
	 * @param label the display field.
	 */
	protected void bind(SimpleLongProperty tsslp, Label label) {
		label.textProperty().bind(tsslp.asString("%,d"));
	}

	/**
	 * Set up a listener to keep a display field updated to reflect changes to a
	 * property.
	 * 
	 * @param tsslp the property.
	 * @param label the display field.
	 */
	protected void bind(SimpleStringProperty tsslp, Label label) {
		label.textProperty().bind(tsslp);
	}

	/**
	 * Build up the ancestor set.
	 */
	protected void buildAncestorSet() {
		ancestorSet.clear();

		for (Collection<Path> coll : dupCollections) {
			for (Path path : coll) {
				addAncestors(path);
			}
		}
	}

	protected void buildPathToDupCollMap() {
		pathToDupCollMap.clear();

		for (Collection<Path> coll : dupCollections) {
			for (Path path : coll) {
				pathToDupCollMap.put(path, coll);
			}
		}
	}

	/**
	 * Collect the results from the pipeline.
	 */
	protected void collectResultsFromPipeline() {
		PipelineQueue pipelineOutputQueue = output;
		Collection<Path> dupColl = null;
		ArrayList<Collection<Path>> dupColls = new ArrayList<Collection<Path>>();

		while ((dupColl = pipelineOutputQueue.poll()) != null) {
			if (dupColl.size() > 1) {
				dupColls.add(dupColl);
			}
		}

		dupCollections = dupColls;
	}

	/**
	 * @return a new object.
	 */
	protected FileTreeView createFileTreeView() {
		TreeItem<File> rootFileTreeItem = newRootFileTreeItem();
		FileTreeView fileTreeView = new FileTreeView(rootFileTreeItem);

		fileTreeView.showRootProperty().set(false);

		return fileTreeView;
	}

	/**
	 * Construct a new object.
	 * 
	 * @param aLabel the display field.
	 * @return the new object.
	 */
	protected ElapsedTimeTracker newElapsedTimeTracker(Label aLabel) {
		return new ElapsedTimeTracker(aLabel);
	}

	/**
	 * @return a new object.
	 */
	protected FileIconFactory newFileIconFactory() {
		return new FileIconFactory();
	}

	/**
	 * @return a new object.
	 */
	protected HashSet<Path> newPathHashSet() {
		return new HashSet<Path>();
	}

	/**
	 * Get a new {@link DecoratedFileTreeView} to present search results.
	 * 
	 * @param fileIconFactory the {@link FileIconFactory} to use.
	 * @return a new object.
	 */
	protected DecoratedFileTreeView newResultsTreeView(FileIconFactory fileIconFactory) {
		return new DecoratedFileTreeView(newRootFileTreeItem(), fileIconFactory, ancestorSet, dupCollections,
				pathToDupCollMap, this);
	}

	/**
	 * Get a new {@link FileTreeItem} that represents the (synthetic) file system
	 * root.
	 * 
	 * @return a new object.
	 */
	protected FileTreeItem newRootFileTreeItem() {
		return new FileTreeItem(null);
	}

	/**
	 * Set up the browser launcher.
	 */
	protected void setupBrowserLauncher() {
		if (browserLauncher == null) {
			Object userObject = rootPane.getUserData();

			if (userObject instanceof HostServices) {
				hostServices = (HostServices) userObject;
			}

			browserLauncher = new PopupBrowserLauncher(hostServices);
		}
	}

	/**
	 * Bind queue properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpGroupByContent2MatchingSubtreeIdentificationQueueListeners(Pipeline line) {
		bind(line.getGBC2MSIQueueName(), gbc2msiQueueName);
		bind(line.getGBC2MSIQueuePutCount(), gbc2msiPutCount);
		bind(line.getGBC2MSIQueueTakeCount(), gbc2msiTakeCount);
	}

	/**
	 * Bind phase properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpGroupByContentPhaseListeners(Pipeline line) {
		bind(line.getGBCPhaseName(), gbcName);
		bind(line.gbcStateProperty(), gbcState);
		bind(line.getGBCUniqueCount(), gbcUniqueCount);
		bind(line.getGBCFilesComparedCount(), gbcFilesCompared);
		bind(line.getGBCBytesComparedCount(), gbcBytesCompared);
	}

	/**
	 * Bind queue properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpGroupBySize2GroupByContentQueueListeners(Pipeline line) {
		bind(line.getGBS2GBCQueueName(), gbs2gbcQueueName);
		bind(line.getGBS2GBCQueuePutCount(), gbs2gbcPutCount);
		bind(line.getGBS2GBCQueueTakeCount(), gbs2gbcTakeCount);
	}

	/**
	 * Bind phase properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpGroupBySizePhaseListeners(Pipeline line) {
		bind(line.getGBSPhaseName(), gbsName);
		bind(line.gbsStateProperty(), gbsState);
		bind(line.getGBSFilesMeasuredCount(), gbsFilesMeasuredCount);
		bind(line.getGBSSizeCount(), gbsSizeCount);
		bind(line.getGBSUniqueCount(), gbsUniqueCount);
		bind(line.getGBSUnmeasurableCount(), gbsUnmeasurableCount);
	}

	/**
	 * Bind phase properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpMatchingSubtreeIdentificationPhaseListeners(Pipeline line) {
		bind(line.getMSIPhaseName(), msiName);
		bind(line.msiStateProperty(), msiState);
		bind(line.getMSIPathGroupsConsideredProperty(), msiPathGroupsConsidered);
	}

	/**
	 * Set up a pipeline.
	 * 
	 * @return a new object.
	 */
	protected Pipeline setUpPipeline() {
		input = new PipelineQueue(Integer.MAX_VALUE, "Pipeline Input");
		output = new PipelineQueue(Integer.MAX_VALUE, "Pipeline Output");

		Pipeline result = new Pipeline("Pipeline", input, output);
		MultipleSelectionModel<TreeItem<File>> selModel = subtreeSelectionTreeView.getSelectionModel();
		ObservableList<TreeItem<File>> selectedItems = selModel.getSelectedItems();

		try {
			for (TreeItem<File> rootItem : selectedItems) {
				File rootFile = rootItem.getValue();
				Path rootPath = rootFile.toPath();
				LinkedList<Path> searchRoots = new LinkedList<Path>();

				searchRoots.add(rootPath);
				input.put(searchRoots);
			}

			input.put(new ArrayList<Path>()); // EOF convention
		} catch (InterruptedException e) {
			System.err.println("FXMLController.setUpPipeline(): caught " + e.getMessage());
			e.printStackTrace();
		}

		setUpPipelineListeners(result);

		return result;
	}

	/**
	 * Bind queue properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpPipelineInputQueueListeners(Pipeline line) {
		bind(line.getInputName(), pipelineInputQueueName);
		bind(line.getInputPutCount(), pipelineInputPutCount);
		bind(line.getInputTakeCount(), pipelineInputTakeCount);
	}

	/**
	 * Set up the pipeline listeners.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpPipelineListeners(Pipeline line) {
		bind(line.getPhaseName(), pipelineName);
		bind(line.stateProperty(), pipelineState);

		// handle end-of-scan condition
		line.stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				// take appropriate action when pipeline has finished
				if ((newValue == State.CANCELLED) || (newValue == State.FAILED) || (newValue == State.SUCCEEDED)) {
					cancelButton.setDisable(true);
					startButton.setDisable(false);

					elapsedTimeTracker.stopTracking();

					showResults();
				}
			}
		});

		setUpPipelineInputQueueListeners(line);
		setUpSubtreeSearchPhaseListeners(line);
		setUpSubtreeSearch2GroupBySizeQueueListeners(line);
		setUpGroupBySizePhaseListeners(line);
		setUpGroupBySize2GroupByContentQueueListeners(line);
		setUpGroupByContentPhaseListeners(line);
		setUpGroupByContent2MatchingSubtreeIdentificationQueueListeners(line);
		setUpMatchingSubtreeIdentificationPhaseListeners(line);
		setUpPipelineOutputQueueListeners(line);
	}

	/**
	 * Bind queue properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpPipelineOutputQueueListeners(Pipeline line) {
		bind(line.getOutputName(), pipelineOutputQueueName);
		bind(line.getOutputPutCount(), pipelineOutputPutCount);
		bind(line.getOutputTakeCount(), pipelineOutputTakeCount);
	}

	/**
	 * Bind queue properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpSubtreeSearch2GroupBySizeQueueListeners(Pipeline line) {
		bind(line.getSS2GBSQueueName(), ss2gbsQueueName);
		bind(line.getSS2GBSQueuePutCount(), ss2gbsPutCount);
		bind(line.getSS2GBSQueueTakeCount(), ss2gbsTakeCount);
	}

	/**
	 * Bind phase properties to display fields.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpSubtreeSearchPhaseListeners(Pipeline line) {
		bind(line.getSSPPhaseName(), sspName);
		bind(line.sspStateProperty(), sspState);
		bind(line.getSSPDirectoryCount(), sspDirectoryCount);
		bind(line.getSSPFailedAccessCount(), sspFailedAccessCount);
		bind(line.getSSPOtherCount(), sspOtherCount);
		bind(line.getSSPRegularFileCount(), sspRegularFileCount);
		bind(line.getSSPSymbolicLinkCount(), sspSymbolicLinkCount);
		bind(line.getSSPUnreadableCount(), sspUnreadableCount);
	}

	/**
	 * Display the pipeline results.
	 */
	protected void showResults() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				assembleResults();

				assembleResultsTreeView();
				rootPane.setRight(resultsTreeView);
				resultsTreeView.setVisible(true);
			}
		});
	}
	
	/**
	 * The heap tracking component.
	 */
	protected HeapTracker heapTracker;

	/**
	 * Start the heap tracker.
	 */
	protected void startHeapTracker() {
		heapTracker = new HeapTracker(heapProgressBar, heapMessage);
	}

	/**
	 * Start up a {@link Phase} in a {@link Thread}.
	 * 
	 * @param aPhase the phase.
	 */
	protected void startPhase(Phase aPhase) {
		Thread th = new Thread(aPhase);

		th.setDaemon(true);
		th.start();
	}
}
