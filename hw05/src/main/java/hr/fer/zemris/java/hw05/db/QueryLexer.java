package hr.fer.zemris.java.hw05.db;

import java.util.List;

public class QueryLexer {

	private List<Token> list;

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

	public QueryLexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;

	}

	/**
	 * Method nextToken() generates next token depending on state value and returns
	 * token.
	 * 
	 * @throws IllegalArgumentException() if input is illegal
	 * @return nextToken
	 */
	public Token nextToken() {
		Token newToken = null;
		skipBlanks();
		String string = new String(data);

		if (token != null && token.getType() == TokenType.EOF) {
			throw new IllegalArgumentException("There are no more tokens.");
		}

		if (string.equals("")) {
			newToken = new Token(TokenType.EOF, null);
			this.token = newToken;
		}

		if (currentIndex >= data.length) {
			newToken = new Token(TokenType.EOF, null);
			this.token = newToken;

		} else {
			skipBlanks();
			getNextToken();

		}
		return token;

	}


	private void getNextToken() {

		String next = "";

		if (Character.isLetter(data[currentIndex])) {

			while (currentIndex < data.length && (Character.isLetter(data[currentIndex]))) {
				next = next + data[currentIndex];
				currentIndex++;
			}

			if (next.toLowerCase().equals("and")) {
				Token newToken = new Token(TokenType.AND, next);
				this.token = newToken;
			} else if (next.equals("LIKE")) {
				Token newToken = new Token(TokenType.OPERATOR, next);
				this.token = newToken;
			} else {
				Token newToken = new Token(TokenType.STRING, next);
				this.token = newToken;
			}
			return;
		} else

		if (data[currentIndex] == '\"') { // stringLiteral
			currentIndex++;
			while (currentIndex < data.length && data[currentIndex] != '\"') {

				next = next + data[currentIndex];
				currentIndex++;
			}
			++currentIndex;
			Token newToken = new Token(TokenType.STRING_LITERAL, next);
			this.token = newToken;
			return;
		} else

		// >, <, >=, <=, =, !=, LIKE-rijesen
		if (data[currentIndex] == '<' || data[currentIndex] == '>' || data[currentIndex] == '!') {

			if (currentIndex + 1 < data.length
					&& (data[currentIndex] == '>' || data[currentIndex] == '<' || data[currentIndex] == '!')
					&& data[currentIndex + 1] == '=') {
				next = next + data[currentIndex] + data[currentIndex + 1];
				Token newToken = new Token(TokenType.OPERATOR, next);
				currentIndex = currentIndex + 2;
				this.token = newToken;
				return;
			} else if (data[currentIndex] == '>' || data[currentIndex] == '<') {
				next = next + data[currentIndex];
				Token newToken = new Token(TokenType.OPERATOR, next);
				currentIndex = currentIndex + 1;
				this.token = newToken;
				return;
			} else {
				throw new IllegalArgumentException("Invalid operator input " + data[currentIndex]);
			}
		}

		else if (data[currentIndex] == '=') {
			next = next + data[currentIndex];
			Token newToken = new Token(TokenType.OPERATOR, next);
			currentIndex = currentIndex + 1;
			this.token = newToken;
			return;
		} else {
			throw new IllegalArgumentException("Invalid input " + data[currentIndex]);
		}
	}

	public Token getToken() {
		return this.token;
	}

	public List<Token> getListTokens() {
		return this.list;

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
