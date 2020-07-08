package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class has property name, and getter method getName()). Property is read-only, no setter is
 * provided.  Constructor is used for initialization.
 * 
 * @author antonija
 *
 */
public class ElementVariable extends Element {
	
	/**
	 * Private string variable name
	 */
	private String name;
	
	public ElementVariable(String input) {
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
	    return name;
	  }
}
