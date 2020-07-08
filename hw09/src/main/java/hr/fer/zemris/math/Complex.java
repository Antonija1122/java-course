package hr.fer.zemris.math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class represents complex number with values double re and double im.
 * 
 * @author antonija
 *
 */
public class Complex {

	/**
	 * Real part of this complex number
	 */
	private double re;
	/**
	 * Imaginary part of this complex number
	 */
	private double im;

	/**
	 * Complex number (0,0).
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Complex number (1,0).
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Complex number (-1,0).
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Complex number (0,1).
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Complex number (0,-1).
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Public constructor initializes instance of this class with input real and
	 * imaginary values
	 * 
	 * @param re input real value for Complex number
	 * @param im input imaginary value for Complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Getter method for real part of this complex number
	 * 
	 * @return
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter method for imaginary part of this complex number
	 * 
	 * @return im
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Getter method for length of this coplex number
	 * 
	 * @return module of this complex number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);

	}

	/**
	 * This method returns new instance of complex number with value: this*c
	 * 
	 * @param c input multiplier complex number
	 * @return Complex this*c
	 */
	public Complex multiply(Complex c) {
		return new Complex(-this.im * c.im + this.re * c.re, this.im * c.re + this.re * c.im);
	}

	/**
	 * This method returns new instance of complex number with value: this/c
	 * 
	 * @param c input divider complex number
	 * @return Complex this/c
	 */
	public Complex divide(Complex c) {

		Complex conugate = new Complex(c.re, -c.im);

		Complex numerator = this.multiply(conugate);
		Complex denominator = c.multiply(conugate);

		return new Complex(numerator.re / denominator.re, numerator.im / denominator.re);
	}

	/**
	 * This method returns new instance of complex number with value: this+c
	 * 
	 * @param c input increment complex number
	 * @return Complex this+c
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * This method returns new instance of complex number with value: this-c
	 * 
	 * @param c input decrement complex number
	 * @return Complex this-c
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * This method returns new instance of complex number with value: -this
	 * 
	 * @return Complex -this
	 */
	public Complex negate() {
		return new Complex(-re, -im);

	}

	/**
	 * This method returns new instance of complex number with value: this^n
	 * 
	 * @param n input exponent
	 * @return Complex this^n
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power must be greater than zero.");
		} else if (n == 0) {
			return Complex.ONE;
		}
		double magnitude = Math.pow(this.module(), n);

		double real = Math.cos(this.getAngle() * n) * magnitude;
		double imaginary = Math.sin(this.getAngle() * n) * magnitude;
		Complex newComplexNumber = new Complex(real, imaginary);

		return newComplexNumber;
	}

	/**
	 * This method returns list of n new instances of complex numbers with values
	 * that are n-th roots of this instance of Complex number
	 * 
	 * @param n input root
	 * @return List<Complex> roots 
	 */
	public List<Complex> root(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("root must be greater or equal to 0.");
		}
		List<Complex> rootComplexNumbers = new ArrayList<Complex>();

		double rootMagnitude = Math.pow(this.module(), 1.0 / n);
		double rootAngleSparse = Math.toRadians(360 / n);// results will be distanced by 360/n degrees

		for (int i = 0; i < n; i++) {

			double real = Math.cos(this.getAngle() / n + i * rootAngleSparse) * rootMagnitude;
			double imaginary = Math.sin(this.getAngle() / n + i * rootAngleSparse) * rootMagnitude;
			rootComplexNumbers.add(new Complex(real, imaginary));
		}
		return rootComplexNumbers;

	}

	/**
	 * This method returns string representation of this complex number in form: (real, iimaginary)
	 */
	@Override
	public String toString() {
		Locale currentLocale = Locale.getDefault();
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');

		DecimalFormat df = new DecimalFormat("####0.0", otherSymbols);

		String newString = "(" + re;
		if (im < 0) {
			newString = newString + "-i" + df.format(Math.abs(im)) + ")";
		} else {
			newString = newString + "+i" + df.format(im) + ")";
		}
		return newString;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(im) != Double.doubleToLongBits(other.im))
			return false;
		if (Double.doubleToLongBits(re) != Double.doubleToLongBits(other.re))
			return false;
		return true;
	}

	/**
	 * This method returns angle of this complex number.
	 * @return angle in radians
	 */
	private double getAngle() {
		if (re == 0 && im == 0) {
			return 0;
		}
		if (re < 0) {
			return Math.atan(im / re) + Math.PI;
		}
		if (re >= 0 && im < 0) {
			return Math.atan(im / re) + 2 * Math.PI;
		}
		return Math.atan(im / re);
	}
}
