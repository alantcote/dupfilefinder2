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

import cotelab.dupfilefinder2.pipeline.Phase;
import cotelab.dupfilefinder2.pipeline.Pipeline;
import cotelab.dupfilefinder2.pipeline.PipelineQueue;
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

public class FXMLController implements Initializable {
	public class HeapMonitor implements Runnable {
		protected static final long DELAY_MILLIS = 500;
		protected static final long GIG = 1024 * 1024 * 1024;
		protected static final long KILO = 1024;
		protected static final long MEG = 1024 * 1024;

		protected Runtime runtime = Runtime.getRuntime();

		@Override
		public void run() {
			while (true) {
				try {
					while (true) {
						Thread.sleep(DELAY_MILLIS);

						updateHeapProgressBar();
					}
				} catch (InterruptedException e) {
//					e.printStackTrace();
				}
			}
		}

		protected String conciseFormat(long val) {
			String result = "";

			if (val >= GIG) {
				result = Long.toString(val / GIG) + "." + Long.toString((val % GIG) / MEG) + "G";
			} else if (val >= MEG) {
				result = Long.toString(val / MEG) + "." + Long.toString((val % MEG) / KILO) + "M";
			} else if (val >= KILO) {
				result = Long.toString(val / KILO) + "." + Long.toString(val % KILO) + "K";
			} else {
				result = Long.toString(val) + "B";
			}

			return result;
		}

		protected void updateHeapProgressBar() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					long totalHeap = runtime.totalMemory();
					long freeHeap = runtime.freeMemory();
					long usedHeapBytes = totalHeap - freeHeap;
					double progress = ((double) usedHeapBytes) / totalHeap;
					String usedString = conciseFormat(usedHeapBytes);
					String totalString = conciseFormat(totalHeap);
					String mssg = usedString + " / " + totalString + " heap used.";

					heapProgressBar.setProgress(progress);
					heapMessage.setText(mssg);
				}
			});
		}

	}
	
	public static final String HELP_ABOUT_RESOURCE = "helpAbout.html";
	
	public static final String HELP_USAGE_URL =
			"https://github.com/alantcote/dupfilefinder2/wiki/Usage";

	protected HashSet<Path> ancestorSet = new HashSet<Path>();

	protected PopupBrowserLauncher browserLauncher = null;

	@FXML
	protected Button cancelButton;

	protected ArrayList<Collection<Path>> dupCollections = null;

	@FXML
	protected Label elapsedTime;

	@FXML
	protected MenuItem fileCloseMenuItem;

	protected FileTreeView fileTreeView;

	@FXML
	protected Label gbc2msiPutCount;

	@FXML
	protected Label gbc2msiQueueName;

	@FXML
	protected Label gbc2msiTakeCount;

	@FXML
	protected Label gbcBytesCompared;

	@FXML
	protected Label gbcFilesCompared;

	@FXML
	protected Label gbcName;

	@FXML
	protected Label gbcState;

	@FXML
	protected Label gbcUniqueCount;

	@FXML
	protected Label gbs2gbcPutCount;

	@FXML
	protected Label gbs2gbcQueueName;

	@FXML
	protected Label gbs2gbcTakeCount;

	@FXML
	protected Label gbsFilesMeasuredCount;

	@FXML
	protected Label gbsName;

	@FXML
	protected Label gbsSizeCount;

	@FXML
	protected Label gbsState;

	@FXML
	protected Label gbsUniqueCount;

	@FXML
	protected Label gbsUnmeasurableCount;

	@FXML
	protected Label heapMessage;

	@FXML
	protected ProgressBar heapProgressBar;

	@FXML
	protected MenuItem helpAboutMenuItem;

	@FXML
	protected MenuItem helpUsageMenuItem;

	protected HostServices hostServices = null;

	protected PipelineQueue input = null;

	@FXML
	protected Label msiName;

	@FXML
	protected Label msiPathGroupsConsidered;

	@FXML
	protected Label msiState;

	protected PipelineQueue output = null;

	protected Hashtable<Path, Collection<Path>> pathToDupCollMap = new Hashtable<Path, Collection<Path>>();

	protected Pipeline pipeline;

	@FXML
	protected Label pipelineInputPutCount;

	@FXML
	protected Label pipelineInputQueueName;

	@FXML
	protected Label pipelineInputTakeCount;

	@FXML
	protected Label pipelineName;

	@FXML
	protected Label pipelineOutputPutCount;

	@FXML
	protected Label pipelineOutputQueueName;

	@FXML
	protected Label pipelineOutputTakeCount;

	@FXML
	protected Label pipelineState;

	@FXML
	protected TreeView<String> placeHolderTreeView;

	@FXML
	protected TreeView<File> resultsTreeView;

	@FXML
	protected BorderPane rootPane;

	@FXML
	protected Label ss2gbsPutCount;

	@FXML
	protected Label ss2gbsQueueName;

	@FXML
	protected Label ss2gbsTakeCount;

	@FXML
	protected Label sspDirectoryCount;

	@FXML
	protected Label sspFailedAccessCount;

	@FXML
	protected Label sspName;

	@FXML
	protected Label sspOtherCount;

	@FXML
	protected Label sspRegularFileCount;

	@FXML
	protected Label sspState;

	@FXML
	protected Label sspSymbolicLinkCount;

	@FXML
	protected Label sspUnreadableCount;

	@FXML
	protected Button startButton;

	protected long startStamp = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("FXMLController.initialize(): switching to proper TreeView");

		fileTreeView = createFileTreeView();
		fileTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		rootPane.setLeft(fileTreeView);

		Thread t = new Thread(new HeapMonitor());

		t.setDaemon(true);
		t.start();
		
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

		System.out.println("FXMLController.initialize(): calling startButton.setOnAction()");

		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("FXMLController.initialize(): start button handler calling setUpPipeline()");

				startStamp = System.currentTimeMillis();

				pipeline = setUpPipeline();

				System.out.println("FXMLController.initialize(): start button handler starting pipeline");

				startPhase(pipeline);

				System.out.println("FXMLController.initialize(): start button handler enabling cancel button");

				cancelButton.setDisable(false);

				System.out.println("FXMLController.initialize(): start button handler disabling start button");

				startButton.setDisable(true);

				System.out.println("FXMLController.initialize(): start button handler method complete");
			}

		});

		System.out.println("FXMLController.initialize(): calling cancelButton.setOnAction()");

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("FXMLController.initialize(): cancel button handler cancelling pipeline");

				pipeline.cancel(true);

				System.out.println("FXMLController.initialize(): cancel button handler disabling cancel button");

				cancelButton.setDisable(true);

				System.out.println("FXMLController.initialize(): cancel button handler enabling start button");

				startButton.setDisable(false);

				System.out.println("FXMLController.initialize(): cancel button handler method complete");

			}

		});

		System.out.println("FXMLController.initialize(): method complete");
	}

	public void refreshResultAids() {
		buildPathToDupCollMap();
		buildAncestorSet();
	}

	protected void addAncestors(Path path) {
		Path parent = path.getParent();

		if (parent != null) {
			if (!ancestorSet.contains(parent)) {
				ancestorSet.add(parent);

				addAncestors(parent);
			}
		}
	}

	protected void assembleResults() {
		collectResultsFromPipeline();
		refreshResultAids();
	}

	protected void assembleResultsTreeView() {
		FileIconFactory fileIconFactory = new FileIconFactory();

		resultsTreeView = new DecoratedFileTreeView(new FileTreeItem(null), fileIconFactory, ancestorSet,
				dupCollections, pathToDupCollMap, this);
		resultsTreeView.setShowRoot(false);
	}

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

