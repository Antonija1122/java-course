package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class has property symbol, and getter method geSymbol()). Property is read-only, no setter is
 * provided.  Constructor is used for initialization.
 * 
 * @author antonija
 *
 */
public class ElementOperator extends Element{

	
	/**
	 * Private string variable symbol
	 */
	private String symbol;
	
	/**
	 * Public constructor initializes symbol of new instance of this Object
	 * @param String input
	 */
	public ElementOperator(String input) {
		this.symbol=input;
	}
	
	/**
	 * Getter method for symbol 
	 * @return String symbol
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	
	/**
	 * return string representation of symbol property
	 */
	@Override
	public	String asText() {
		return symbol;
	}
	
	  @Override
	  public String toString() {
	    return symbol;
	  }
}
