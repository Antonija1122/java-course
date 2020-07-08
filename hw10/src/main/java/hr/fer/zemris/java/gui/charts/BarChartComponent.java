package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -28031991L;
	/**
	 * arrow size
	 */
	private static final int ARR_SIZE = 6;
	/**
	 * distance between text and numbers and numbers from axiss
	 */
	private static final int space = 30;
	/**
	 * text heigth
	 */
	private static final int fontHeight = 13;
	/**
	 * distance to y axis
	 */
	private static final int dToy = fontHeight + space + space;
	/**
	 * distance to x axis
	 */
	private static final int dTox = fontHeight + space + space;
	/**
	 * available height for drawing chart
	 */
	private int availableHeight;
	/**
	 * available width for drawing chart
	 */
	private int availableWidth;

	/**
	 * private barChart
	 */
	private BarChart barChart;

	/**
	 * public construcotr accepts barChart
	 * @param barChart
	 */
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
		repaint();
	}

	/**
	 * for given data draws a barChart
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		int maxChartValue = Integer.MIN_VALUE;
		int minChartValue = Integer.MAX_VALUE;

		for (XYValue value : barChart.getList()) {
			maxChartValue = Math.max(maxChartValue, value.getY());
			minChartValue = Math.min(minChartValue, value.getY());
		}

		int width_Of_Y_label_desc = g2D.getFontMetrics().stringWidth(barChart.getAboutY());
		int width_Of_X_label_desc = g2D.getFontMetrics().stringWidth(barChart.getAboutX());
		
		// int heightOfFont = g2D.getFontMetrics().getAscent();
		availableHeight = getHeight() - dTox - ARR_SIZE * 2;
		availableWidth = getWidth() - dToy - ARR_SIZE * 2;

		// labela na y-osi
		int y_label_yloc = ARR_SIZE * 2 + (availableHeight) / 2 + width_Of_Y_label_desc ;

		drawText(barChart.getAboutY(), g2D, -Math.PI / 2, -y_label_yloc, fontHeight);

		// brojevi na y-osi
		draw_y_values(g2D);
		// brojevi na x_osi

		draw_x_values(getHeight() - dTox + space, g2D);

		// labela na x-osi
		int x_label_xloc = dToy + (availableWidth) / 2 - width_Of_X_label_desc / 2;
		int x_label_yloc = getHeight();

		drawText(barChart.getAboutX(), g2D, 0, x_label_xloc, x_label_yloc);

		drawArrow(g2D, dToy - ARR_SIZE, getHeight() - dTox, getWidth(), getHeight() - dTox);
		drawArrow(g2D, dToy, getHeight() - dTox + ARR_SIZE, dToy, 0);

		// paint bars
		int gap = 1;
		int availableWidth = getWidth() - dToy - ARR_SIZE * 2 - gap * (barChart.getList().size() - 1);

		int[] widthArray = calculateBarDimensions(availableWidth, barChart.getList().size());

		int x = dToy;

		Color color = new Color(255, 120, 0);

		int index = 0;
		double factor = (double) (maxChartValue) / (barChart.getYmax() - barChart.getYmin());

		int scaledAvailableHeight = (int) (factor * availableHeight);

		for (XYValue xyValue : barChart.getList()) {

			int y_axis = xyValue.getY();
			int height = (int) ((scaledAvailableHeight) * ((double) y_axis / maxChartValue));

			g2D.setColor(color);
			g2D.fillRect(x, availableHeight + ARR_SIZE * 2 - height, widthArray[index], height);

			x += (widthArray[index++] + gap);
			g.setColor(new Color(255, 220, 0));
			g2D.drawLine(x, getHeight() - dTox + ARR_SIZE, x, 0);

			g.setColor(new Color(255, 120, 0));

		}
	}

	/**
	 * This method draws x numbers next to x axis
	 * @param y0
	 * @param g Graphics2D
	 */
	private void draw_x_values(int y0, Graphics2D g) {

		Font currentFont = g.getFont();
		g.setFont(new Font("default", Font.BOLD, 12));
		int len = barChart.getList().size();
		int offset = (int) ((availableWidth) * ((double) 1 / (len))) / 2;

		for (int i = 0; i < len; ++i) {
			int newX = (int) (dToy + (int) ((availableWidth) * ((double) i / (len))));
			g.drawString(String.valueOf(i + 1), newX + offset, y0);

		}

		g.setFont(currentFont);

	}

	/**
	 * This method draws y numbers next to y axis
	 * @param g Graphics2D
	 */
	private void draw_y_values(Graphics2D g) {

		int startingWidth = g.getFontMetrics().stringWidth("0");
		Font currentFont = g.getFont();
		g.setFont(new Font("default", Font.BOLD, 12));
		Color originColor;
		int xInitOffset = fontHeight + space;

		int len = (barChart.getYmax() - barChart.getYmin()) / barChart.getDistance();

		for (int i = 0; i <= len; ++i) {

			int value = i * barChart.getDistance() + barChart.getYmin();
			int offset = g.getFontMetrics().stringWidth(String.valueOf(value)) - startingWidth;
			int newY = (int) (availableHeight - (availableHeight) * ((double) i / (len))) + 2 * ARR_SIZE;

			g.drawString(String.valueOf(value), xInitOffset - offset, newY + g.getFontMetrics().getAscent() / 3);
			originColor = g.getColor();
			g.setColor(new Color(255, 200, 0));
			g.drawLine(dToy - ARR_SIZE, newY, getWidth(), newY);
			g.setColor(originColor);
		}
		g.setFont(currentFont);

	}

	/**
	 * Writes label description from origin point 
	 * @param descr axis description
	 * @param g Graphics2D
	 * @param angle angle of text
	 * @param x_origin x start
	 * @param y_origin y start
	 */
	private void drawText(String y_ax_descr, Graphics2D g, double angle, int x_origin, int y_origin) {

		AffineTransform defaultAt = g.getTransform();

		AffineTransform at = new AffineTransform();
		at.rotate(angle);
		g.setTransform(at);
		g.drawString(y_ax_descr, x_origin, y_origin);

		g.setTransform(defaultAt);

	}

	/**
	 * This method draws an arrow from 
	 * @param g1 Graphics
	 * @param x1 start point x
	 * @param y1 start point y
	 * @param x2 end point x
	 * @param y2 end point x
	 */
	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 },
				4);
	}

	/**
	 * This method calculates bar width
	 * @param available space to be divided
	 * @param n number of divisions for available space
	 * @return array of calculated divisions
	 */
	private static int[] calculateBarDimensions(int available, int n) {

		int[] dimensionArray = new int[n];
		Arrays.fill(dimensionArray, (int) Math.round((double) available / n));

		if (available == 0 || (dimensionArray[0] * n - available) == 0)
			return dimensionArray;

		int offset = available - dimensionArray[0] * n;
		int leftIndex = n / 2;
		int rightIndex = leftIndex;
		int increment = offset / Math.abs(offset);

		// If the number is odd
		if ((int) Math.abs(offset) % 2 == 1) {
			dimensionArray[leftIndex] += increment;
			leftIndex--;
			rightIndex++;
			offset -= increment;
		}

		while (offset != 0) {
			leftIndex--;
			rightIndex++;
			dimensionArray[leftIndex] += increment;
			dimensionArray[rightIndex] += increment;
			offset -= 2 * increment;
		}
		return dimensionArray;
	}

}
