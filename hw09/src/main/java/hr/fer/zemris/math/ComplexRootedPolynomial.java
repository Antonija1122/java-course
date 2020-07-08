package hr.fer.zemris.math;

/**
 * This class represents polynomial for Complex numbers implemented with class
 * Complex. Polynom is represented in form:
 * z0*(z-z1)*(z-z2)*(z-z3)*(z-z4)...*(z-zn)
 * @author antonija
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * constant for this polynomial
	 */
	private Complex z0;

	/**
	 * roots of this instance of polynomial
	 */
	private Complex[] roots;

	/**
	 * Public constructor for instancing this class. Constructor initializes z0 and
	 * roots.
	 * 
	 * @param constant constant for this polynomial
	 * @param roots    array of Complex roots of this polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.z0 = constant;
		this.roots = roots;
	}

	/**
	 * Getter method for constant z0
	 * 
	 * @return Complex z0
	 */
	public Complex getZ0() {
		return z0;
	}

	/**
	 * Getter method for roots
	 * 
	 * @return Complex[] roots
	 */
	public Complex[] getRoots() {
		return roots;
	}

	/**
	 * This method computes polynomial value at given point z.
	 * 
	 * @param z input point for calculating value of this polynomial
	 * @return value of this polynomial in point z
	 */
	public Complex apply(Complex z) {
		Complex result = z0;
		for (int i = 0; i < this.roots.length; i++) {
			result = result.multiply(z.sub(this.roots[i]));
		}
		return result;
	}

	/**
	 * This method converts this representation to ComplexPolynomial type.
	 * 
	 * @return ComplexPolynomial of this instance of ComplexRootedPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial newPoly = new ComplexPolynomial(z0);
		Complex[] newFactors = new Complex[2];
		for (int i = 0; i < roots.length; i++) {
			newFactors[0] = roots[i].negate();
			newFactors[1] = Complex.ONE;
			newPoly = newPoly.multiply(new ComplexPolynomial(newFactors));
		}
		return newPoly;
	}

	/**
	 * This method returns string representation for this polynomial
	 */
	@Override
	public String toString() {
		String newString = z0.toString();

		for (int i = 0; i < this.roots.length; i++) {
			newString = newString + "*(z-" + roots[i].toString() + ")";
		}
		return newString;

	}

	/**
	 * This method finds index of closest root for given complex number z that is
	 * within treshold. If there is no such root, returns -1.
	 * 
	 * @param z        input Complex number
	 * @param treshold distance for searching closest root
	 * @return index of clossest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {

		int index = -1;
		double min = z.sub(roots[0]).module();
		double modul;
		for (int i = 0; i < this.roots.length; i++) {
			modul = z.sub(roots[i]).module();
			if (modul <= treshold && modul <= min) {
				index = i;
				min = modul;
			}
		}
		return index;

	}

}
