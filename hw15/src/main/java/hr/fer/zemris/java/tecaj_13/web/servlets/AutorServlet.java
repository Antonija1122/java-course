package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet parses all requests with path pattern /servleti/author/*. It
 * dispatches request to diferent jsps- new, edit, autor itd. depending on request path
 * 
 * @author antonija
 *
 */
@WebServlet("/servleti/author/*")
public class AutorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] parts = parseRequest(req.getPathInfo());

		if (parts.length == 1) {
			getNick(parts, req, resp);
			req.getRequestDispatcher("/WEB-INF/pages/autor.jsp").forward(req, resp);
		} else if (parts.length == 2) {
			getNick(parts, req, resp);
			if (parts[1].equals("new")) {
				checkCurrentUser(parts, req, resp);
				req.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(req, resp);
			} else if (parts[1].equals("edit")) {
				checkCurrentUser(parts, req, resp);
				long id = Long.parseLong(req.getParameter("id"));
				req.setAttribute("blogEntry", DAOProvider.getDAO().getBlogEntry(id));
				req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
			} else {
				// mora biti broj koji postoji
				try {
					long EID = Long.parseLong(parts[1]);
					req.setAttribute("blogEntry", DAOProvider.getDAO().getBlogEntry(EID));
					req.getRequestDispatcher("/WEB-INF/pages/EID.jsp").forward(req, resp);
				} catch (NumberFormatException | DAOException e) {
					req.setAttribute("error", "Given URL is not valid!");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				}
			}

		} else {
			req.setAttribute("error", "Given URL is not valid!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}

	/**
	 * This method checks if user is loged in 
	 * @param parts
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkCurrentUser(String[] parts, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession().getAttribute("currentnick") != null
				&& ((String) req.getSession().getAttribute("currentnick")).equals(parts[0])) {
			/// nesto dodti

			return;
		}
		req.setAttribute("error", "Given URL is not valid!");
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);

	}

	private void getNick(String[] parts, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			BlogUser user = DAOProvider.getDAO().getUser(parts[0]);
			try {
				req.setAttribute("blogs", DAOProvider.getDAO().getBlogEntrys(user));
			} catch (DAOException e) {
				req.setAttribute("blogs", Collections.EMPTY_LIST);
			}
			req.setAttribute("author", user);
		} catch (DAOException e) {
			req.setAttribute("error", "Given URL is not valid!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}

	/**
	 * This method splits paths in parts
	 * @param pathInfo input path
	 * @return
	 */
	private String[] parseRequest(String pathInfo) {
		pathInfo = pathInfo.substring(1);
		String[] parts = pathInfo.split("/");

		return parts;
	}

}
