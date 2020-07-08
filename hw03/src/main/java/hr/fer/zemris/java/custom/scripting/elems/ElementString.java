package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class has property name, and getter method getValue()). Property is read-only, no setter is
 * provided.  Constructor is used for initialization.
 * 
 * @author antonija
 *
 */
public class ElementString extends Element{
	
	/**
	 * Private string variable value
	 */
	private String value;
	/**
	 * Public constructor initializes value of new instance of this Object
	 * @param Sting input
	 */
	public ElementString(String input) {
		this.value=input;
	}
	
	/**
	 * Getter method for value 
	 * @return String value
	 */
	public String getValue() {
		return this.value;
	}
	
	
	/**
	 * return string representation of value property
	 */
	@Override
	public String asText() {
		return value;
	}
	
	  @Override
	  public String toString() {
	    return value;
	  }
}
