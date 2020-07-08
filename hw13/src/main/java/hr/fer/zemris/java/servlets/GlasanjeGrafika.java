package hr.fer.zemris.java.servlets;

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

/**
 * This servlet extends HttpServlet. It creates simple pie chart that displays
 * percentage of votes for each band stored in sessions attributes. This servlet
 * dynamically creates a png image and writes it in response output stream.
 * 
 * @author antonija
 *
 */
@WebServlet(name = "voteResChart", urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafika extends HttpServlet {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset, "Pie-chart");
		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();

		int width = 500;
		int height = 400;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
		outputStream.close();
	}

	/**
	 * Method creates a sample dataset from votes number stored in sessions
	 * attributes.
	 */
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();
		List<String> bands = (List<String>) req.getSession().getAttribute("ids");

		for (String id : bands) {
			result.setValue((String) req.getSession().getAttribute(id + "name"),
					Integer.parseInt((String) req.getSession().getAttribute(id + "votes")));
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