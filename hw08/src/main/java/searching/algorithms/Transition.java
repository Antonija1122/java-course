package searching.algorithms;

/**
 * This class represents transition to next state of puzzle. Cost in this way will be 1.
 * @author antonija
 *
 * @param <S>
 */
public class Transition<S> {

	/**
	 * transition state
	 */
	private S state;
	/**
	 * cost of transition state
	 */
	private double cost;

	/**
	 * Public constructor initializes private variables.
	 * @param state
	 * @param cost
	 */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Getter method for state
	 * @return state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Getter method for cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}

}
