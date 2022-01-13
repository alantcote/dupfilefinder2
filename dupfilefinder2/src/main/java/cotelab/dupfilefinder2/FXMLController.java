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
import cotelab.dupfilefinder2.pipeline.Phase;
import cotelab.dupfilefinder2.pipeline.Pipeline;
import cotelab.dupfilefinder2.pipeline.PipelineQueue;
import cotelab.dupfilefinder2.pipeline.SubtreeSearchPhase;
import cotelab.dupfilefinder2.treeview.DecoratedFileTreeView;
import javafx.application.HostServices;
import javafx.application.Platform;
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

/* TODO This class is way too big; it needs some sort of refactoring to make it more
 * understandable and more testable.
 */
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
	 * The starting timestamp.
	 */
	protected long startStamp = 0;

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
//		System.out.println("FXMLController.initialize(): switching to proper TreeView");

		subtreeSelectionTreeView = createFileTreeView();
		subtreeSelectionTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		rootPane.setLeft(subtreeSelectionTreeView);

		startHeapMonitor();

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

//		System.out.println("FXMLController.initialize(): calling startButton.setOnAction()");

		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				System.out.println("FXMLController.initialize(): start button handler calling setUpPipeline()");

				startStamp = System.currentTimeMillis();

				pipeline = setUpPipeline();

//				System.out.println("FXMLController.initialize(): start button handler starting pipeline");

				startPhase(pipeline);

//				System.out.println("FXMLController.initialize(): start button handler enabling cancel button");

				cancelButton.setDisable(false);

//				System.out.println("FXMLController.initialize(): start button handler disabling start button");

				startButton.setDisable(true);

//				System.out.println("FXMLController.initialize(): start button handler method complete");
			}

		});

//		System.out.println("FXMLController.initialize(): calling cancelButton.setOnAction()");

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				System.out.println("FXMLController.initialize(): cancel button handler cancelling pipeline");

				pipeline.cancel(true);

//				System.out.println("FXMLController.initialize(): cancel button handler disabling cancel button");

				cancelButton.setDisable(true);

//				System.out.println("FXMLController.initialize(): cancel button handler enabling start button");

				startButton.setDisable(false);

//				System.out.println("FXMLController.initialize(): cancel button handler method complete");

			}

		});

