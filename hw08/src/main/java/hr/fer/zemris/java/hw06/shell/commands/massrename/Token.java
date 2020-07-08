package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * This class represents instances of tokens for NameBuilderLexer and NameBuilderParser
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
	 * Method returns string representation of token.
	 * @return String of token
	 */
	public String toString() {
		return "("+ type + ", " + value + ")";
	}

}
