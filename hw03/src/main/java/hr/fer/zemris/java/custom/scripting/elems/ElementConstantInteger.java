package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class has property int value, and getter method getValue()). Property is read-only, no setter is
 * provided.  Constructor is used for initialization.
 * 
 * @author antonija
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * private property int value
	 */
	int value;
	
	/**
	 * Public constructor initializes value of new instance of this Object
	 * @param int input
	 */
	public ElementConstantInteger(int input) {
		this.value=input;
	}
	
	
	/**
	 * Getter method for value 
	 * @return int value
	 */
	int getValue() {
		return this.value;
	}
	
	
	/**
	 * return string representation of value property
	 */
	@Override
	String asText() {
		return Integer.valueOf(value).toString();
	}

	  @Override
	  public String toString() {
	    return Integer.valueOf(value).toString();
	  }

}