//		System.out.println("FXMLController.initialize(): method complete");
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
		FileIconFactory fileIconFactory = new FileIconFactory();

		resultsTreeView = new DecoratedFileTreeView(new FileTreeItem(null), fileIconFactory, ancestorSet,
				dupCollections, pathToDupCollMap, this);
		resultsTreeView.setShowRoot(false);
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
		TreeItem<File> rootFileTreeItem = new FileTreeItem(null);
		FileTreeView fileTreeView = new FileTreeView(rootFileTreeItem);

		fileTreeView.showRootProperty().set(false);

		return fileTreeView;
	}

	/**
	 * Format elapsed time for display.
	 * 
	 * @param elapsedMillis the elapsed time, in milliseconds.
	 * @return
	 */
	protected String formatElapsed(long elapsedMillis) {
		long fraction = elapsedMillis % 1000;
		long remnant = elapsedMillis / 1000;
		long seconds = remnant % 60;

		remnant /= 60;

		long minutes = remnant % 60;
		long hours = remnant / 60;
		String fractionString = "00" + Long.toString(fraction);
		String secondsString = Long.toString(seconds);
		String minutesString = Long.toString(minutes);
		String hoursString = Long.toString(hours);

		fractionString = fractionString.substring(fractionString.length() - 3);

		String result = hoursString + "h ";

		result += minutesString + "m ";
		result += secondsString + ".";
		result += fractionString + "s";

		return result;
	}

	/**
	 * @return a new object.
	 */
	protected HashSet<Path> newPathHashSet() {
		return new HashSet<Path>();
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
	 * @param line the pipeline.
	 */
	protected void setUpGroupByContent2MatchingSubtreeIdentificationQueueListeners(Pipeline line) {
		gbc2msiQueueName.setText(line.getGBC2MSIQueueName().get());

		line.getGBC2MSIQueuePutCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbc2msiPutCount, newValue);
			}

		});

		line.getGBC2MSIQueueTakeCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbc2msiTakeCount, newValue);
			}

		});
	}

	/**
	 * @param line the pipeline.
	 */
	protected void setUpGroupByContentPhaseListeners(Pipeline line) {
		gbcName.setText(line.getGBCPhaseName().get());

		line.getGBCUniqueCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbcUniqueCount, newValue);
			}

		});

		line.getGBCBytesComparedCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbcBytesCompared, newValue);
			}

		});

		line.getGBCFilesComparedCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbcFilesCompared, newValue);
			}

		});

		gbsState.setText(line.gbsStateProperty().get().toString());
		line.gbcStateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				updateInFXThread(gbcState, newValue);
			}

		});
	}

	/**
	 * @param line the pipeline.
	 */
	protected void setUpGroupBySize2GroupByContentQueueListeners(Pipeline line) {
		gbs2gbcQueueName.setText(line.getGBS2GBCQueueName().get());

		line.getGBS2GBCQueuePutCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbs2gbcPutCount, newValue);
			}

		});

		line.getGBS2GBCQueueTakeCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbs2gbcTakeCount, newValue);
			}

		});
	}

	/**
	 * @param line the pipeline.
	 */
	protected void setUpGroupBySizePhaseListeners(Pipeline line) {
		gbsName.setText(line.getGBSPhaseName().get());

		line.getGBSFilesMeasuredCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbsFilesMeasuredCount, newValue);
			}

		});

		line.getGBSSizeCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbsSizeCount, newValue);
			}

		});

		gbsState.setText(line.gbsStateProperty().get().toString());
		line.gbsStateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				updateInFXThread(gbsState, newValue);
			}

		});

		line.getGBSUniqueCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbsUniqueCount, newValue);
			}

		});

		line.getGBSUnmeasurableCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(gbsUnmeasurableCount, newValue);
			}

		});
	}

	/**
	 * @param line the pipeline.
	 */
	protected void setUpMatchingSubtreeIdentificationPhaseListeners(Pipeline line) {
		msiName.setText(line.getMSIPhaseName().get());

		line.getMSIPathGroupsConsideredProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(msiPathGroupsConsidered, newValue);
			}

		});

		msiState.setText(line.msiStateProperty().get().toString());
		line.msiStateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				updateInFXThread(msiState, newValue);
			}

		});
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
//		LinkedList<Path> searchRoots = new LinkedList<Path>();
		MultipleSelectionModel<TreeItem<File>> selModel = subtreeSelectionTreeView.getSelectionModel();
		ObservableList<TreeItem<File>> selectedItems = selModel.getSelectedItems();

		try {
			for (TreeItem<File> rootItem : selectedItems) {
				File rootFile = rootItem.getValue();
//				Path rootPath = Paths.get(rootFile.getAbsolutePath());
				Path rootPath = rootFile.toPath();
				LinkedList<Path> searchRoots = new LinkedList<Path>();

				searchRoots.add(rootPath);
				input.put(searchRoots);

//				System.out.println("FXMLController.setupPhase(): root = " + root);
			}

//			input.put(searchRoots);
			input.put(new ArrayList<Path>()); // EOF convention
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		setUpPipelineListeners(result);

		return result;
	}

	/**
	 * @param line the pipeline.
	 */
	protected void setUpPipelineInputQueueListeners(Pipeline line) {
		pipelineInputQueueName.setText(line.getInputName().get());

		pipelineInputPutCount.setText(Integer.toString(line.getInputPutCount().get()));
		line.getInputPutCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(pipelineInputPutCount, newValue);
			}

		});

		line.getInputTakeCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(pipelineInputTakeCount, newValue);
			}

		});
	}

	/**
	 * Set up the pipeline listeners.
	 * 
	 * @param line the pipeline.
	 */
	protected void setUpPipelineListeners(Pipeline line) {
		pipelineName.setText(line.getPhaseName().get());

		pipelineState.setText(line.stateProperty().get().toString());
		line.stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				updateInFXThread(pipelineState, newValue);

				// take appropriate action when pipeline has finished
				if ((newValue == State.CANCELLED) || (newValue == State.FAILED) || (newValue == State.SUCCEEDED)) {
					cancelButton.setDisable(true);
					startButton.setDisable(false);

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
	 * @param line the pipeline.
	 */
	protected void setUpPipelineOutputQueueListeners(Pipeline line) {
		pipelineOutputQueueName.setText(line.getOutputName().get());

		line.getOutputPutCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(pipelineOutputPutCount, newValue);
			}

		});

		line.getOutputTakeCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(pipelineOutputTakeCount, newValue);
			}

		});
	}

	/**
	 * @param line the pipeline.
	 */
	protected void setUpSubtreeSearch2GroupBySizeQueueListeners(Pipeline line) {
		ss2gbsQueueName.setText(line.getSS2GBSQueueName().get());

		line.getSS2GBSQueuePutCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(ss2gbsPutCount, newValue);
			}

		});

		line.getSS2GBSQueueTakeCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(ss2gbsTakeCount, newValue);
			}

		});
	}

	/**
	 * @param line the pipeline.
	 */
	protected void setUpSubtreeSearchPhaseListeners(Pipeline line) {
		sspName.setText(line.getSSPPhaseName().get());

		line.getSSPDirectoryCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(sspDirectoryCount, newValue);
			}

		});

		line.getSSPFailedAccessCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(sspFailedAccessCount, newValue);
			}

		});

		line.getSSPOtherCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(sspOtherCount, newValue);
			}

		});

		line.getSSPRegularFileCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(sspRegularFileCount, newValue);
			}

		});

		sspState.setText(line.sspStateProperty().get().toString());
		line.sspStateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				updateInFXThread(sspState, newValue);
			}

		});

		line.getSSPSymbolicLinkCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(sspSymbolicLinkCount, newValue);
			}

		});

		line.getSSPUnreadableCount().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateInFXThread(sspUnreadableCount, newValue);
			}

		});
	}

	/**
	 * Display the pipeline results.
	 */
	protected void showResults() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
