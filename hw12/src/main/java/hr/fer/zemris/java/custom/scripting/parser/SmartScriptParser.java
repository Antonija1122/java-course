package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

import java.util.Stack;


import hr.fer.zemris.java.custom.scripting.elems.Element;

import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;


/**
 * Class implements a simple parser that parses input string. 
 * @author antonija
 * @throws SmartScriptParserException() if input is illegal
 */
public class SmartScriptParser {

	/**
	 * Private lexer instance that groups input string into tokens with TokenTypes
	 * that this parser understands
	 */
	private SmartScriptLexer lexer;

	/**
	 * DocumentNode will have all the nodes that parser created from input string
	 */
	private DocumentNode documentNode;

	/**
	 * Public contructor that initializes lexer from class SmartScriptLexer for
	 * creating tokens for parser. 
	 * Parsing for input string is called.
	 * 
	 * @param documentBody input String
	 */
	public SmartScriptParser(String documentBody) {
		this.lexer = new SmartScriptLexer(documentBody);
		parsing();
	}

	/**
	 * Method that uses lexer and stack to parse input string. There are two sorts
	 * of tags: empty and non empty Empty tags are added as children in
	 * ArrayIndexedList and non empty tags are pushed to the stack and become new parent Node
	 * 
	 * @throws SmartScriptParserException() if input is illegal
	 */
	private void parsing() {

		Stack stack = new Stack();

		DocumentNode documentNode = new DocumentNode();
		stack.push(documentNode);

		try {
			SmartScriptToken token = lexer.nextToken();

			while (token != null && token.getType() != SmartScriptTokenType.EOF) {

				if (token.getType() == SmartScriptTokenType.TEXT) {

					// AKO JE TEKST dodaj
					Node lastNode = (Node) stack.peek();
					Node child = new TextNode(token.getValue().toString()); // dodaje se dijete na zadnji node
					lastNode.addChildNode(child);
					token = lexer.nextToken();

				} else if (isEmptyTag(token)) {
					// ako je prazan tag dodaj ga

					if (token.getValue().toString().equals("END")) {

						stack = chechEndTag(stack);
						token = lexer.getToken();

					} else {

						Node lastNode = (Node) stack.peek();
						Node child = addEchoNode();
						lastNode.addChildNode(child);

						token = lexer.getToken();
					}

				} else if (!isEmptyTag(token) && token.getType() == SmartScriptTokenType.TAG_NAME) {

					if (token.getValue().toString().equals("FOR")) {
						// ako je tag for
						Node child = addForNode();
						Node lastNode = (Node) stack.peek();
						lastNode.addChildNode(child);

						stack.push(child);
						token = lexer.getToken();

					} else {

						// ovjde bi se mogli dodavati novi non empty tagovi
						throw new SmartScriptParserException("Unknown non empty tag");

					}
				} else {
					throw new SmartScriptParserException("SmartScriptTokenType is invalid");
				}

			} // nakon zadnje tokena

		} catch (SmartScriptLexerException e) {

			if (lexer.getToken().getType() == SmartScriptTokenType.EOF) {
				System.out.println("kraj");
			}

			else {
				throw new SmartScriptParserException(e.getMessage());
			}
		}

		if (lexer.getClosedTags() != lexer.getOpenedTags()) {
			throw new SmartScriptParserException("Invalid input. Not all opened tags are closed.");
		}

		this.documentNode = documentNode;

	}

