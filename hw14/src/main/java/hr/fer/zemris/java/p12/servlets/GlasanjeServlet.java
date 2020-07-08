package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollModel;
import hr.fer.zemris.java.p12.model.PollOptionsModel;

/**
 * This servlet extends HttpServlet. It reads data from database and registeres
 * all candidates (pollOptions) for poll with id given in requests parameters.
 * This request if forwarded to jsp "/WEB-INF/pages/glasanjeIndex.jsp".
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glasanje", urlPatterns = { "/servleti/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Učitaj raspoložive bendove
		long id = 0;
		try {
			id = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		List<PollOptionsModel> options = DAOProvider.getDao().getPollOptions(id, "id");
		PollModel poll = DAOProvider.getDao().getPoll(id);
		req.setAttribute("poll", poll);
		req.setAttribute("pollOptions", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
