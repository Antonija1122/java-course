package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * This class models one BlogUser from this app
 * 
 * @author antonija
 *
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	/**
	 * id of user
	 */
	@Id
	@GeneratedValue
	private long id;

	/**
	 * first name of user
	 */
	@Column(nullable = false)
	private String firstName;

	/**
	 * last name of user
	 */
	@Column(nullable = false)
	private String lastName;

	/**
	 * nickname of user
	 */
	@Column(unique = true, nullable = false)
	private String nick;

	/**
	 * email of user
	 */
	@Column(nullable = false)
	private String email;

	/**
	 * hashed password of user
	 */
	@Column(nullable = false)
	private String passwordHash;

	/**
	 * list of blog entrys for this user
	 */
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("createdAt")
	private List<BlogEntry> blogs = new ArrayList<>();

	/**
	 * public constructor for this user
	 */
	public BlogUser() {

	}

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param user input id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for firstName
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for firstName
	 * 
	 * @param firstName input firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for lastName
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for lastName
	 * 
	 * @param lastName input lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick input nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email input email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for passwordHash
	 * 
	 * @return passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for passwordHash
	 * 
	 * @param passwordHash input passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter for blogs
	 * 
	 * @return blogs
	 */
	public List<BlogEntry> getBlogs() {
		return blogs;
	}

	/**
	 * Setter for blogs
	 * 
	 * @param blogs input blogs
	 */
	public void setBlogs(List<BlogEntry> blogs) {
		this.blogs = blogs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		BlogUser other = (BlogUser) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
