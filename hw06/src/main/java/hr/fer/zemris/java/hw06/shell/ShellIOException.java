package hr.fer.zemris.java.hw06.shell;

/**
 * Exception made for shell program.
 * 
 * @author antonija
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 2404280345L;

	/**
	 * Defalut constructor
	 */
	public ShellIOException() {
	}

	/**
	 * Constructor makes it possible to send String message if user tries to pop an
	 * element from empty stack.
	 * 
	 * @param message
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
