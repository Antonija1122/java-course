package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Token class defines Objects with value and TokenType. It is used for parsing strings.
 * @author antonija
 *
 */
public class SmartScriptToken {
	/**
	 * type of token 
	 */
	private SmartScriptTokenType type; 
	/**
	 * Object value of token
	 */
	private Object value;
	
	
	/**
	 * Constructor sets this.value to input Object value and this.type to input TokenType type
	 * @param type input type
	 * @param value input value
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {

		this.type=type;
		this.value=value;
	
	}
	
	/**
	 * Method getValue() returns value of this token
	 * @return token value
	 */
	public Object getValue() {
		return value;
	
	}

	/**
	 * Method getType() returns TokenType of this token
	 * @return token type
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
	
	/**
	 * Help method that converts Token to String.
	 * @return String of token
	 */
	public String toString() {
		return "("+ type + ", " + value + ")";
	}
}
