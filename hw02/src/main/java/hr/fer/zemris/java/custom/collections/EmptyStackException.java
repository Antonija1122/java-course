package hr.fer.zemris.java.custom.collections;

/**
 * Exception made for empty stack error. If user tries to pop one element from
 * empty stack this exception is thrown.
 * 
 * @author antonija
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 24042803L;

	/**
	 * Defalut constructor
	 */
	public EmptyStackException() {
	}

	/**
	 * Constructor makes it possible to send String message if user tries to pop an
	 * element from empty stack.
	 * 
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
