package hr.fer.zemris.java.tecaj_13.dao;
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
}