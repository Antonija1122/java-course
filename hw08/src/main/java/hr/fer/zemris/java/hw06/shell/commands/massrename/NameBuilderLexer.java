package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * This class represents lexer for NameBuilderParser. This lexer creates tokens:
 * STRING, EOF, NUMBER_1, NUMBER_2, TAG_OPEN, TAG_CLOSE.
 * 
 * @author antonija
 *
 */
public class NameBuilderLexer {

	/**
	 * variable state determines work mode TEXT, TAG
	 */
	private LexerState state = LexerState.TEXT;

	/**
	 * Input string
	 */
	private char[] data;
	/**
	 * current token
	 */
	private Token token;
	/**
	 * Index of first character of input string that is not converted to Token
	 */
	private int currentIndex;

	/**
	 * Constructor receives as an input string which is converted to Char Array
	 * data. Constructor also sets currentIndex at zero and sets default state at
	 * TEXT
	 * 
	 * @throws NullPointerException if input string is null
	 * @param text
	 */
	public NameBuilderLexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}

	/**
	 * This method finds next token and returns it
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		Token newToken = null;
		if (checkEOF()) {
			return this.token;
		}
		if (state == LexerState.TEXT) {
			skipBlanks();
			newToken = itIsText();
			return newToken;
		} else {
			newToken = getNextTagToken();
			return newToken;
		}

	}

	/**
	 * This method checks if next token is EOF.
	 * 
	 * @return true if next token is EOF, false otherwise
	 */
	private boolean checkEOF() {
		Token newToken = null;
		skipBlanks();
		String string = new String(data);

		if (token != null && token.getType() == TokenType.EOF) {
			throw new NameBuilderLexerException("There are no more tokens.");
		}
		if (string.equals("")) {
			newToken = new Token(TokenType.EOF, null);
			this.token = newToken;
			return true;
		}
		if (currentIndex >= data.length) {
			newToken = new Token(TokenType.EOF, null);
			this.token = newToken;
			return true;
		}
		return false;
	}

	/**
	 * This method finds next token for TAG state. It also checks if state of lexer
	 * has to be changed to TEXT.
	 * 
	 * @return next tag token
	 */
	private Token getNextTagToken() {
		Token newToken = null;
		String text = "";
		if (data[currentIndex] == '}') {
			state = LexerState.TEXT;
			newToken = new Token(TokenType.TAG_CLOSE, "}");
			currentIndex = currentIndex + 1;
			this.token = newToken;
			setState(LexerState.TEXT);
			return newToken;
		} else if (data[currentIndex] == ',') {
			currentIndex++;
			while (currentIndex < data.length && data[currentIndex] != '}') {
				text = text + data[currentIndex];
				currentIndex++;
			}
			try {
				text = text.trim();
				newToken = new Token(TokenType.NUMBER_2, text);
				this.token = newToken;
				return newToken;
			} catch (NumberFormatException e) {
				throw new NameBuilderLexerException("Illegal input for NUMBER_2.");
			}
		} else {
			while (currentIndex < data.length && data[currentIndex] != '}' && data[currentIndex] != ',') {
				text = text + data[currentIndex];
				currentIndex++;
			}
			try {
				text = text.trim();
				newToken = new Token(TokenType.NUMBER_1, Integer.parseInt(text));
				this.token = newToken;
				return newToken;
			} catch (NumberFormatException e) {
				throw new NameBuilderLexerException("Illegal input for NUMBER_1.");
			}
		}

	}

	/**
	 * This method finds next token for TEXT state. It also checks if state of lexer
	 * has to be changed to TAG.
	 * 
	 * @return next tag token
	 */
	private Token itIsText() {
		Token newToken = null;
		String text = "";
		if (currentIndex + 1 < data.length && data[currentIndex] == '$' && data[currentIndex + 1] == '{') {
			state = LexerState.TAG;
			newToken = new Token(TokenType.TAG_OPEN, "${");
			currentIndex = currentIndex + 2;
			this.token = newToken;
			setState(LexerState.TAG);
			return newToken;
		} else {
			while (currentIndex + 1 < data.length && (data[currentIndex] != '$' && data[currentIndex + 1] != '{')) {
				text = text + data[currentIndex];
				currentIndex++;
			}
			if (currentIndex + 1 == data.length) { // ukljuci i zadnji znak
				text = text + data[currentIndex];
				currentIndex++;
			}
			newToken = new Token(TokenType.STRING, text);
			this.token = newToken;
			return newToken;
		}
	}

	/**
	 * This method changes current lexer state.
	 * 
	 * @param state new lexer state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
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
