package hr.fer.zemris.java.hw03.prob1;

/**
 * Class lexer gives next token for every call of function nextToken(), and
 * gives last token for every call of function getToken(). TokenTypes are EOF,
 * WORD, NUMBER and SYMBOL. Class provides two work modes BASIC and EXTENDED.
 * 
 * @author antonija
 *
 */
public class Lexer {

	/**
	 * variable state determines work mode BASIC or EXTENDED
	 */
	private LexerState state;

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
	private int currentIndex; // indeks prvog neobrađenog znaka

	/**
	 * Constructor receives as an input string which is converted to Char Array
	 * data. Constructor also sets currentIndex at zero and sets default state at
	 * BASIC
	 * 
	 * @throws NullPointerException if input string is null
	 * @param text
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;

	}

	/**
	 * Method nextToken() generates next token depending on state value and returns
	 * token.
	 * 
	 * @throws LexerException() if input is illegal
	 * @return nextToken
	 */
	public Token nextToken() {
		String string = new String(data);
		Token newToken = null;

		if (token != null && token.getType() == TokenType.EOF) { // ako je zadnji bio EOF baci iznimku
			throw new LexerException("There are no more tokens left.");
		}

		// ako je prazan ulaz vrijednost je nula, type je EOF
		if (string.equals("")) {
			newToken = new Token(TokenType.EOF, null);
			this.token = newToken;
			return newToken;
		}

		skipBlanks(); // preskoci praznine

		// ako je ulaz samo praznine
		string = new String(data);
		if (currentIndex >= data.length) {
			newToken = new Token(TokenType.EOF, null);
			this.token = newToken;
			return newToken;
		}

		if (state == LexerState.BASIC) {
			newToken = basic();
			return newToken;
		} else {
			newToken = extended();
			return newToken;
		}

	}

	/**
	 * Method getToken() returns current token.
	 * 
	 * @return token
	 */
	public Token getToken() {
		return this.token;
	}

	/**
	 * Method setState(LexerState state) sets state value to input variable state.
	 * 
	 * @param state input value state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
	}

	/// pomocne metode

	/**
	 * Method generates and returns token by BASIC state rules.
	 * 
	 * @return next token
	 */
	private Token basic() {
		// ako je rijec
		skipBlanks();
		Token newToken;
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			newToken = isItAWord();
			return newToken;
		}

		// ako nije rijec provjeri jel broj
		if (Character.isDigit(data[currentIndex])) {
			newToken = isItANumber();
			return newToken;
		}

		// ako nije broj niti rijec onda je znak
		Character symbol = data[currentIndex];
		++currentIndex;
		newToken = new Token(TokenType.SYMBOL, symbol);
		this.token = newToken;
		return newToken;
	}

	/**
	 * Method generates and returns token by EXTENDED state rules.
	 * 
	 * @return next token
	 */
	private Token extended() {
		skipBlanks();
		Token newToken = null;
		String word = new String();

		if (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '\t'
				&& data[currentIndex] != '\r' && data[currentIndex] != '\n' && data[currentIndex] != '#') {

			while (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '\t'
					&& data[currentIndex] != '\r' && data[currentIndex] != '\n' && data[currentIndex] != '#') {

				word = word + data[currentIndex];
				++currentIndex;
			}
			newToken = new Token(TokenType.WORD, word);
			this.token = newToken;
			return newToken;

		} else if (data[currentIndex] == '#') {
			++currentIndex;
			newToken = new Token(TokenType.SYMBOL, '#');
			this.token = newToken;
			return newToken;
		}
		return newToken;

	}

	/**
	 * Method generates next token as a word and returns it.
	 * 
	 * @throws LexerException if input is illegal
	 * @return next token od TokenType WORD
	 */
	private Token isItAWord() {
		String word = new String();
		while (currentIndex < data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
			if (data[currentIndex] == '\\') {
				if (data.length == currentIndex + 1 || Character.isLetter(data[currentIndex + 1])) {
					throw new LexerException("Invalid escaping.");
				} else {
					word = word + data[currentIndex + 1];
					++currentIndex;
				}
			} else {
				word = word + data[currentIndex];
			}
			++currentIndex;
		}
		Token newToken = new Token(TokenType.WORD, word);
		this.token = newToken;
		return newToken;
	}

	/**
	 * Method generates next token as a number and returns it.
	 * 
	 * @throws LexerException if input number is illegal
	 * @return next token of TokenType NUMBER
	 */
	private Token isItANumber() {
		String number = new String();
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			number = number + data[currentIndex];
			++currentIndex;
		}
		try {
			Token newToken = new Token(TokenType.NUMBER, Long.parseLong(number));
			this.token = newToken;
			return newToken;
		} catch (IllegalArgumentException e) {

			throw new LexerException("Number format is illegal.");
		}
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