package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * This servlet does action of editing blog. Text and title are read from
 * request parameters and current blog entry is edited
 * 
 * @author antonija
 */
@WebServlet("/servleti/editBlog")
public class EditServlet extends HttpServlet {

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

	protected void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, NoSuchAlgorithmException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/"
					+ (String) req.getSession().getAttribute("currentnick"));
			return;
		}
		// nekako editirati stari blogentry
		BlogEntry editedEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id")));
		editedEntry.getUser().getBlogs().remove(editedEntry);

		editedEntry.setLastModifiedAt(new Date());
		editedEntry.setTitle((String) req.getParameter("title"));
		editedEntry.setText((String) req.getParameter("text"));
		DAOProvider.getDAO().editBlogEntry(editedEntry);
		// maknut stari dodat novi -

		editedEntry.getUser().getBlogs().add(editedEntry);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/"
				+ (String) req.getSession().getAttribute("currentnick"));
	}

}
