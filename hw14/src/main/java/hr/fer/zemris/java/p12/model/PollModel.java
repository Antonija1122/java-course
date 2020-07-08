package hr.fer.zemris.java.p12.model;

/**
 * 
 * Poll model that has data: polls id, title and message. This class offers
 * getters and setters for all properties
 * 
 * @author antonija
 *
 */
public class PollModel {

	/**
	 * polls id
	 */
	private long id;
	/**
	 * polls title
	 */
	private String title;
	/**
	 * polls message
	 */
	private String message;

	/**
	 * Setter method for polls message
	 * 
	 * @param message input message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter method for id
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter method for title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter method for message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for polls id
	 * 
	 * @param id input id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Setter method for polls title
	 * 
	 * @param title input title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
