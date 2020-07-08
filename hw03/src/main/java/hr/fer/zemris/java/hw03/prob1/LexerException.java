package hr.fer.zemris.java.hw03.prob1;

/**
 * LexerException is Exception that extends RuntimeException.
 * Exception is created for parser created in this package
 * 
 * @author antonija
 *
 */
public class LexerException extends RuntimeException {

    private static final long serialVersionUID = 2803456789L;
    
	/**
	 * default constructor
	 */
    public LexerException() {
    	
    }
    
	/**
	 * Constructor that excepts string as an input. Usually for explaining exception. 
	 * @param message
	 */
    public LexerException(String message) {
    	
    	super(message);
    	
    }
    
}