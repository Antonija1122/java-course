package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * This class extends LocalizationProviderBridge and represents
 * LocalizationProviderBridge for a JFrame, this object on dispose of JFrame
 * disconnects listener from its parent.
 * 
 * @author antonija
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Public constructor adds WindowAdapter for tracking if user is closing input
	 * JFrame
	 * 
	 * @param l ILocalizationProvider
	 * @param frame JFrame
	 */
	public FormLocalizationProvider(ILocalizationProvider l, JFrame frame) {
		super(l);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
			}
		});

	}

}
