package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits from Node class.
 * 
 * @author antonija
 *
 */
public class ForLoopNode extends Node {

	/**
	 * loop variable
	 */
	ElementVariable variable;
	/**
	 * start value of variable
	 */
	Element startExpression;
	/**
	 * end value of variable
	 */
	Element endExpression;
	/**
	 * step value 
	 */
	Element stepExpression; // which can be null

	/**
	 * Public constructor that initializes new ForLoopNode instance that extends
	 * node.
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		if (variable == null || startExpression == null || endExpression == null) {
			throw new IllegalArgumentException();
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter method for variable
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter method for startExpression
	 * 
	 * @return startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter method for endExpression
	 * 
	 * @return endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter method for stepExpression
	 * 
	 * @return stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	
	/**
	 * Method used for rewriting expressions in correct way after parsing it
	 */
	public String getText() {
		String s = "";

		if (stepExpression == null) {

			s = s + " " + variable.toString() + " " + startExpression.toString() + " " + endExpression.toString();
		} else {
			s = s + " " + variable.toString() + " " + startExpression.toString() + " " + endExpression.toString() + " "
					+ stepExpression.toString() + " ";

		}
		return s;
	}

}
