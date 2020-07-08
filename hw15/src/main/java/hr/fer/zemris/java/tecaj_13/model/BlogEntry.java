package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class models one BlogEntry from this app
 * 
 * @author antonija
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {
	/**
	 * id of entry
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * private creator of this entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogUser user;

	/**
	 * list of comments for this entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();

	/**
	 * date if creation of this entry
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	/**
	 * date if last modification of this entry
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date lastModifiedAt;

	/**
	 * title of this entry
	 */
	@Column(length = 200, nullable = false)
	private String title;

	/**
	 * text of this entry
	 */
	@Column(length = 4096, nullable = false)
	private String text;

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id input id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for comments
	 * 
	 * @return comments
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter for comments
	 * 
	 * @param comments input comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for createdAt
	 * 
	 * @return createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for createdAt
	 * 
	 * @param createdAt input createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for lastModifiedAt
	 * 
	 * @return lastModifiedAt
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for lastModifiedAt
	 * 
	 * @param lastModifiedAt input lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter for title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title
	 * 
	 * @param title input title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for text
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for text
	 * 
	 * @param text input text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for user
	 * 
	 * @return user
	 */
	public BlogUser getUser() {
		return user;
	}

	/**
	 * Setter for user
	 * 
	 * @param user input user
	 */
	public void setUser(BlogUser user) {
		this.user = user;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}