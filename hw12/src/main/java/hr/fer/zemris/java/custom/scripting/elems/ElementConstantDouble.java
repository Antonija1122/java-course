package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class has property double value, and getter method getValue()). Property is read-only, no setter is
 * provided.  Constructor is used for initialization.
 * 
 * @author antonija
 *
 */
public class ElementConstantDouble extends Element{

	/**
	 * private property double value
	 */
	private double value;
	
	/**
	 * Public constructor initializes value of new instance of this Object
	 * @param double input
	 */
	public ElementConstantDouble(double input) {
		this.value=input;
	}
	
	
	/**
	 * Getter method for value 
	 * @return double value
	 */
	public double getValue() {
		return this.value;
	}
	
	
	/**
	 * return string representation of value property
	 */
	@Override
	public	String asText() {
		return Double.valueOf(value).toString();
	}
	
	  @Override
	  public String toString() {
	    return Double.valueOf(value).toString();
	  }
}
