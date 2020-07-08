package hr.fer.zemris.java.hw03.prob1;
/**
 * Token class defines Objects with value and TokenType.
 * @author antonija
 *
 */
public class Token {
	
	
	/**
	 * type of token 
	 */
	private TokenType type; 
	/**
	 * Object value of token
	 */
	private Object value;
	
	
	/**
	 * Constructor sets this.value to input Object value and this.type to input TokenType type
	 * @param type input type
	 * @param value input value
	 */
	public Token(TokenType type, Object value) {

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
	public TokenType getType() {
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

