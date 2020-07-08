package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This interface represents provider for translation of certain words with
 * corresponding key.
 * 
 * @author antonija
 *
 */
public interface ILocalizationProvider {

	/**
	 * This method returns word with key value of input key from properties map for
	 * current language
	 * 
	 * @param key key of wanted string
	 * @return string
	 */
	String getString(String key);

	/**
	 * This method registers input listener to this subject
	 * 
	 * @param l added listener
	 */
	public void addLocalizationListener(ILocalizationListener l);

	/**
	 * This method unregisters input listener from this subject
	 * 
	 * @param l removed listener
	 */
	public void removeLocalizationListener(ILocalizationListener l);

	/**
	 * This method returns key string representation of current language for example
	 * "en" for English or "hr" for Croatian.
	 * 
	 * @return  key string representation of current language
	 */
	public String getCurrentLanguage();

}
