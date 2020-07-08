package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This worker implements home page for user. After color is changer home page
 * is called again.
 * 
 * @author antonija
 *
 */
public class Home implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void processRequest(RequestContext context) {

		try {

			if (context.getPersistentParameter("bgcolor") != null) {
				context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));

			} else {
				context.setTemporaryParameter("background", "7F7F7F");
			}

			context.getDispatcher().dispatchRequest("/private/pages/home.smscr");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
