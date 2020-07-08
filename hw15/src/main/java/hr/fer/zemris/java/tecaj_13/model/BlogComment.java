package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * This class models one BlogComment from this app
 * @author antonija
 *
 */
@Entity
@Table(name="blog_comments")
@Cacheable(true)
public class BlogComment {

	/**
	 * comments id
	 */
	@Id @GeneratedValue
	private Long id;
	/**
	 * comments blogentry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	private BlogEntry blogEntry;
	/**
	 * creators email
	 */
	@Column(length=100,nullable=false)
	private String usersEMail;
	/**
	 * text of comment
	 */
	@Column(length=4096,nullable=false)
	private String message;
	/**
	 * time in which this comment was posted on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date postedOn;
	
	/**
	 * Getter for id
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter for id 
	 * @param id input id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for blogEntry
	 * @return blogEntry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Setter for blogEntry 
	 * @param id input blogEntry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for usersEMail
	 * @return usersEMail
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for usersEMail 
	 * @param usersEMail input usersEMail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message 
	 * @param message input message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for postedOn
	 * @return postedOn
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for postedOn 
	 * @param postedOn input postedOn
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}