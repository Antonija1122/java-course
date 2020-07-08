package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class SmartScriptLexer gives next token for every call of function
 * nextToken(), and gives last token for every call of function getToken().
 * TokenTypes are VARIABLE, TEXT, TAG_NAME, OPERATOR, CONSTANT_INTEGER,
 * CONSTANT_DOUBLE, FUNCTION, EOF, STRING. Class provides two working modes TAG, TEXT.
 * 
 * @author antonija
 *
 */
public class SmartScriptLexer {
	
	

	/**
	 * variable state determines work mode TEXT, TAG
	 */
	private SmartScriptLexerState state = SmartScriptLexerState.TEXT;

	/**
	 * Input string
	 */
	private char[] data;
	/**
	 * current token
	 */
	private SmartScriptToken token;
	/**
	 * Index of first character of input string that is not converted to Token
	 */
	private int currentIndex; 
	
	/**
	 * Helper value for checking if all tags are closed 
	 */
	private int openedTags=0;
	/**
	 * Helper value for checking if all tags are closed 
	 */
	private int closedTags=0;
	/**
	 * Constructor receives as an input string which is converted to Char Array
	 * data. Constructor also sets currentIndex at zero and sets default state at
	 * TEXT
	 * 
	 * @throws NullPointerException if input string is null
	 * @param text
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
//		if(data[0]=='{') { state=SmartScriptLexerState.TAG; }
//		else {this.state=SmartScriptLexerState.TEXT;}
	}

	/**
	 * Method nextToken() generates next token depending on state value and returns
	 * token.
	 * 
	 * @throws SmartScriptLexerException() if input is illegal
	 * @return nextToken
	 */
	public SmartScriptToken nextToken() {
		SmartScriptToken newToken = null;
		skipBlanks();
		String string = new String(data);

		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("There are no more tokens.");
		}
		if (string.equals("")) {
			newToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			this.token = newToken;
			return newToken;
		}
		if (currentIndex >= data.length) {
			newToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			this.token = newToken;
			return newToken;
		}
		if (state == SmartScriptLexerState.TEXT) {
			skipBlanks();
			newToken = itIsText(); 
			return newToken;
		} else {
			skipBlanks();
			if (data[currentIndex] == '{') {
				newToken = getTagName();
				return newToken;
			} else {
				newToken = getNextTagToken();
				return newToken;
			}
		}
	}

	// POMOCNE

	// ako je tag uzmi ime

	/**
	 * If state is TAG function getTagName() returns name of current TAG
	 * 
	 * @return next token that is tag name
	 * @throws SmartScriptLexerException if name input is illegal
	 */
	private SmartScriptToken getTagName() {
		SmartScriptToken newToken = null;
		String tagName = "";
		if (currentIndex + 1 >= data.length || data[currentIndex] != '{' || data[currentIndex + 1] != '$') {
			throw new SmartScriptLexerException("invalid tag begining {$.");
		}
		currentIndex = currentIndex + 2;
		skipBlanks();
		if (data[currentIndex] == '=') {
			tagName += data[currentIndex];
			currentIndex = currentIndex + 1;
		} else if (Character.isLetter(data[currentIndex])) {
			while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
					|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
				tagName = tagName + data[currentIndex];
				currentIndex++;
			}
			// newToken = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, tagName);
			newToken = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, new ElementString(tagName.toUpperCase()));
			this.token = newToken;
			openedTags++;
			return newToken;
		} else {
			throw new SmartScriptLexerException("invalid tag name.");
		}

		// newToken = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, tagName);
		newToken = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, new ElementString(tagName.toUpperCase()));
		this.token = newToken;
		openedTags++;
		return newToken;
	}

	// next tag token

	/**
	 * Method generates and returns new token if the state is TAG. Method also
	 * checks if the next state is NEXT. Possible TokenTypes are VARIABLE, TAG_NAME,
	 * OPERATOR, CONSTANT_INTEGER, CONSTANT_DOUBLE, FUNCTION, EOF, STRING
	 * 
	 * @return next token
	 */
	private SmartScriptToken getNextTagToken() {
		skipBlanks();
		SmartScriptToken newToken = null;
		String next = "";

		if (currentIndex + 1 < data.length && data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			state = SmartScriptLexerState.TEXT;
			closedTags++;
			currentIndex = currentIndex + 2;

			newToken = nextToken(); /// ovdje
			this.token = newToken;
			return newToken;

		} else if (Character.isLetter(data[currentIndex])) { // ako je varijabla

			while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
					|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
				next = next + data[currentIndex];
				currentIndex++;
			}
			// newToken = new SmartScriptToken(SmartScriptTokenType.VARIABLE, next);
			newToken = new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable(next));
			this.token = newToken;
			return newToken;
		} else if (data[currentIndex] == '-' || Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
			// ako je broj
			if (data[currentIndex] == '-') {
				if ((currentIndex + 1 == data.length || !Character.isDigit(data[currentIndex + 1]))) {
					// newToken = new SmartScriptToken(SmartScriptTokenType.OPERATOR, "-");
					newToken = new SmartScriptToken(SmartScriptTokenType.OPERATOR, new ElementOperator("-"));
					this.token = newToken;
					currentIndex++;
					return newToken;
				} else {
					next = next + data[currentIndex];
					currentIndex++;
				}
			}
			while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
				next = next + data[currentIndex];
				currentIndex++;
			}
			try {
				int integer = Integer.parseInt(next);
				// newToken = new SmartScriptToken(SmartScriptTokenType.CONSTANT_INTEGER,
				// integer);
				newToken = new SmartScriptToken(SmartScriptTokenType.CONSTANT_INTEGER,
						new ElementConstantInteger(integer));
				this.token = newToken;
				return newToken;
			} catch (IllegalArgumentException e) {
				try {
					double d = Double.parseDouble(next);
					// newToken = new SmartScriptToken(SmartScriptTokenType.CONSTANT_DOUBLE, d);
					newToken = new SmartScriptToken(SmartScriptTokenType.CONSTANT_DOUBLE, new ElementConstantDouble(d));
					this.token = newToken;
					return newToken;
				} catch (IllegalArgumentException e2) {
					throw new SmartScriptLexerException("Illegal number input");
				}
			}
		} else if (data[currentIndex] == '+' || data[currentIndex] == '*' || data[currentIndex] == '/'
				|| data[currentIndex] == '^') {
			next = next + data[currentIndex];
			currentIndex++;
			// newToken = new SmartScriptToken(SmartScriptTokenType.OPERATOR, next);
			newToken = new SmartScriptToken(SmartScriptTokenType.OPERATOR, new ElementOperator(next));

			this.token = newToken;
			return newToken;
		} else if (data[currentIndex] == '@') { // FUNKCIJE
			currentIndex++;
			if (Character.isLetter(data[currentIndex])) {
				while (currentIndex < data.length
						&& (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
								|| data[currentIndex] == '_' || data[currentIndex] == '0')) {
					next = next + data[currentIndex];
					currentIndex++;
				}
				// newToken = new SmartScriptToken(SmartScriptTokenType.FUNCTION, next);
				newToken = new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction(next));

				this.token = newToken;
				return newToken;
			} else {
				throw new SmartScriptLexerException("Illegal function input");
			}
		} else if (data[currentIndex] == '"') { // string
			currentIndex++;
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				if (data[currentIndex] == '\\') {
					if (data[currentIndex + 1] != '"' && data[currentIndex + 1] != '\\'
							&& data[currentIndex + 1] != '\r' && data[currentIndex + 1] != '\t'
							&& data[currentIndex + 1] != '\n') {
						throw new SmartScriptLexerException("Invalid escaping.");
					} else {
						next = next + data[currentIndex + 1];
						++currentIndex;
					}
				} else {
					next = next + data[currentIndex];
				}
				++currentIndex;
			}
			++currentIndex;
			// newToken = new SmartScriptToken(SmartScriptTokenType.STRING, next);
			newToken = new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString(next));
			this.token = newToken;
			return newToken;
		} else {
			throw new SmartScriptLexerException("Illegal input -> " + data[currentIndex] + currentIndex);
		}

	}

	/**
	 * Method generates and returns new token if the state is TEXT. Method also
	 * checks if the next state is TAG. Possible TokenTypes are TEXT
	 * 
	 * @return next token
	 */
	private SmartScriptToken itIsText() {
		SmartScriptToken newToken = null;
		String text = "";
		if (currentIndex + 1 < data.length && data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			state = SmartScriptLexerState.TAG;
			newToken = getTagName();
			this.token = newToken;
			return newToken;
		}
		while (currentIndex + 1 < data.length && (data[currentIndex] != '{' || data[currentIndex + 1] != '$')) {
			if (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] != '{' && data[currentIndex + 1] != '\\' && data[currentIndex + 1] != '\r'
						&& data[currentIndex + 1] != '\t' && data[currentIndex + 1] != '\n') {
					throw new SmartScriptLexerException("Invalid escaping.");
				} else {
					text = text + data[currentIndex + 1];
					++currentIndex;
				}
			} else {
				text = text + data[currentIndex];
			}
			++currentIndex;
		}
		if (currentIndex == data.length - 1) {
			text = text + data[currentIndex]; // ovo zbog zadnjeg znaka jer while ne obuhvati zadnji znak
			++currentIndex;
		}
		this.state = SmartScriptLexerState.TAG;
		// newToken = new SmartScriptToken(SmartScriptTokenType.TEXT, text);
		newToken = new SmartScriptToken(SmartScriptTokenType.TEXT, new ElementString(text));
		this.token = newToken;
		return newToken;
	}
	
	

	/**
	 * Method setState(LexerState state) sets state value to input variable state.
	 * 
	 * @param state input value state
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
	}

	/**
	 * Method getToken() returns current token.
	 * 
	 * @return token
	 */
	public SmartScriptToken getToken() {
		return this.token;
	}
	
	/**
	 * Method returns number of open tags
	 * @return
	 */
	public int getOpenedTags() {
		return openedTags;
	}
	
	/**
	 * Method returns number of closed  tags
	 * @return
	 */
	public int getClosedTags() {
		return closedTags;
	}
	
	

	/**
	 * Method skips all empty spaces before next token.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

}
