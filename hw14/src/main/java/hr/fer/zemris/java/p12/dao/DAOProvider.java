package hr.fer.zemris.java.p12.dao;

import java.util.ResourceBundle;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * This class is a singleton class that knows who to return as a service
 * provider to access the data subsystem.
 * 
 * @author antonija
 *
 */
public class DAOProvider {

	/**
	 * private and only instance of database provider
	 */
	private static DAO dao = new SQLDAO();

	static {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("sqlDao");
			dao = (DAO) Class.forName(rb.getString("sqlDao")).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DAO getter .
	 * 
	 * @return an object that encapsulates access to a data persistence layer.
	 */
	public static DAO getDao() {
		return dao;
	}

}