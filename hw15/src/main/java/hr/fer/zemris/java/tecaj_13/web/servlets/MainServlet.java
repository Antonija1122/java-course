package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet represents main page. Request is redirected to index.jsp where
 * users are shown and login is available. Also possibility of registration for new users is
 * provided
 * 
 * @author antonija
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("users", DAOProvider.getDAO().getUsers());

		BlogUser r = new BlogUser();
		FormularLogin f = new FormularLogin();
		f.popuniIzRecorda(r);

		req.setAttribute("login", f);

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}