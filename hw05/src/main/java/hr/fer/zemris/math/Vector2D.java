package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Class defines Vector in two dimensions. Vector has 2 coordinates, x and y.
 * Class provides public constructor for creating vectors and also implements
 * methods for operating with these vectors. Methods are: public double getX();
 * public double getY(); public void translate(Vector2D offset); public Vector2D
 * translated(Vector2D offset); public void rotate(double angle); public
 * Vector2D rotated(double angle); public void scale(double scaler); public
 * Vector2D scaled(double scaler); public Vector2D copy();
 * 
 * @author antonija
 *
 */
public class Vector2D {

	private static double precision = 0.000001;

	/**
	 * X value of length of vector
	 */
	private double x;

	/**
	 * Y value of length of vector
	 */
	private double y;

	/**
	 * Public constructor sets values of x and y to input values and sets start of
	 * vector in (0,0).
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter method for value x (length on abscissa) of this vector.
	 * 
	 * @return x value
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Getter method for value y (length on ordinate) of this vector.
	 * 
	 * @return y value
	 */
	public double getY() {
		return this.y;
	}


	/**
	 * Method translates this vector for offset vector.
	 * 
	 * @param offset
	 */
	public void translate(Vector2D offset) {

		this.x +=  offset.x;
		this.y +=  offset.y;
	}

	/**
	 * Method returns translation of this vector for offset vector. This vector
	 * remains unchanged.
	 * 
	 * @param offset input vector
	 * @return translated vector
	 */
	public Vector2D translated(Vector2D offset) {

		Vector2D newVector = new Vector2D(this.x+offset.x, this.y+offset.y);

		return newVector;
	};

	/**
	 * Method rotates this vector for angle value of input angle.
	 * 
	 * @param angle input angle
	 */
	public void rotate(double angle) {
		double alfa = getAngle(this) + angle;
		double magnitude = Math.sqrt(x * x + y * y);

		Vector2D newVector = fromMagnitudeAndAngle(magnitude, alfa);

		this.x = newVector.x;
		this.y = newVector.y;

	}

	/**
	 * Method returns rotation of this vector for input angle. This vector remains
	 * unchanged.
	 * 
	 * @param angle input angle
	 * @return rotated vector
	 */
	public Vector2D rotated(double angle) {
		double alfa = getAngle(this) + angle;
		double magnitude = Math.sqrt(x * x + y * y);

		Vector2D newVector = fromMagnitudeAndAngle(magnitude, alfa);
		
		return newVector;
	}

	/**
	 * Scale scales this vector for input value scaler.
	 * 
	 * @param scaler value of scaling
	 */
	public void scale(double scaler) {

		this.x = this.x * scaler;
		this.y = this.y * scaler;

	}

	/**
	 * Scaled scales this vector for input value scaler and returns new vector. This
	 * vector remains unchanged.
	 * 
	 * @param scaler value of scaling
	 * @return scaled vector
	 */
	public Vector2D scaled(double scaler) {

		Vector2D newVector = new Vector2D(this.x * scaler, this.y * scaler);

		return newVector;
	}

	/**
	 * Method copies this vector into a new one and returns it.
	 * 
	 * @return copy of this vector
	 */
	public Vector2D copy() {

		Vector2D newVector = new Vector2D(this.x, this.y);

		return newVector;

	};

	/**
	 * Method getAngle is a helper method that calculates and returns angle of input
	 * vector
	 * 
	 * @param vector input vector
	 * @return angle of input vector
	 */
	private double getAngle(Vector2D helper) {
		if (helper.x == 0 && helper.y == 0) {
			return 0;
		}
		if (helper.x < 0) {
			return Math.atan(helper.y / helper.x) + Math.PI;
		}
		if (helper.x >= 0 && helper.y < 0) {
			return Math.atan(helper.y / helper.x) + 2 * Math.PI;
		}
		return Math.atan(helper.y / helper.x);
	}

	/**
	 * Method creates new Vector2D vector that starts in (0,0) and ends in the point
	 * calculated in this method from input magnitude and input angle.
	 * 
	 * @param magnitude input magnitude
	 * @param angle     input angle
	 * @return Vector2D vector with angle value of input angle and magnitude of
	 *         input magnitude that starts in (0,0)
	 */
	private static Vector2D fromMagnitudeAndAngle(double magnitude, double angle) {
		if (magnitude < 0) {
			throw new IllegalArgumentException("Magnitude must be a positive number.");
		}
		double xPart = Math.cos(angle) * magnitude;
		double yPart = Math.sin(angle) * magnitude;
		Vector2D newVector = new Vector2D(xPart, yPart);
		return newVector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vector2D))
			return false;

		Vector2D other = (Vector2D) obj;
		return Math.abs(this.x - other.x) < precision && Math.abs(this.y - other.y) < precision;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String newString = "";
		newString = "( " + round(x) + ", " + round(y) + " )";

		return newString;

	}

	/**
	 * Helper private method for rounding numbers before converting them to string.
	 */
	private double round(double number) {
		double broj = number * 10000;
		broj = Math.round(broj);
		broj = broj / 10000;
		return broj;

	}

}
