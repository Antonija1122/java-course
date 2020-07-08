package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This servlet checks login data and sets current user if input data is
 * correct. Otherwise request is redirected back to main page with error
 * messages
 * 
 * @author antonija
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			obradi(req, resp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			obradi(req, resp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks login data and sets current user if input data is correct.
	 * Otherwise request is redirected back to main page with error
	 * 
	 * @param req input request 
	 * @param resp input reponse
	 * @throws ServletException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	protected void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, NoSuchAlgorithmException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Login".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/index.jsp");
			return;
		}

		FormularLogin f = new FormularLogin();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		if (f.imaPogresaka()) {
			req.setAttribute("login", f);
			req.setAttribute("users", DAOProvider.getDAO().getUsers());
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}

		BlogUser current = DAOProvider.getDAO().getUser(f.getNick());
		postaviUsera(req, current);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/index.jsp");
	}

	/**
	 * This method sets data about loged in user in sessions attributes 
	 * @param req input request 
	 * @param current current BlogUser
	 */
	private void postaviUsera(HttpServletRequest req, BlogUser current) {
		req.getSession().setAttribute("currentid", current.getId());
		req.getSession().setAttribute("currentfn", current.getFirstName());
		req.getSession().setAttribute("currentln", current.getLastName());
		req.getSession().setAttribute("currentnick", current.getNick());
	}
}