	/**
	 * Method checks if token is an empty tag and returns true if it is, false otherwise. 
	 * @param token input token
	 * @return true if token is empty tag, false otherwise
	 */
	// provjerava je li prazan tag -za sada samo provjerava for kao non empty
	private boolean isEmptyTag(SmartScriptToken token) {
		if (token.getType() == SmartScriptTokenType.TAG_NAME) {
			if (token.getValue().toString().equals("FOR")) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that adds empty tags as a child of last node on stack
	 * 
	 * @return child Node
	 */
	private Node addEchoNode() {

		Stack helperStack = new Stack();
		SmartScriptToken token = lexer.nextToken(); // PRVI NAKON IMENA (PRESKOCI =)

		while (lexer.getToken() != null && lexer.getToken().getType() != SmartScriptTokenType.EOF
				&& lexer.getToken().getType() != SmartScriptTokenType.TEXT
				&& lexer.getToken().getType() != SmartScriptTokenType.TAG_NAME) {

			if (lexer.getToken().getType() != SmartScriptTokenType.VARIABLE
					&& lexer.getToken().getType() != SmartScriptTokenType.STRING
					&& lexer.getToken().getType() != SmartScriptTokenType.FUNCTION
					&& lexer.getToken().getType() != SmartScriptTokenType.OPERATOR
					&& lexer.getToken().getType() != SmartScriptTokenType.CONSTANT_DOUBLE
					&& lexer.getToken().getType() != SmartScriptTokenType.CONSTANT_INTEGER) {

				throw new SmartScriptParserException("Unknown SmartScriptTokenType in echoNode");
			}
			helperStack.push(token.getValue());
			token = lexer.nextToken();
		}

		Element[] echoElements = new Element[helperStack.size()];
		for (int i = echoElements.length - 1; i >= 0; i--) {
			echoElements[i] = (Element) helperStack.pop();
		}
		Node child = new EchoNode(echoElements); // dodaje se dijete na zadnji node
		return child;

	}

	/**
	 * Method that adds non empty tags (in this case only FOR) as a child of last
	 * node on stack
	 * 
	 * @return child Node
	 */
	private Node addForNode() {

		Stack helperStack = new Stack();
		SmartScriptToken token = lexer.nextToken();

		SmartScriptToken firstoken = token;
		if (firstoken.getType() != SmartScriptTokenType.VARIABLE) {
			throw new SmartScriptParserException("First token in for loop has to be type of VARIABLE");
		}

		while (lexer.getToken() != null && lexer.getToken().getType() != SmartScriptTokenType.EOF
				&& lexer.getToken().getType() != SmartScriptTokenType.TEXT
				&& lexer.getToken().getType() != SmartScriptTokenType.TAG_NAME) {

			if (lexer.getToken().getType() != SmartScriptTokenType.VARIABLE
					&& lexer.getToken().getType() != SmartScriptTokenType.STRING
					&& lexer.getToken().getType() != SmartScriptTokenType.FUNCTION
					&& lexer.getToken().getType() != SmartScriptTokenType.OPERATOR
					&& lexer.getToken().getType() != SmartScriptTokenType.CONSTANT_DOUBLE
					&& lexer.getToken().getType() != SmartScriptTokenType.CONSTANT_INTEGER) {

				throw new SmartScriptParserException("Unknown SmartScriptTokenType in EchoNode");
			}
			helperStack.push(token.getValue());
			token = lexer.nextToken();

		}
		if (lexer.getToken().getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptParserException("For loop not properly closed END is missing");
		}
		Element[] forElements = new Element[helperStack.size()]; // dobijem elemente for petlje
		for (int i = forElements.length - 1; i >= 0; i--) {
			forElements[i] = (Element) helperStack.pop();
		}
		if (forElements.length == 3) {

			ElementVariable variable = (ElementVariable) forElements[0];
			Element startExpresion = forElements[1];
			Element endExpresion = forElements[2];

			Node child = new ForLoopNode(variable, startExpresion, endExpresion, null); // dodaje se dijete na zadnji

			return child;// node

		} else if (forElements.length == 4) {

			ElementVariable variable = (ElementVariable) forElements[0];
			Element startExpresion = forElements[1];
			Element endExpresion = forElements[2];
			Element stepExpresion = forElements[3];

			Node child = new ForLoopNode(variable, startExpresion, endExpresion, stepExpresion);
			return child;

		} else {

			throw new SmartScriptParserException("For loop can only have 3 or 4 elements");
		}
	}

	/**
	 * Method checks if END tag is correctly written. Method also checks if there
	 * are more END tags than non empty tags. In that case
	 * SmartScriptParserException() is thrown
	 * 
	 * @throws SmartScriptParserException
	 * @param input stack
	 * @return new stack
	 */
	private Stack chechEndTag(Stack stack) {

		SmartScriptToken token = lexer.nextToken(); // PRVI NAKON ENDA

		if (token != null && lexer.getToken().getType() != SmartScriptTokenType.EOF
				&& token.getType() != SmartScriptTokenType.TEXT && token.getType() != SmartScriptTokenType.TAG_NAME) {

			throw new SmartScriptParserException("Invalid END tag");
		}

		if (stack.size() == 0) {
			throw new SmartScriptParserException("Stack is empty. There are more END tags than non empty tags");
		}
		Node saving = (Node) stack.pop();
		if (stack.size() == 0) {
			throw new SmartScriptParserException("Stack is empty. There are more END tags than non empty tags");
		}
		return stack;
	}

	public DocumentNode getDocumentNode() {
		return documentNode;
	}

}
