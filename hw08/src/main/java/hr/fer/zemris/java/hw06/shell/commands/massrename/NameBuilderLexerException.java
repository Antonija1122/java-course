package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * This class represents exception for NameBuilderLexer
 * @author antonija
 *
 */
public class NameBuilderLexerException extends RuntimeException{
	
	private static final long serialVersionUID = 28034564664645L;

	/**
	 * default constructor
	 */
	public NameBuilderLexerException() {

	}

	/**
	 * Constructor that excepts string as an input. Usually for explaining exception. 
	 * @param message
	 */
	public NameBuilderLexerException(String message) {

		super(message);

	}

}
