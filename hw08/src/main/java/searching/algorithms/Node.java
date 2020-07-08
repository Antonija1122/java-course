package searching.algorithms;

/**
 * This class represents node for current state. This class with information
 * about current state has information of previous state to current state and
 * cost from previous to current state(number of moves from previous to
 * current).
 * 
 * @author antonija
 *
 * @param <S>
 */
public class Node<S> {

	/**
	 * Current state of node 
	 */
	private S currentState;
	/**
	 * Cost of transition from parent to current state
	 */
	private double cost;
	/**
	 * state previous to current state
	 */
	private Node<S> parent;

	/**
	 * Public constructor initializes private variables to input values.
	 * @param parent previous state
	 * @param state current state
	 * @param cost cost of transition from parent to current state
	 */
	public Node(Node<S> parent, S state, double cost) {
		super();
		this.cost = cost;
		this.parent = parent;
		this.currentState = state;
	}

	/**
	 * Getter method for current state
	 * @return currentState
	 */
	public S getState() {
		return currentState;
	}

	/**
	 * Getter method for state
	 * @return state
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Getter method for parent
	 * @return parent
	 */
	public Node<S> getParent() {
		return parent;
	}

}
