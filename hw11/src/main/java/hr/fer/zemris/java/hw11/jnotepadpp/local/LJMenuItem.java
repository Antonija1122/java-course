package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenuItem;

public class LJMenuItem extends JMenuItem {

	String key;

	ILocalizationProvider lp;

	// first argument key second flp
	public LJMenuItem(String key, ILocalizationProvider lp) {
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