//    protected FileTreeView subtreeSelectionTreeView = new FileTreeView(null);

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

	protected FileTreeView createFileTreeView() {
		TreeItem<File> rootFileTreeItem = new FileTreeItem(null);
		FileTreeView fileTreeView = new FileTreeView(rootFileTreeItem);

		fileTreeView.showRootProperty().set(false);

		return fileTreeView;
	}

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

	protected void setupBrowserLauncher() {
		if (browserLauncher == null) {
			Object userObject = rootPane.getUserData();

			if (userObject instanceof HostServices) {
				hostServices = (HostServices) userObject;
			}
			
			browserLauncher = new PopupBrowserLauncher(hostServices);
		}
	}

	protected Pipeline setUpPipeline() {
		input = new PipelineQueue(Integer.MAX_VALUE, "Pipeline Input");
		output = new PipelineQueue(Integer.MAX_VALUE, "Pipeline Output");

		Pipeline result = new Pipeline("Pipeline", input, output);
//		LinkedList<Path> searchRoots = new LinkedList<Path>();
		MultipleSelectionModel<TreeItem<File>> selModel = fileTreeView.getSelectionModel();
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

	protected void setUpPipelineListeners(Pipeline line) {

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

		pipelineName.setText(line.getPhaseName().get());

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

	protected void showResults() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("FXMLController.showResults(): calling assembleResults()");

				assembleResults();

				System.out.println("FXMLController.showResults(): calling assembleResultsTreeView()");

				assembleResultsTreeView();

				System.out.println("FXMLController.showResults(): calling rootPane.setRight()");

				rootPane.setRight(resultsTreeView);
				resultsTreeView.setVisible(true);

				System.out.println("FXMLController.showResults(): method completed.");
			}

		});
	}

	protected void startPhase(Phase aPhase) {
		Thread th = new Thread(aPhase);

		th.setDaemon(true);
		th.start();
	}

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
