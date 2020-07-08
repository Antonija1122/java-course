package hr.fer.zemris.java.gui.charts;

/**
 * This class represents data with x and y values that represent data for chart
 * in class BarChart
 * 
 * @author antonija
 *
 */
public class XYValue {

	/**
	 * value x of data
	 */
	private int x;
	/**
	 * value y of data
	 */
	private int y;

	/**
	 * Getter method for x
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter method for y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Public constructor creates instance of this class with x and y values of
	 * input values
	 * 
	 * @param x input data x
	 * @param y input data y
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

}
