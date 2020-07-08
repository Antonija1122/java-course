package hr.fer.zemris.java.p12.dao;


import java.util.List;

import hr.fer.zemris.java.p12.model.PollModel;
import hr.fer.zemris.java.p12.model.PollOptionsModel;

/**
 * Interface towards subsystem for database extraction.
 * @author antonija
 *
 */
public interface DAO {

	/**
	 * This method gets all polls from database 
	 * @return list of polls
	 * @throws DAOException in case of error
	 */
	public List<PollModel> getPollsList() throws DAOException;
	
	/**
	 * This method gets all PollOptions from database 
	 * @return list of PollOptions
	 * @throws DAOException in case of error
	 */
	public List<PollOptionsModel> getPollOptions(long id, String order) throws DAOException;
	
	/**
	 * This method gets PollOption from database with input id
	 * @return PollOption with input id
	 * @throws DAOException in case of error
	 */
	public PollOptionsModel getPollOption(long id) throws DAOException;
	
	/**
	 * This method gets Poll from database with input id
	 * @return Poll with input id
	 * @throws DAOException in case of error
	 */
	public PollModel getPoll(long id) throws DAOException;
	
	/**
	 * This method increases voteCount variable for PollOption that has input id
	 * @param id input id for required PollOption
	 * @throws DAOException in case of error
	 */
	public void updateVote(long id) throws DAOException;
	
}