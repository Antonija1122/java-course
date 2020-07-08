package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Class Context has private stack that stores states for turtle 
 * @author antonija
 *
 */
public class Context {

	/**
	 * Private stack object
	 */
	private ObjectStack<TurtleState> stack;

	/**
	 * Constructor initializes internal stack to empty stack
	 */
	public Context() {
		stack = new ObjectStack<>();
	}

	/**
	 * Method returns current state which is peek state from stack
	 * 
	 * @return
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	};

	/**
	 * Method pushes new state on the top of the stck
	 * 
	 * @param state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Method pops one state from the stack and returns it. State is removed from
	 * the top of the stack
	 */
	public void popState() {
		stack.pop();
	}

}
