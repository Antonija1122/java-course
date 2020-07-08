package coloring.algorithms;

/**
 * This class represents one pixel in picture. Private variables x and y
 * represent coordinates of this pixel. This class has methods: toSting;
 * hashCode(); equals().
 * 
 * @author antonija
 *
 */
public class Pixel {

	/**
	 * Public value of x coordinate
	 */
	public int x;

	/**
	 * Public value of y coordinate
	 */
	public int y;

	/**
	 * Public constructor sets x and y values to input values.
	 * 
	 * @param x input value for x
	 * @param y input value for y
	 */
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * String representation for pixel in form: (x,y).
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * This method returns hash code for this pixel
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * This method represents equals method for two pixels. Two pixels are equal if
	 * they have equal x and y values.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
