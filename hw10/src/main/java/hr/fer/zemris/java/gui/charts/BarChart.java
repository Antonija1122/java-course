package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents bar chart
 * 
 * @author antonija
 *
 */
public class BarChart {

	/**
	 * List of data represented by instances of XYValue class
	 */
	private List<XYValue> list;

	/**
	 * x label explanation
	 */
	private String aboutX;
	/**
	 * y label explanation
	 */
	private String aboutY;
	/**
	 * minimal y value
	 */
	private int ymin;
	/**
	 * max y value
	 */
	private int ymax;

	/**
	 * distance between two y values in grid
	 */
	private int distance;

	/**
	 * Public constructor sets values of this instance to input values
	 * 
	 * @param list     input data
	 * @param aboutX   x label explanation
	 * @param aboutY   y label explanation
	 * @param ymin     min y
	 * @param ymax     max y
	 * @param distance distance between two y values
	 */
	public BarChart(List<XYValue> list, String aboutX, String aboutY, int ymin, int ymax, int distance) {
		super();
		this.list = list;
		this.aboutX = aboutX;
		this.aboutY = aboutY;
		if (ymin < 0) {
			throw new IllegalArgumentException();
		}
		this.ymin = ymin;
		if (ymax <= ymin) {
			throw new IllegalArgumentException();
		}
		this.ymax = ymax;
		setDistance(distance);
		this.distance = distance;
		check();
	}

	/**
	 * This method sets distance to input distance or first increased value of
	 * distance that divides ymax-ymin
	 * 
	 * @param distance2 input distance
	 */
	private void setDistance(int distance2) {
		if ((ymax - ymin) % distance2 == 0) {
			this.distance = distance2;
			return;
		}
		while ((ymax - ymin) % distance2 != 0) {
			distance2++;
		}
		this.distance = distance2;
	}

	/**
	 * This methid checks if all y values are larger than ymin
	 */
	private void check() {
		for (XYValue value : list) {
			if (value.getY() < ymin) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Getter method for distance
	 * 
	 * @return distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Getter method for list of data
	 * 
	 * @return list
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Getter method for aboutX
	 * 
	 * @return aboutX
	 */
	public String getAboutX() {
		return aboutX;
	}

	/**
	 * Getter method for aboutY
	 * 
	 * @return aboutY
	 */
	public String getAboutY() {
		return aboutY;
	}

	/**
	 * Getter method for ymin
	 * 
	 * @return ymin
	 */
	public int getYmin() {
		return ymin;
	}

	/**
	 * Getter method for ymax
	 * 
	 * @return ymax
	 */
	public int getYmax() {
		return ymax;
	}

}
