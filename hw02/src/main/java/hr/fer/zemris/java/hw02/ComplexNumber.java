package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * ComplexNumber is a class that implements methods for operating with complex
 * numbers. Class contains private variables that represent real and imaginary
 * part of complex numbers.
 * 
 * @author antonija
 *
 */
public class ComplexNumber {
	
	
	private static double precision = 0.000001;

	private double real;
	private double imaginary;

	/**
	 * Public constructor which accepts two arguments: real part and imaginary part
	 * and creates instance od ComplexNumber when called
	 */
	public ComplexNumber(double real, double imginary) {
		this.real = real;
		this.imaginary = imginary;
	}

	/**
	 * Public static factory method fromReal returns new instance of ComplexNumber
	 * with real part of input real and imaginary part equal to zero.
	 * 
	 * @param real input argument
	 * @return new intance of ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		ComplexNumber newComplexNumber = new ComplexNumber(real, 0);
		return newComplexNumber;
	}

	/**
	 * Public static factory method fromImaginary returns new instance of
	 * ComplexNumber with imaginary part of input imaginary and real part equal to
	 * zero.
	 * 
	 * @param real input argument
	 * @return new intance of ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		ComplexNumber newComplexNumber = new ComplexNumber(0, imaginary);
		return newComplexNumber;
	}

	/**
	 * Public static factory method fromMagnitudeAndAngle returns new instance of
	 * ComplexNumber with imaginary part and zero part calculated from input values
	 * of angle and magnitude.
	 * 
	 * @param magnitude input argument
	 * @param angle     input argument
	 * @return new intance of ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if (magnitude < 0) {
			throw new IllegalArgumentException("Magnitude must be a positive number.");
		}
		double real = Math.cos(angle) * magnitude;
		double imaginary = Math.sin(angle) * magnitude;
		ComplexNumber newComplexNumber = new ComplexNumber(real, imaginary);
		return newComplexNumber;
	}

	/**
	 * Method returns real part of complex number
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return this.real;
	}

	/**
	 * Method returns imaginary part of complex number
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Method returns magnitude od complex number calculated from imaginary and real
	 * part of complex number
	 * 
	 * @return magnitude part of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Method returns angle od complex number calculated from imaginary and real
	 * part of complex number
	 * 
	 * @return angle part of complex number
	 */
	public double getAngle() {
		if(real==0 && imaginary==0) {
			return 0;
		}
		if (real < 0) {
			return Math.atan(imaginary / real) + Math.PI;
		}
		if(real >=0  && imaginary<0) {
			return Math.atan(imaginary / real) + 2*Math.PI;
		}
		return Math.atan(imaginary / real);
	}
	
	

	/**
	 * Method sums real and imaginary parts of input ComplexNumber and this
	 * ComplexNumber
	 * 
	 * @param c input ComplexNumber
	 * @return new ComplexNumber that is sum of two input ComplexNumbers
	 */
	public ComplexNumber add(ComplexNumber c) {
		ComplexNumber newComplexNumber = new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
		return newComplexNumber;
	}

	/**
	 * Method subs real and imaginary parts of input ComplexNumber and this
	 * ComplexNumber and returns the result ComplexNumber
	 * 
	 * @param c input ComplexNumber
	 * @return new ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		ComplexNumber newComplexNumber = new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
		return newComplexNumber;
	}

	/**
	 * Method multiplies complex numbers and returns the result
	 * 
	 * @param c
	 * @return result of multiplying two input complex numbers
	 */
	public ComplexNumber mul(ComplexNumber c) {

		ComplexNumber newComplexNumber = new ComplexNumber(-this.imaginary * c.imaginary + this.real * c.real,
				this.imaginary * c.real + this.real * c.imaginary);
		return newComplexNumber;
	}

	/**
	 * Method divides complex numbers and returns the result
	 * 
	 * @param c
	 * @return result of dividing two input complex numbers
	 */
	public ComplexNumber div(ComplexNumber c) {

		ComplexNumber conugate = new ComplexNumber(c.real, -c.imaginary);

		ComplexNumber numerator = this.mul(conugate);
		ComplexNumber denominator = c.mul(conugate);

		// denominator is now real number, imainary part is 0

		ComplexNumber newComplexNumber = new ComplexNumber(numerator.real / denominator.real,
				numerator.imaginary / denominator.real);
		return newComplexNumber;
	}

	/**
	 * Method uses DeMoivre's Theorem and calculates power(ComplexNumber, n) using
	 * formula: [r(cos θ + j sin θ)]^n = r^n*(cos nθ + j sin nθ)
	 * 
	 * @throws IllegalArgumentException for ilegal input
	 * @param n power value
	 * @return result of operation power(ComplexNumber, n)
	 */
	public ComplexNumber power(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Power must be greater than zero.");
		}
		double powerMagnitude = Math.pow(this.getMagnitude(), n);
		ComplexNumber newComplexNumber = fromMagnitudeAndAngle(powerMagnitude, this.getAngle() * n);

