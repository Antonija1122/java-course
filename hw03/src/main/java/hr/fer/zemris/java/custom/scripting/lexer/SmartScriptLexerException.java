package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * SmartScriptLexerException is Exception that extends RuntimeException.
 * Exception is created for parser created in this package
 * 
 * @author antonija
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	private static final long serialVersionUID = 28034566456789L;

	/**
	 * default constructor
	 */
	public SmartScriptLexerException() {

	}

	/**
	 * Constructor that excepts string as an input. Usually for explaining exception. 
	 * @param message
	 */
	public SmartScriptLexerException(String message) {

		super(message);

	}
}
