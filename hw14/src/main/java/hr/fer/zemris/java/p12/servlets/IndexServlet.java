package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollModel;

/**
 * This servlet reads all polls from database and sets that list to request
 * attributes. This request if forwarded to "/WEB-INF/pages/index.jsp" that
 * renders links of polls to user.
 * 
 * @author antonija
 *
 */
@WebServlet(name = "IndexServlet", urlPatterns = { "/servleti/index.html" })
public class IndexServlet extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DAO dao = DAOProvider.getDao();
		List<PollModel> polls = dao.getPollsList();
		req.setAttribute("pollList", polls);

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
