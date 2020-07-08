package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This interface is listener to change of localization.
 * 
 * @author antonija
 *
 */
public interface ILocalizationListener {

	/**
	 * Method performs action for every change of localization of subject this
	 * listener is registered to
	 */
	void localizationChanged();

}
