package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class implements all methods from ILocalizationProvider except
 * getString(String key): String method
 * 
 * @author antonija
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Collection of registered listeners
	 */
	List<ILocalizationListener> listeners;

	/**
	 * Constructor creates empty list of listeners
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<ILocalizationListener>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * This method informs all listeners about localization change
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}
