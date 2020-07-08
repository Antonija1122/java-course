package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This contains program that shows bar chart for data from file given in input
 * arguments for main program.
 * 
 * @author antonija
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * private BarChartComponent
	 */
	private BarChartComponent barChartComponent;
	/**
	 * path to file with data fot bar chart
	 */
	private Path path;

	/**
	 * Public constructor initialize private variables
	 * 
	 * @param barChart input barChart
	 * @param path     path to data file for chart
	 */
	public BarChartDemo(BarChart barChart, Path path) {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLocation(20, 50);
		setSize(500, 500);
		setTitle("Moj prvi prozor!");
		barChartComponent = new BarChartComponent(barChart);
		this.path = path;
		initGUI();

	}

	/**
	 * This method initializes gui frame
	 */
	private void initGUI() {
		// kod za inicijalizaciju komponenti koje prozor prikazuje
		// ...
		Container cp = getContentPane();
		JPanel title = new JPanel();
		title.add(new JLabel(path.toString()), BorderLayout.CENTER);
		cp.add(barChartComponent, BorderLayout.CENTER);
		cp.add(title, BorderLayout.NORTH);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("You have to enter path to chart info");
			System.exit(-1);
		}

		Path path = Paths.get(".").resolve(args[0]);
		if (!Files.isRegularFile(path))
			throw new IllegalArgumentException();

		BarChart barChart = createContent(path);
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(barChart, path).setVisible(true);
		});

	}

	/**
	 * This method creates content for barChart
	 * 
	 * @param path path to data file for barChart
	 * @return created BarChart
	 */
	private static BarChart createContent(Path path) {

		List<XYValue> listXYValue = new ArrayList<>();
		String x_ax_descr;
		String y_ax_descr;
		int y_min;
		int y_max;
		int adj_gap;

		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

			x_ax_descr = lines.get(0);
			y_ax_descr = lines.get(1);
			String[] dimensions = lines.get(2).split("\\s+");

			for (String d : dimensions) {
				String[] x_y = d.split(",");
				if (x_y.length != 2)
					throw new IllegalArgumentException();
				int x = Integer.parseInt(x_y[0]);
				int y = Integer.parseInt(x_y[1]);
				listXYValue.add(new XYValue(x, y));
			}
			y_min = Integer.parseInt(lines.get(3));
			y_max = Integer.parseInt(lines.get(4));
			adj_gap = Integer.parseInt(lines.get(5));

		} catch (IOException | NumberFormatException e) {
			throw new IllegalArgumentException();
		}

		return new BarChart(listXYValue, x_ax_descr, y_ax_descr, y_min, y_max, adj_gap);
	}

}
