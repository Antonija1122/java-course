package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
 * This servlet extends HttpServlet. It reads voting results from database. List
 * of PollOptionsModels and list of winners are saved to attributes. Request is
 * forwarded to jsp "/WEB-INF/pages/glasanjeRez.jsp".
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glasRez", urlPatterns = { "/servleti/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("pollID");
		PollModel poll = DAOProvider.getDao().getPoll(Long.parseLong(id));
		List<PollOptionsModel> options = DAOProvider.getDao().getPollOptions(poll.getId(), "VOTESCOUNT DESC");
		req.setAttribute("poll", poll);
		req.setAttribute("options", options);
		req.setAttribute("winners", getWinners(options));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}

	/**
	 * This method creates list of PollOptionsModels that have max number of votes
	 * from list of all PollOptionsModels.
	 * 
	 * @param options input list of PollOptionsModels
	 * @return list of winners
	 */
	private List<PollOptionsModel> getWinners(List<PollOptionsModel> options) {
		List<PollOptionsModel> winners = new ArrayList<PollOptionsModel>();
		long max = options.get(0).getVotesCount();
		for (PollOptionsModel m : options) {
			if (m.getVotesCount() >= max) {
				winners.add(m);
			}
		}
		return winners;

	}

}
