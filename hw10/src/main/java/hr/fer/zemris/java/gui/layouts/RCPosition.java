package hr.fer.zemris.java.gui.layouts;

/**
 * This class represents position of component in CalcLayout class. Two private
 * variables represent number of row and number of column for a component.
 * 
 * @author antonija
 *
 */
public class RCPosition {

	/**
	 * first value represents ror number
	 */
	private int x;
	/**
	 * second value represent column number
	 */
	private int y;

	/**
	 * Public constructor sets private variables to input values
	 * @param x number of input row
	 * @param y number of input column
	 */
	public RCPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter method for x
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter method for y
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * {@inheritDoc}
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
