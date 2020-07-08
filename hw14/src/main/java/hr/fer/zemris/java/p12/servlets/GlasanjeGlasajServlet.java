package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * This servlet extends HttpServlet. This servlet updates(increments by 1) votes
 * for candidate with id given in parameters and redirects request to servlet "/servleti/glasanje-rezultati".
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glasGlas", urlPatterns = { "/servleti/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = 0;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		DAOProvider.getDao().updateVote(id);
		long Pollid = DAOProvider.getDao().getPollOption(id).getPollID();
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + Pollid);

	}

}
