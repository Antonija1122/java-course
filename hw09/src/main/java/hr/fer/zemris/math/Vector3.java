package hr.fer.zemris.math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * This class represents 3D vector with values x, y and z.
 * @author antonija
 *
 */
public class Vector3 {

	/**
	 * Value of coordinate x
	 */
	private double x;
	/**
	 * Value of coordinate y
	 */
	private double y;
	/**
	 * Value of coordinate z
	 */
	private double z;

	/**
	 * Public constructor for setting initital values of coordinates for 3d vector
	 * @param x initial value of x
	 * @param y initial value of y
	 * @param z initial value of z
	 */
	public Vector3(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * This method calculates magnitude of this vector 
	 * @return magnitude of this vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * This vector calculates normalized vector of this vector.
	 * @return normalized vector with the same course as this vector
	 */
	public Vector3 normalized() {
		return new Vector3(x / this.norm(), y / this.norm(), z / this.norm());
	}

	/**
	 * This vector returns vector that is a sum of this vector and input vector other
	 * @param other input vector
	 * @return this + c
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}

	/**
	 * This vector returns vector that is a result of operation this-other
	 * @param other input vector
	 * @return this - c
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}

	/**
	 * this method returns the result of scalar multiplication of this vector and input vector
	 * @param other input vector
	 * @return scalar multiplication of this vector and input vector
	 */
	public double dot(Vector3 other) {
		return other.getX() * this.x + other.getY() * this.y + other.getZ() * this.z;

	}

	/**
	 * this method returns the result of vector multiplication of this vector and input vector
	 * @param other input vector
	 * @return vector multiplication of this vector and input vector
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(y*other.getZ()-z*other.getY(), z*other.getX()-x*other.getZ(), x*other.getY()-y*other.getX());
		}

	/**
	 * This method returns this vector scaled by input factor
	 * @param s input scaler factors
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * This method returns cosines of the angle between this vector and input vector
	 * @param other input vector
	 * @return cosines of the angle between this vector and input vector
	 */ 
	public double cosAngle(Vector3 other) {
		return this.dot(other)/(this.norm()*other.norm());
	}

	/**
	 * Getter method for x
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter method for y
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter method for z
	 * @return z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Getter method for array {x, y, z}
	 * @return double array {x, y, z}
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}

	/**
	 * This method returns string representation of this vector
	 * @return string representation of this vector
	 */
	public String toString() {
		Locale currentLocale = Locale.getDefault();
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		
		DecimalFormat df = new DecimalFormat("####0.000000", otherSymbols);

		
		String newString = "(" + df.format(x) + ", " + df.format(y) + ", " + df.format(z) + ")";
		return newString;
	}
	
	/**
	 * Helper private method for rounding numbers before converting them to string.
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}
