package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private List<Token> list;

	private QueryLexer lexer;

	private Token token;

	public QueryParser(String text) {
		this.lexer = new QueryLexer(text);
		list = new ArrayList<>();
		createListForQuery();
	}

	private void createListForQuery() {

		token = lexer.nextToken();

		while (token.getType() != TokenType.EOF) {

			list.add(token);
			token = lexer.nextToken();
		}
		list.add(token); // dodaje EOF
	}

//	 => Method must return true if query was of of the form jmbag="xxx" (i.e. it must have only one
//	comparison, on attribute jmbag, and operator must be equals). We will call queries of this form direct
//	queries.

	boolean isDirectQuery() {

		token = list.get(0);

		if (token.getType() == TokenType.EOF)
			return false;
		if (token.getType() != TokenType.STRING)
			return false;
		if (!token.getValue().equals("jmbag"))
			return false;

		token = list.get(1);

		if (token.getType() != TokenType.OPERATOR)
			return false;
		if (!token.getValue().equals("="))
			return false;

		token = list.get(2);

		if (token.getType() != TokenType.STRING_LITERAL)
			return false;

		token = list.get(3);

		if (token.getType() != TokenType.EOF)
			return false;

		return true;

	};

//	 => Method must return the string ("xxx" in previous example) which was given in equality comparison in
//	direct query. If the query was not a direct one, method must throw IllegalStateException.

	String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("This query is not direct query");
		}

		String jmbag = (String) list.get(2).getValue();

		return jmbag;
	};

//	 => For all queries, this method must return a list of conditional expressions from query; please observe that
//	for direct queries this list will have only one element.

	List<ConditionalExpression> getQuery() {

		Token token;
		Token tokenString;
		Token tokenOperator;
		Token tokenStrLit;

		List<ConditionalExpression> list = new ArrayList<>();

		if (isDirectQuery()) {
			ConditionalExpression newCond = new ConditionalExpression(FieldValueGetters.JMBAG, getQueriedJMBAG(),
					ComparisonOperators.EQUALS);
			list.add(newCond);
			return list;
		} else {
			int i = 0;
			token = this.list.get(i);

			if (token.getType() != TokenType.STRING) {
				throw new IllegalArgumentException(
						"Illegal input. First string is expected to be jmbg, firstName or lastName");
			}

			while (token.getType() != TokenType.EOF) {

				if (token.getType() == TokenType.STRING) {
					tokenString = token;
					i++;
					token = this.list.get(i);
					if (token.getType() == TokenType.OPERATOR) {
						tokenOperator = token;
						i++;
						token = this.list.get(i);
						if (token.getType() == TokenType.STRING_LITERAL) {
							tokenStrLit = token;
							i++;
							ConditionalExpression newCond = createNextConditionalExpression(tokenString, tokenOperator,
									tokenStrLit);
							list.add(newCond);
							token = this.list.get(i);

						} else {
							throw new IllegalArgumentException("Illegal input. STRING_LITERAL");
						}
					} else {
						throw new IllegalArgumentException("Illegal input.");
					}

				} else {
					if (token.getType() == TokenType.AND) {
						
						i++;
						token = this.list.get(i);

						if (token.getType() == TokenType.EOF) {
							throw new IllegalArgumentException(
									"Illegal input. After logic operator \"and\" new conditional expresion is expected");

						}
					}
				}
			}

		}

		return list;
	}

	private ConditionalExpression createNextConditionalExpression(Token tokenString, Token tokenOperator,
			Token tokenStrLit) {

		ConditionalExpression newCond;

		IComparisonOperator c;

		if (tokenOperator.getValue().equals("=")) {
			c = ComparisonOperators.EQUALS;
		} else if (tokenOperator.getValue().equals("<")) {
			c = ComparisonOperators.LESS;
		} else if (tokenOperator.getValue().equals(">")) {
			c = ComparisonOperators.GREATER;
		} else if (tokenOperator.getValue().equals("<=")) {
			c = ComparisonOperators.LESS_OR_EQUALS;
		} else if (tokenOperator.getValue().equals(">=")) {
			c = ComparisonOperators.GREATER_OR_EQUALS;
		} else if (tokenOperator.getValue().equals("LIKE")) {
			c = ComparisonOperators.LIKE;
		} else {
			throw new IllegalArgumentException("Illegal input. OPERATOR not recognized");
		}

		switch ((String) tokenString.getValue()) {

		case "jmbag":
			newCond = new ConditionalExpression(FieldValueGetters.JMBAG, (String) tokenStrLit.getValue(), c);
			break;
		case "firstName":
			newCond = new ConditionalExpression(FieldValueGetters.FIRST_NAME, (String) tokenStrLit.getValue(), c);
			break;
		case "lastName":
			newCond = new ConditionalExpression(FieldValueGetters.LAST_NAME, (String) tokenStrLit.getValue(), c);
			break;
		default:
			throw new IllegalArgumentException("Illegal input. Invalid FieldValueGetters");
		}
		return newCond;
	}

}
