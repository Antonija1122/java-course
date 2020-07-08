package hr.fer.zemris.java.hw05.db;

/**
 * Enumeration defines all token types for QueryToken 
 * @author antonija
 *
 */

public enum TokenType {
	
	/**
	 * operators for string: < , > , >= , <= , != , LIKE
	 */
	OPERATOR, 
	
	/**
	 * String values for firstName, lastName, jmbag
	 */
	STRING_LITERAL,
	
	/**
	 * String for names of variables : "firstName", "lastName", "jmbag" or some other variables
	 */
	STRING, 
	
	/**
	 * Logic operator and 
	 */
	AND,
	
	/**
	 * end of input string 
	 */
	EOF

}
