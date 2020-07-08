package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * Interface towards subsystem for database extraction.
 * @author antonija
 *
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Method that returns blog entries from database that belong to input user 
	 * @param user input user
	 * @return blog entries from user
	 * @throws DAOException
	 */
	public List<BlogEntry> getBlogEntrys(BlogUser user) throws DAOException;
	
	/**
	 * Method that returns user from database with nick input nick 
	 * @param nick users nick
	 * @return user with nick equal to input nick
	 * @throws DAOException
	 */
	public BlogUser getUser(String nick) throws DAOException;
	
	/**
	 * Method returns list of all users from database
	 * @return list of all users from database
	 * @throws DAOException
	 */
	public List<BlogUser> getUsers() throws DAOException;
	
	/**
	 * Method adds input user to database
	 * @param user input user
	 * @throws DAOException
	 */
	public void newUser(BlogUser user) throws DAOException;
	
	
	/**
	 * Method adds input entry to database
	 * @param entry input entry
	 * @throws DAOException
	 */
	public void newBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Method edits input entry in database
	 * @param user input user
	 * @throws DAOException
	 */
	public void editBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Method adds input comment to database
	 * @param comment input comment
	 * @throws DAOException
	 */
	public void newBlogComment(BlogComment comment) throws DAOException;
	
	
	
}