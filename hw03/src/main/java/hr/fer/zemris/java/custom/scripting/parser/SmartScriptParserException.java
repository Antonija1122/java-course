package hr.fer.zemris.java.custom.scripting.parser;


/**
 * SmartScriptParserException is Exception that extends RuntimeException.
 * Exception is created for parser created in this package
 * 
 * @author antonija
 *
 */
public class SmartScriptParserException extends RuntimeException {
	
    private static final long serialVersionUID = 28334503456789L;
    
	/**
	 * default constructor
	 */
    public SmartScriptParserException() {
    	
    }
    
	/**
	 * Constructor that excepts string as an input. Usually for explaining exception. 
	 * @param message
	 */
    public SmartScriptParserException(String message) {
    	
    	super(message);
    	
    }

}
