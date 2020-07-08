package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * This class is a class that knows who to return as a service
 * provider to access the data subsystem.
 * 
 * @author antonija
 *
 */
public class DAOProvider {

	/**
	 * private dao 
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * getter for dao
	 * @return
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}