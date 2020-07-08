package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class provides EntityManagerFactory. It holds EntityManagerFactory as a
 * private variable and provides its getter
 * 
 * @author antonija
 *
 */
public class JPAEMFProvider {

	/**
	 * private instance of EntityManagerFactory
	 */
	public static EntityManagerFactory emf;

	/**
	 * Public getter for emf
	 * @return enf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Public constructor for emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}