package hr.fer.zemris.math;

/**
 * This class represents polynomial of complex number. In private array of
 * complex number every instance of this class stores factors for polynomial. If
 * factors are : z0, z1, z2...zn then polynomial is : z0 + z1*z^1 + z2*z^2 +
 * z3*z^3..... + zn*z^n
 * 
 * @author antonija
 *
 */
public class ComplexPolynomial {

	/**
	 * Private array of polynom factors that are Complex numbers
	 */
	Complex[] factors;

	/**
	 * Public constructor initializes array of factors
	 * 
	 * @param factors input array of factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * This method returns order of this polynomial ; eg. For (7+2i)z^3+2z^2+5z+1
	 * returns 3
	 * 
	 * @return order of this polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * This method computes a new polynomial this*p and returns it
	 * 
	 * @param p input polynomial
	 * @return this*p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int length = this.order() + p.order() + 1;
		Complex[] newRoots = new Complex[length];
		for (int i = 0; i < length; i++) {
			newRoots[i] = Complex.ZERO;
		}

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				newRoots[i + j] = newRoots[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(newRoots);
	}

	/**
	 * This method computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {

		Complex[] derive = new Complex[this.order()];
		for (int i = 0; i < this.order(); i++) {
			derive[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(derive);
	}

	/**
	 * This method computes polynomial value at given point z 
	 * @param z input complex number
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;

		for (int i = 0; i < factors.length; i++) {
			result = result.add(z.power(i).multiply(factors[i]));
		}

		return result;
	}

	
	/**
	 * 	This method returns string representation for this polynomial 
	 */
	@Override
	public String toString() {
		String newString = "";

		for (int i = factors.length - 1; i > 0; i--) {
			newString = newString + factors[i].toString() + "*z^" + i + "+";
		}
		// jos za nulu
		return newString + factors[0].toString();
	}

	public Complex[] getFactors() {
		return factors;
	}

}
