package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionsModel;

/**
 * This servlet extends HttpServlet. It creates simple pie chart that displays
 * percentage of votes for each candidate stored pollOptions for poll with id
 * stored in parameters. This servlet dynamically creates a png image and writes
 * it in response output stream.
 * 
 * @author antonija
 *
 */
@WebServlet(name = "voteResChart", urlPatterns = { "/servleti/glasanje-grafika" })
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("pollID"));
		PieDataset dataset = createDataset(req, id);
		JFreeChart chart = createChart(dataset, "Pie-chart");
		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();

		int width = 500;
		int height = 400;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
		outputStream.close();
	}

	/**
	 * Method creates a sample dataset from votes number stored database for current poll
	 */
	private PieDataset createDataset(HttpServletRequest req, long id) {
		DefaultPieDataset result = new DefaultPieDataset();
		List<PollOptionsModel> options = DAOProvider.getDao().getPollOptions(id, "id");

		for (PollOptionsModel option : options) {
			result.setValue(option.getOptionTitle(), option.getVotesCount());
		}
		return result;

	}

	/**
	 * Creates a JFreeChart from input data
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		return chart;

	}

}