package hr.fer.zemris.java.p12.dao;

/**
 * This exception extends RuntimeException and is thrown if error occurs in
 * classes that implement DAO interface
 * 
 * @author antonija
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor
	 */
	public DAOException() {
	}

	/**
	 * Public constructor that enables to print message and cause to user and defines if stack is printable
	 * @param message error message
	 * @param cause cause of error
	 * @param enableSuppression
	 * @param writableStackTrace 
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Public constructor that enables to print message and cause to user
	 * @param message error message
	 * @param cause cause of error
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Public constructor that enables to print message to user
	 * @param message error message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Public constructor that enables to print cause of error to user
	 * @param cause error cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}