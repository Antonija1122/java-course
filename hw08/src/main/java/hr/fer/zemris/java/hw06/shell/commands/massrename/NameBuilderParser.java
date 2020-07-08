package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class parses input IZRAZ and creates list of NameBuilders that will
 * build new file name by IZRAZ rules.
 * 
 * @author antonija
 *
 */
public class NameBuilderParser {

	/**
	 * Private lexer that creates tokens for this parser
	 */
	private NameBuilderLexer lexer;

	/**
	 * private list of NameBuilders
	 */
	private List<NameBuilder> builders;

	/**
	 * Public constructor takes IZRAZ and initializes private list of builders 
	 * @param izraz
	 */
	public NameBuilderParser(String izraz) {
		this.lexer = new NameBuilderLexer(izraz);
		builders = new ArrayList<NameBuilder>();
	}

	/**
	 * This method creates builders from the order of tokens. This builders are added to private list of builders. 
	 * @return List of builders created from IZRAZ rules.
	 */
	public NameBuilder getNameBuilder() {

		Token token = lexer.nextToken();
		try {
			while (token.getType() != TokenType.EOF) {
				if (token.getType() == TokenType.STRING) {
					NameBuilderString strBuilder = new NameBuilderString((String) token.getValue());
					builders.add(strBuilder);

				} else if (token.getType() == TokenType.TAG_OPEN) {
					while (token.getType() != TokenType.EOF && token.getType() != TokenType.TAG_CLOSE) {
						token = lexer.nextToken();
						if (token.getType() == TokenType.NUMBER_1) {
							int br1 = (int) token.getValue();
							token = lexer.nextToken();
							if (token.getType() == TokenType.NUMBER_2) {
								String formater = (String) token.getValue();
								NameBuilderReplaceGroup groupBuilder = new NameBuilderReplaceGroup(br1, formater);
								builders.add(groupBuilder);
								token = lexer.nextToken();
							} else {
								if (token.getType() != TokenType.TAG_CLOSE) {
									throw new IllegalArgumentException(
											"Invalid input for IZRAZ. Tag has to be properly closed.");
								}
								NameBuilderReplaceGroup groupBuilder = new NameBuilderReplaceGroup(br1, null);
								builders.add(groupBuilder);
							}
						} else {
							// error mora biti barem prvi arg
							throw new IllegalArgumentException(
									"Invalid input for IZRAZ. There has to be at least one number in tag.");
						}
					}
					if (token.getType() != TokenType.TAG_CLOSE) {
						// error mora se zatvorit
						throw new IllegalArgumentException(
								"Invalid input for IZRAZ. Tag has to be properly closed before the end of input.");
					}
				}
				token = lexer.nextToken();
			}
		} catch (NameBuilderLexerException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		NameBuilderArray builders = new NameBuilderArray(this.builders);
		return builders;
	}


}
