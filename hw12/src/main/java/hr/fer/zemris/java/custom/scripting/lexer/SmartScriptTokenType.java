package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration TokenType defines nine types of token VARIABLE, TEXT, TAG_NAME,
 * OPERATOR, CONSTANT_INTEGER, CONSTANT_DOUBLE, FUNCTION, EOF, STRING.
 * 
 * @author antonija
 *
 */
public enum SmartScriptTokenType {

	// tags =, for, end samo za for
	// variables A7_bb, counter, tmp_34; these are not valid: _a21, 32, 3s_ee
	//

	VARIABLE, TEXT, TAG_NAME, OPERATOR, CONSTANT_INTEGER, CONSTANT_DOUBLE, FUNCTION, EOF, STRING

}
