/**
 * 
 */
package io.github.alantcote.dupfilefinder2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import de.saxsys.mvvmfx.testingutils.jfxrunner.TestInJfxThread;
import io.github.alantcote.dupfilefinder2.PopupBrowserLauncher;
import cotelab.junit4utils.TestCaseWithJMockAndByteBuddy;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.web.WebView;

/**
 * Test case for {@link PopupBrowserLauncher}.
 */
@RunWith(JfxRunner.class)
public class PopupBrowserLauncherTest extends TestCaseWithJMockAndByteBuddy {
	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#newDialog()}.
	 */
	@Test
	@TestInJfxThread
	public void testNewDialog() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(fixture.newDialog() instanceof Dialog);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#newOKButtonType()}.
	 */
	@Test
	public void testNewOKButtonType() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(fixture.newOKButtonType() instanceof ButtonType);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#newWebView()}.
	 */
	@Test
	@TestInJfxThread
	public void testNewWebView() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(fixture.newWebView() instanceof WebView);
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#openWebViewDialog(java.lang.String)}.
	 */
	@Test
	public void testOpenWebViewDialogString() {
		final SimpleIntegerProperty sip = new SimpleIntegerProperty(0);
		PopupBrowserLauncher fixture = new PopupBrowserLauncher() {

			@Override
			public void openWebViewDialog(String url, double height, double width) {
				assertEquals("url", url);
				assertTrue(600 == height);
				assertTrue(800 == width);

				sip.set(1 + sip.get());
			}

		};

		fixture.openWebViewDialog("url");

		assertTrue(1 == sip.get());
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#openWebViewDialog(java.lang.String, double, double)}.
	 */
	@Test
	public void testOpenWebViewDialogStringDoubleDouble() {
		// most of the classes used in this method are final, so they can't be mocked.
		// it is pointless to mock Dialog, because the methods of interest are final.
		// ergo, it is not clear how to test this method.
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#popup(java.lang.String)}.
	 */
	@Test
	public void testPopup() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher() {

			@Override
			public void openWebViewDialog(String url) {
				assertEquals("url", url);
			}

		};

		fixture.popup("url");
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#PopupBrowserLauncher()}.
	 */
	@Test
	public void testPopupBrowserLauncher() {
		// there is nothing to test
	}

	/**
	 * Test method for
	 * {@link io.github.alantcote.dupfilefinder2.PopupBrowserLauncher#PopupBrowserLauncher(javafx.application.HostServices)}.
	 */
	@Test
	public void testPopupBrowserLauncherHostServices() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(null == fixture.hostServices);
	}

}
