package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * 
 * This class stores the connection to the database in the ThreadLocal object.
 * ThreadLocal is actually a folder whose keys are thread identifiers that
 * operate over the map
 * 
 * @author antonija
 *
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * 
	 * Set a link to the current tree (or delete the log from the folder if it is an
	 * argument <code>null</code>).
	 * 
	 * @param con connection towards database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Get the connection that the current thread (user) can use.
	 * 
	 * @return connection towards database
	 * 
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}