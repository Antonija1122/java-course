package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This listener registers current time when app is started so every servlet and
 * jsp can reach that data.
 * 
 * @author antonija
 *
 */
@WebListener
public class Informacije implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("timeStart", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}