		return newComplexNumber;
	}

	/**
	 * Method uses DeMoivre's Theorem and calculates root(ComplexNumber, n) using
	 * formula: [r(cos θ + j sin θ)]^n = r^n*(cos nθ + j sin nθ) ; (r∠θ)^n = r^n
	 * ∠nθ,
	 * 
	 * @throws IllegalArgumentException for ilegal input
	 * @param n root value
	 * @return result of operation root(ComplexNumber, n)
	 */
	public ComplexNumber[] root(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("root must be greater or equal to 0.");
		}
		ComplexNumber[] rootComplexNumbers = new ComplexNumber[n];

		double rootMagnitude = Math.pow(this.getMagnitude(), 1.0 / n);
		double rootAngleSparse = Math.toRadians(360 / n);// results will be distanced by 360/n degrees

		for (int i = 0; i < n; i++) {
			ComplexNumber newComplexNumber = fromMagnitudeAndAngle(rootMagnitude,
					this.getAngle() / n + i * rootAngleSparse);
			rootComplexNumbers[i] = newComplexNumber;
		}
		return rootComplexNumbers;
	}

	/**
	 * Method that converts ComplexNumber real and imaginary values to string;
	 * 
	 * @return String of ComplexNumber
	 */
	public String toString() {
		if (this.imaginary < 0) {
			String string = Double.toString(this.real) + Double.toString(this.imaginary) + "i";
			return string;

		} else {
			String string = Double.toString(this.real) + "+" + Double.toString(this.imaginary) + "i";
			return string;
		}
	}

	/**
	 * Method takes input string and convert it's values to real and imaginary parts
	 * and creates new ComplexNumber.
	 * If input is illegal exception is thrown 
	 * @throws IllegalArgumentException, NullPointerException, NumberFormatException for illegal input
	 * @param s input string 
	 * @return new ComplexNumber 
	 */
	public static ComplexNumber parse(String s) {
		String[] realAndImaginary = new String[2];
		realAndImaginary = getNumbers(s);

		try {
			realAndImaginary = getNumbers(s);
		} catch (NumberFormatException ignore) {
			System.out.println("Invalid Input1");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Invalid input2.");
		}
		ComplexNumber newComplexNumber = new ComplexNumber(Double.parseDouble(realAndImaginary[0]),
				Double.parseDouble(realAndImaginary[1]));
		return newComplexNumber;

	}

	/**
	 * Method is used to help convert input string in ComplexNumber.
	 * Method is used by public method parse(String s). 
	 * @param s input string
	 * @return String[] string array with string values of real and imaginary parts of ComplexNumber
	 */
	private static String[] getNumbers(String s) {

		StringBuilder sb = new StringBuilder(s);
		String[] realAndImaginary = new String[2];

		String realString = new String();
		String imagString = new String();
		String operator1 = new String();
		String operator2 = new String();

		// ako je zadnje i mora postojat imaginarni dio ako ne idem dolje u zadnji else
		// i parsiram u realni
		if (s.charAt(s.length() - 1) == 'i') {
			sb.deleteCharAt(s.length() - 1);
			// ako je unesen samo i imaginarni dio je jedan a realni 0
			if (sb.length() == 0) {
				realAndImaginary[0] = "0";
				realAndImaginary[1] = "1";
				return realAndImaginary;
			}
			// ako je prvi znak operator spremi znak i provjeri za +i -i unose
			if (s.charAt(0) == '-' || s.charAt(0) == '+') {
				operator1 += s.charAt(0);
				sb = sb.deleteCharAt(0);
				// ovo je za unose -i +i
				if (sb.length() == 0) {
					imagString = operator1 + "1";
					realAndImaginary[0] = "0";
					realAndImaginary[1] = imagString.toString();
					return realAndImaginary;
				}
			}
			// provjeri dali je izmedu znaka i i-a samo imaginarni dio ako nije ide u catch
			try {
				// oblici -3.34i +3.34i
				double tmp = Double.parseDouble(sb.toString());
				imagString = sb.toString();
				realAndImaginary[0] = "0";
				realAndImaginary[1] = operator1 + Double.toString(tmp);
				return realAndImaginary;

			} catch (NumberFormatException e) {

				// u catchu smo što znaci da moraju postojat barem jos jedan operator osim
				// -3.34i ili +3.34i
				try {
					for (int i = 0; i < sb.length(); i++) {
						if (sb.charAt(i) == '-' || sb.charAt(i) == '+') {

							// postoji operator sto znaci da razdvaja dva broja razdvoji dva broja i
							// operator
							realString = operator1 + sb.substring(0, i);
							operator2 += sb.charAt(i); // pisalo
							imagString = operator2 + sb.substring(i + 1);

							if (imagString.equals(operator2)) {
								imagString += "1"; // u slucaju da je -broj+i ili -broj-i
							}
							// provjera mogu li se parsirati
							double realPart = Double.parseDouble(realString);
							double imagPart = Double.parseDouble(imagString);
							realAndImaginary[0] = Double.toString(realPart);// realString;
							realAndImaginary[1] = Double.toString(imagPart);
							return realAndImaginary;
						}

					}
					// ako try nije uspio znaci da je krivi upis
				} finally {

				}
			}
			// ako postoji samo realni dio inace ce bacit iznimku koju cemo uhvatit u drugoj
			// f-ji
		} else {
			double realPart = Double.parseDouble(s);
			realString = Double.toString(realPart);
			realAndImaginary[0] = realString;
			realAndImaginary[1] = "0";
			return realAndImaginary;

		}

		if (realAndImaginary[0] == null || realAndImaginary[0] == null) {
			throw new IllegalArgumentException("Invalid input.3");
		}
		return realAndImaginary;

	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(real, imaginary);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ComplexNumber))
			return false;
		
		ComplexNumber other = (ComplexNumber) obj;
		return Math.abs(this.real - other.real) < precision
				&& Math.abs(this.imaginary - other.imaginary) < precision;
	} 
	
	
	
	
	

}
