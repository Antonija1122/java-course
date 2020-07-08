package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * This worker changes color for clients background.
 * @author antonija
 *
 */
public class BgColorWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void processRequest(RequestContext context) {

		String colorString = context.getParameter("bgcolor");

		if (colorString != null && isValidColor(colorString)) {
			context.setPersistentParameter("bgcolor", colorString);
			context.setTemporaryParameter("messagge", "Color is updated");

		} else {
			context.setTemporaryParameter("messagge", "Color is not updated");
		}

		try {
			context.getDispatcher().dispatchRequest("/private/pages/bgcolor.smscr");
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}


	private boolean isValidColor(String colorString) {
		Pattern pattern = Pattern.compile("^([A-Fa-f0-9]{6})$");
		Matcher matcher = pattern.matcher(colorString);
		return matcher.matches();
	}
}
