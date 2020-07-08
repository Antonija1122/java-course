package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {
	
	String key;
	
	ILocalizationProvider lp;

	//first argument key second flp
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key=key;
		this.lp=lp;
		putValue(NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "_short_description"));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
				putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "_short_description"));
			}
		});
		
		

	}



}
