package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class has property name, and getter method getName()). Property is read-only, no setter is
 * provided.  Constructor is used for initialization.
 * 
 * @author antonija
 *
 */
public class ElementFunction extends Element {
	/**
	 * Private string variable name
	 */
	private String name;
	
	/**
	 * Public constructor initializes name of new instance of this Object
	 * @param String input
	 */
	public ElementFunction(String input) {
		this.name=input;
	}
	
	/**
	 * Getter method for name 
	 * @return String name
	 */
	String getName() {
		return this.name;
	}
	
	
	/**
	 * return string representation of name property
	 */
	@Override
	String asText() {
		return name;
	}
	
	@Override
	public String toString() {
	    return "@" + name  +" ";
	  }
}
