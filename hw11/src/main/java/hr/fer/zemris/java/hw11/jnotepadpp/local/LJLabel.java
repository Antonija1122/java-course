package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.Action;
import javax.swing.JLabel;

public class LJLabel extends JLabel {

	String key;

	ILocalizationProvider lp;

	// first argument key second flp
	public LJLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		setText(lp.getString(key));
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		});

	}
}
