package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * A node representing a command which generates some textual output
 * dynamically. It inherits from Node class.
 * 
 * @author antonija
 *
 */
public class EchoNode extends Node {
	
	/**
	 */
	Element[] elements;
	
	/**
	 * Public constructor that initializes new ForLoopNode instance that extends node.
	 * @param elements - initial value of elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	
	/**
	 * Getter method for elements 
	 *Method used for rewriting expressions in correct way after parsing it 
	 * @return Element[] elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	public String getText() {
		String s="";
		for(int i=0; i<elements.length; i++) {
		
			if(elements[i] instanceof ElementString) {
				s += "\"" + elements[i].toString() + "\" ";
			} else {
				s= s + elements[i].toString()+ " ";
			}
		}
		return s;
	
	}
	
	
	
	
}