//				System.out.println("FXMLController.showResults(): calling assembleResults()");

				assembleResults();

//				System.out.println("FXMLController.showResults(): calling assembleResultsTreeView()");

				assembleResultsTreeView();

//				System.out.println("FXMLController.showResults(): calling rootPane.setRight()");

				rootPane.setRight(resultsTreeView);
				resultsTreeView.setVisible(true);

//				System.out.println("FXMLController.showResults(): method completed.");
			}

		});
	}

	/**
	 * Start the heap monitor.
	 */
	protected void startHeapMonitor() {
		Thread t = new Thread(new HeapMonitor(heapProgressBar, heapMessage));

		t.setDaemon(true);
		t.start();
	}

	/**
	 * Start up a {@link Phase} in a {@link Thread}.
	 * 
	 * @param aPhase
	 */
	protected void startPhase(Phase aPhase) {
		Thread th = new Thread(aPhase);

		th.setDaemon(true);
		th.start();
	}

	/**
	 * Update the elapsed time field.
	 */
	protected void updateElapsedTime() {
		long now = System.currentTimeMillis();
		long elapsedMillis = now - startStamp;
		String elapsedString = formatElapsed(elapsedMillis);

		elapsedTime.setText(elapsedString);
	}

	/**
	 * Update the text of a {@link Label} with a string value of a {@link Number}.
	 * 
	 * @param field    the Label.
	 * @param newValue the Number.
	 */
	protected void updateInFXThread(Label field, Number newValue) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				field.setText(newValue.toString());

				updateElapsedTime();
			}

		});
	}

	/**
	 * Update the text of a {@link Label} with a string value of a {@link State}.
	 * 
	 * @param field    the Label.
	 * @param newValue the State.
	 */
	protected void updateInFXThread(Label field, State newValue) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				field.setText(newValue.toString());

				updateElapsedTime();
			}

		});
	}
}
