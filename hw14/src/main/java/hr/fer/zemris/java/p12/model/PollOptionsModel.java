package hr.fer.zemris.java.p12.model;

/**
 * 
 * Poll model that has data: pollOptions id, title, url link, pollID and
 * votesCount. This class offers getters and setters for all properties
 * 
 * @author antonija
 *
 */
public class PollOptionsModel {

	/**
	 * PollOptions id
	 */
	private long id;
	/**
	 * PollOptions title
	 */
	private String optionTitle;
	/**
	 * PollOptions url link
	 */
	private String optionLi;
	/**
	 * PollOptions Poll id
	 */
	private long pollID;
	/**
	 * PollOptions vote count
	 */
	private long votesCount;

	/**
	 * Getter method for id
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter method for optionTitle
	 * 
	 * @return optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Getter method for optionLi
	 * 
	 * @return optionLi
	 */
	public String getOptionLi() {
		return optionLi;
	}

	/**
	 * Getter method for pollID
	 * 
	 * @return pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Getter method for votesCount
	 * 
	 * @return votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter method for id
	 * 
	 * @param id input id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Setter method for optionTitle
	 * 
	 * @param optionTitle input optionTitle
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Setter method for optionLi
	 * 
	 * @param optionLi input optionLi
	 */
	public void setOptionLi(String optionLi) {
		this.optionLi = optionLi;
	}

	/**
	 * Setter method for pollID
	 * 
	 * @param pollID input pollID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Setter method for votesCount
	 * 
	 * @param votesCount input votesCount
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
