package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class serves as a bridge from listener and some other
 * ILocalizationProvider. Object of this class can connect and disconnect
 * ILocalizationProvider and private listener.
 * 
 * @author antonija
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * current language
	 */
	private String language;
	/**
	 * true if connected, false otherwise
	 */
	private boolean connected;
	/**
	 * ILocalizationProvider that is subject to listener
	 */
	private ILocalizationProvider parent;
	/**
	 * Listener to parent
	 */
	private ILocalizationListener listener;

	/**
	 * Public constructor sets language to input subject language and creates new
	 * ILocalizationListener
	 * 
	 * @param l subject
	 */
	public LocalizationProviderBridge(ILocalizationProvider l) {
		this.parent = l;
		language = parent.getCurrentLanguage();
		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	/**
	 * This method unregisters listener from parent
	 */
	public void connect() {
		if (connected == true) {
			return;
		}
		parent.addLocalizationListener(listener);
		connected = true;

	}

	/**
	 * This method registers listener to parent
	 */
	public void disconnect() {
		parent.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
