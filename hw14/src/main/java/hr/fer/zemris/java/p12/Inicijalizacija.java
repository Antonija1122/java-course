package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * This listener initializes this webapp. In method contextInitialized()
 * database setting are loaded from configure file. If there is no data in input
 * database (path properties in dbsettings.properties) then two tables are
 * created : tables Polls and PollOptions. Table Polls has titles and message
 * for polls and table PollOptions has all candidates and some other data of
 * those candidates.
 * 
 * @author antonija
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	private ServletContextEvent sce;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		this.sce = sce;

		try {
			// ovdje procitati iz file-a
			Properties prop = new Properties();
			String configFileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");

			prop.load(Files.newInputStream(Paths.get(configFileName)));
			String connectionURL = "jdbc:derby://";

			String tmp = prop.getProperty("host");
			if (tmp == null) {
				throw new NullPointerException("Host property not found in properties");
			} else {
				connectionURL += tmp + ":";
			}
			tmp = prop.getProperty("port");
			if (tmp == null) {
				throw new NullPointerException("Port property not found in properties");
			} else {
				connectionURL += tmp + "//";
			}
			tmp = prop.getProperty("name");
			if (tmp == null) {
				throw new NullPointerException("Name property not found in properties");
			} else {
				connectionURL += tmp;
			}
			tmp = prop.getProperty("user");
			if (tmp == null) {
				throw new NullPointerException("User property not found in properties");
			} else {
				connectionURL += ";user=" + tmp;
			}
			tmp = prop.getProperty("password");
			if (tmp == null) {
				throw new NullPointerException("Password property not found in properties");
			} else {
				connectionURL += ";password=" + tmp;
			}
			connectionURL += ";create=true";

			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException e1) {
				throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
			}
			cpds.setJdbcUrl(connectionURL);
			Connection con = cpds.getConnection();
			DatabaseMetaData dbmd = con.getMetaData();

			checkPolls(dbmd, con);
			checkPollOptions(dbmd, con);
			try {
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks if there are PollOptions tables in database. If no table
	 * is found one is created and is filled with data options for two polls. If
	 * table exists but is empty then that table is also filled with data options
	 * for two polls.
	 * 
	 * @param dbmd input DatabaseMetaData
	 * @param con  input Connection
	 * @throws SQLException sql exception
	 */
	private void checkPollOptions(DatabaseMetaData dbmd, Connection con) throws SQLException {
		ResultSet rs = dbmd.getTables(null, null, "POLLOPTIONS", null);
		if (!rs.next()) {
			createPollOptionsTable(con);
		} else {
			Statement st = con.createStatement();
			st.executeQuery("SELECT * FROM PollOptions");
			ResultSet result = st.getResultSet();
			if (!result.next()) {
				fillPollOptionsData(con, "/WEB-INF/glasanje-definicija.txt",
						"SELECT * FROM Polls WHERE title='Glasanje za omiljeni bend:'");
				fillPollOptionsData(con, "/WEB-INF/glasanje-definicija2.txt",
						"SELECT * FROM Polls WHERE title='Glasanje za omiljenu ljetnu destinaciju:'");
			}
		}
	}

	/**
	 * This method creates PollOptions table and fills that table with data options
	 * for two tables.
	 * 
	 * @param con input Connection
	 * @throws SQLException sql Exception
	 */
	private void createPollOptionsTable(Connection con) throws SQLException {

		PreparedStatement pst = con.prepareStatement(
				"CREATE TABLE PollOptions\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
						+ " optionTitle VARCHAR(100) NOT NULL,\r\n" + " optionLink VARCHAR(150) NOT NULL,\r\n"
						+ " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
						+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
		pst.execute();
		fillPollOptionsData(con, "/WEB-INF/glasanje-definicija.txt",
				"SELECT * FROM Polls WHERE title='Glasanje za omiljeni bend:'");
		fillPollOptionsData(con, "/WEB-INF/glasanje-definicija2.txt",
				"SELECT * FROM Polls WHERE title='Glasanje za omiljenu ljetnu destinaciju:'");

	}

	/**
	 * This method fills PollOptions table with data about poll with candidates described in input file.
	 * @param con input Connection
	 * @param fileName input File path
	 * @param query query command for sql for finding id for wanted poll
	 * @throws SQLException sql exception
	 */
	private void fillPollOptionsData(Connection con, String fileName, String query) throws SQLException {
		String file = sce.getServletContext().getRealPath(fileName);
		List<String> lines = Collections.emptyList();
		long id = 0;
		try {
			lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PreparedStatement st = con.prepareStatement(query);
		st.execute();
		ResultSet result = st.getResultSet();
		//get id from vanted poll
		if (result != null && result.next()) {
			id = result.getLong(1);
		}

		for (String l : lines) {
			String[] parts = l.trim().split("\t");
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, parts[0]);
			pst.setString(2, parts[1]);
			pst.setLong(3, id);
			pst.setLong(4, 0);
			pst.executeUpdate();
		}
	}

	/**
	 * This method checks if there are Polls tables in database. If no table is
	 * found one is created and is filled with data for two polls. If table exists
	 * but is empty then that table id filled with data for two polls.
	 * 
	 * @param dbmd input DatabaseMetaData
	 * @param con  input Connection
	 * @throws SQLException sql exception
	 */
	private void checkPolls(DatabaseMetaData dbmd, Connection con) throws SQLException {
		ResultSet rs = dbmd.getTables(null, "IVICA", "POLLS", null);
		if (!rs.next()) {
			createPollsTable(con);
		} else {
			Statement st = con.createStatement();
			st.executeQuery("SELECT * FROM Polls");
			ResultSet result = st.getResultSet();
			if (!result.next()) {
				fillPollsData(con);
			}
			result.close();
		}
	}

	/**
	 * This method creates Polls table and fills that table with data for two
	 * tables.
	 * 
	 * @param con input Connection
	 * @throws SQLException sql Exception
	 */
	private void createPollsTable(Connection con) throws SQLException {
		PreparedStatement pst = con.prepareStatement(
				"CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)");
		pst.execute();
		fillPollsData(con);
	}

	/**
	 * This method fills Polls data created in database. Polls table is filled with
	 * data about two polls. Voting for favourite band and voting for favourite summer destination.
	 * 
	 * @param con input Connection
	 * @throws SQLException
	 */
	private void fillPollsData(Connection con) throws SQLException {

		PreparedStatement pst;
		pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, "Glasanje za omiljeni bend:");
		pst.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
		pst.executeUpdate();
		// dodaj jos jednu
		pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, "Glasanje za omiljenu ljetnu destinaciju:");
		pst.setString(2, "Od sljedećih destinacija, koja Vam je najdraža? Kliknite na link kako biste glasali!");
		pst.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}