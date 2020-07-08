package hr.fer.zemris.java.webserver;

/**
 * Interface for WebWorker for SmartScriptServer
 * @author antonija
 *
 */
public interface IWebWorker {
	
	/**
	 * Method processes clients request retrieving informations from input RequestContext
	 * @param context input request 
	 * @throws Exception 
	 */
	public void processRequest(RequestContext context) throws Exception;
	
}
