/**
 * 
 */
package cotelab.dupfilefinder2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.web.WebView;
import net.sf.cotelab.jfxrunner.JavaFxJUnit4ClassRunner;

/**
 * Test case for {@link PopupBrowserLauncher}.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class PopupBrowserLauncherTest {
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
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#newDialog()}.
	 */
	@Test
	public void testNewDialog() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(fixture.newDialog() instanceof Dialog);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#newOKButtonType()}.
	 */
	@Test
	public void testNewOKButtonType() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(fixture.newOKButtonType() instanceof ButtonType);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#newWebView()}.
	 */
	@Test
	public void testNewWebView() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(fixture.newWebView() instanceof WebView);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#openWebViewDialog(java.lang.String)}.
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
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#openWebViewDialog(java.lang.String, double, double)}.
	 */
	@Test
	public void testOpenWebViewDialogStringDoubleDouble() {
		// most of the classes used in this method are final, so they can't be mocked.
		// it is pointless to mock Dialog, because the methods of interest are final.
		// ergo, it is not clear how to test this method.
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#popup(java.lang.String)}.
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
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#PopupBrowserLauncher()}.
	 */
	@Test
	public void testPopupBrowserLauncher() {
		// there is nothing to test
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.PopupBrowserLauncher#PopupBrowserLauncher(javafx.application.HostServices)}.
	 */
	@Test
	public void testPopupBrowserLauncherHostServices() {
		PopupBrowserLauncher fixture = new PopupBrowserLauncher();

		assertTrue(null == fixture.hostServices);
	}

}