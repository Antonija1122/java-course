package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * A node representing a piece of textual data. It inherits from Node class
 * 
 * @author antonija
 *
 */
public class TextNode extends Node {

	/**
	 * Private string variable text
	 */
	private String text;

	/**
	 * Public constructor initializes value of new instance of TextNode
	 * 
	 * @param String input
	 */
	public TextNode(String input) {
		super();
		this.text = input;
	}

	/**
	 * Method used for rewriting expressions in correct way after parsing it
	 */
	public String getText() {
		String s = "";
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '{') {
				s = s + "\\{";
			} else {
				s = s + text.charAt(i);
			}

		}
		return s;
	}


}
