package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ChartUtils;

/**
 * This servlet extends HttpServlet. It creates simple pie chart that displays
 * percentage of OS usage. This servlet dynamically creates a png image and writes it in
 * response output stream. 
 * 
 * @author antonija
 *
 */
@WebServlet(name = "createPieChart", urlPatterns = { "/reportImage" })
public class ServletChart extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "Results");
		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();

		int width = 500;
		int height = 350;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
		outputStream.close();
	}

	/**
	 * Method creates a sample dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;
	}

	/**
	 * Method creates a chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		return chart;

	}

}
