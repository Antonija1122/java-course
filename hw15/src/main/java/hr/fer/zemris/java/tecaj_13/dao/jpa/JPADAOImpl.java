package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This class is an implementation of subsystem DAO in JPA technology JSP 
 * 
 * @author antonija
 *
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntrys(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			List<BlogEntry> entrys = (List<BlogEntry>) em.createQuery("select e from BlogEntry as e where e.user=:user")
					.setParameter("user", user).getResultList();
			return entrys;
		} catch (Exception e) {
			throw new DAOException("Error ocurred while extracting entrys for user: " + user.getNick());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			BlogUser user = (BlogUser) em.createQuery("select e from BlogUser as e where e.nick=:nick")
					.setParameter("nick", nick).getSingleResult();
			return user;
		} catch (Exception e) {
			throw new DAOException("Error ocurred while extracting user with nick " + nick);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			List<BlogUser> entrys = (List<BlogUser>) em.createQuery("select e from BlogUser as e").getResultList();
			return entrys;
		} catch (Exception e) {
			throw new DAOException("Error ocurred while extracting users from database");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newUser(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newBlogComment(BlogComment comment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		//comment.getBlogEntry().getComments().add(comment);
		em.persist(comment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.merge(entry);
	}

}