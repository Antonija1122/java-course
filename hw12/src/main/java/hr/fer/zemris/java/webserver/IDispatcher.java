package hr.fer.zemris.java.webserver;

/**
 * Public interface IDispatcher dispatches request next client worker
 * @author antonija
 *
 */
public interface IDispatcher {
	/**
	 * Method dispatches request to next worker
	 * @param urlPath
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}