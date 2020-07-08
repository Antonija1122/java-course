package hr.fer.zemris.java.servlets;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.rest.TagsJason;

/**
 * This class is a listener that sets path of this app in TagsJason class
 * 
 * @author antonija
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		TagsJason.path=sce.getServletContext().getRealPath("");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}