package searching.slagalica;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * This class represents puzzle. Private variables are initial state of puzzle
 * initialConfiguration and goal state of puzzle goalConfiguration.
 * 
 * @author antonija
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	/**
	 * Initial state of puzzle
	 */
	private KonfiguracijaSlagalice initialConfiguration;

	/**
	 * Goal state of puzzle or solution to the puzzle
	 */
	private KonfiguracijaSlagalice goalConfiguration;

	/**
	 * Public constructor initializes initialConfiguration and goalConfiguration
	 * 
	 * @param initialConfiguration input initialConfiguration for this puzzle
	 */
	public Slagalica(KonfiguracijaSlagalice initialConfiguration) {
		super();
		this.initialConfiguration = initialConfiguration;
		this.goalConfiguration = new KonfiguracijaSlagalice(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 });
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return t.equals(goalConfiguration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice currentState) {
		return createNewStateSList(currentState);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KonfiguracijaSlagalice get() {
		return initialConfiguration;
	}

	/**
	 * This method checks all possible transition states for input current state and
	 * adds them to the list of Transition<KonfiguracijaSlagalice> and returns that list.
	 * 
	 * @param currentState input current state 
	 * @return list of all possible Transition<KonfiguracijaSlagalice> for input currentState
	 */
	private List<Transition<KonfiguracijaSlagalice>> createNewStateSList(KonfiguracijaSlagalice currentState) {
		LinkedList<Transition<KonfiguracijaSlagalice>> newStatesList = new LinkedList<Transition<KonfiguracijaSlagalice>>();
		int[] currentStateValue = currentState.getPolje();

		int si = currentState.indexOfSpace();
		if (rightExists(si)) {
			newStatesList.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(currentStateValue.clone(), si, si + 1)), 1.0));
		}
		if (leftExists(si)) {
			newStatesList.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(currentStateValue.clone(), si, si - 1)), 1.0));
		}
		if (upExists(si)) {
			newStatesList.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(currentStateValue.clone(), si, si - 3)), 1.0));
		}
		if (downExists(si)) {
			newStatesList.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(currentStateValue.clone(), si, si + 3)), 1.0));
		}
		return newStatesList;
	}

	/**
	 * This method checks if it is possible to move blank space to the right.
	 * @param indexOfZero index of blank space in current state of puzzle
	 * @return true if zero can go to the right, false otherwise
	 */
	private boolean rightExists(int indexOfZero) {
		if (indexOfZero == 2 || indexOfZero == 5 || indexOfZero == 8)
			return false;
		else
			return true;
	}

	/**
	 * This method checks if it is possible to move blank space to the left.
	 * @param indexOfZero index of blank space in current state of puzzle
	 * @return true if zero can go to the left, false otherwise
	 */
	private boolean leftExists(int indexOfZero) {
		if (indexOfZero == 0 || indexOfZero == 3 || indexOfZero == 6)
			return false;
		else
			return true;
	}

	/**
	 * This method checks if it is possible to move blank space up.
	 * @param indexOfZero index of blank space in current state of puzzle
	 * @return true if zero can go up, false otherwise
	 */
	private boolean upExists(int indexOfZero) {
		if (indexOfZero == 0 || indexOfZero == 1 || indexOfZero == 2)
			return false;
		else
			return true;
	}

	/**
	 * This method checks if it is possible to move blank space down.
	 * @param indexOfZero index of blank space in current state of puzzle
	 * @return true if zero can go down, false otherwise
	 */
	private boolean downExists(int indexOfZero) {
		if (indexOfZero == 6 || indexOfZero == 7 || indexOfZero == 8)
			return false;
		else
			return true;
	}

	/**
	 * This method swaps values in indexes i and j in input int[] value.
	 * @param value input int array
	 * @param i index of first element to swap
	 * @param j index of second element to swap
	 * @return int[] value with swaped values at indexes i and j
	 */
	private int[] swap(int[] value, int i, int j) {
		int element = value[i];
		value[i] = value[j];
		value[j] = element;
		return value;
	}

}
