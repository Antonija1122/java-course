package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Exception made for empty stack.
 * 
 * @author antonija
 */

public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 357985774L;

	/**
	 * Defalut constructor
	 */
	public EmptyStackException() {
	}

	/**
	 * Constructor with message for user. 
	 * 
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
