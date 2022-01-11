package cotelab.dupfilefinder2;

import javafx.application.HostServices;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A launcher that can pop up a browser.
 */
public class PopupBrowserLauncher {
	/**
	 * The {@link HostServices} object, if one has been provided.
	 */
	protected HostServices hostServices = null;

	/**
	 * Construct a new object.
	 */
	public PopupBrowserLauncher() {
	}

	/**
	 * Construct a new object.
	 * 
	 * @param theHostServices the {@link HostServices} object to be used.
	 */
	public PopupBrowserLauncher(HostServices theHostServices) {
		hostServices = theHostServices;
	}

	/**
	 * Open a non-modal dialog containing a display of the web page addressed by the
	 * URL.
	 * 
	 * @param url the URL.
	 */
	public void openWebViewDialog(String url) {
		openWebViewDialog(url, 600, 800);
	}

	/**
	 * Open a non-modal dialog containing a display of the web page addressed by the
	 * URL.
	 * 
	 * @param url    the URL.
	 * @param height height for the content.
	 * @param width  width for the content.
	 */
	public void openWebViewDialog(String url, double height, double width) {
		WebView webView = newWebView();
		WebEngine webEngine = webView.getEngine();
		Dialog<String> dialog = newDialog();
		DialogPane dialogPane = dialog.getDialogPane();
		ButtonType buttonType = newOKButtonType();

		dialogPane.setContent(webView);
		dialogPane.getButtonTypes().add(buttonType);

		webEngine.load(url);

		webView.setPrefHeight(height);
		webView.setPrefWidth(width);

		dialog.show();
	}

	/**
	 * Try to pop up a browser that is displaying the web page addressed by the URL.
	 * 
	 * If an instance of {@link HostServices} has been provided, then it is asked to
	 * open a native browser on the URL. Otherwise, an instance of {@link Dialog} is
	 * popped up, wrapped around an instance of {@link WebView} which is asked to
	 * open and display the URL.
	 * 
	 * Unfortunately, there is no way to know whether either mechanism has been
	 * successful. One must just hope.
	 * 
	 * @param url the URL.
	 */
	public void popup(String url) {
		if ((hostServices != null) && (hostServices instanceof HostServices)) {
			hostServices.showDocument(url);
		} else {
			openWebViewDialog(url);
		}

	}

	/**
	 * @return a new object.
	 */
	protected Dialog<String> newDialog() {
		return new Dialog<String>();
	}

	/**
	 * @return a new object.
	 */
	protected ButtonType newOKButtonType() {
		return new ButtonType("OK", ButtonData.OK_DONE);
	}

	/**
	 * @return a new object.
	 */
	protected WebView newWebView() {
		return new WebView();
	}
}
