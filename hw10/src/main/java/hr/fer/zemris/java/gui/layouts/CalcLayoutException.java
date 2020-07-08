package hr.fer.zemris.java.gui.layouts;

/**
 * CalcLayoutException is Exception that extends RuntimeException.
 * Exception is created for CalcLayout created in this package
 * 
 * @author antonija
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 28035899789L;

	/**
	 * default constructor
	 */
	public CalcLayoutException() {

	}

	/**
	 * Constructor that excepts string as an input. Usually for explaining exception. 
	 * @param message
	 */
	public CalcLayoutException(String message) {

		super(message);

	}
}
