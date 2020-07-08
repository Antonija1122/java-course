package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * This class extends AbstractLocalizationProvider. This class represents
 * translator. This is a singleton class. Constructor is private and there is
 * only one static referense to this object which is available through method
 * getInstance().
 * 
 * @author antonija
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * current language
	 */
	private String language;

	/**
	 * Private instance of ResourceBundle for localization
	 */
	private ResourceBundle bundle;

	/**
	 * static reference to only object of this class
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
	}

	/**
	 * Getter method for instance
	 * @return instance
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Getter method for bundle 
	 * @return bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Setter method for language 
	 * @param language input language
	 */
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(this.language));
		fire();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return language;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
