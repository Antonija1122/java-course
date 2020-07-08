package searching.algorithms;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class implements two methods for going through all states to find
 * solution to the puzzle.
 * 
 * @author antonija
 *
 */
public class SearchUtil {

	/**
	 * This method finds solution to the puzzle from current state to goal state, by
	 * checking all possible transitions. If method can not find the solution
	 * program will break with exception OutOfMemoryError.
	 * 
	 * @param s0   current state
	 * @param succ method that mapps current state to possible next state
	 * @param goal solution to the puzzle
	 * @return solution node
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		LinkedList<Node<S>> zaIstraziti = new LinkedList<>(Arrays.asList(new Node<S>(null, s0.get(), 0)));
		while (!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.pop();
			if (goal.test(ni.getState()))
				return ni;
			succ.apply(ni.getState()).forEach(transitionState -> zaIstraziti
					.add(new Node<S>(ni, transitionState.getState(), ni.getCost() + transitionState.getCost())));
		}
		return null;
	}

	/**
	 * For given configuration of puzzle and defined transition between states this
	 * method tries to find solution. Compring to {@link #bfs()} this method is
	 * improved version of bfs() using additional list which contains visited
	 * states. This way method will not visit the same state twice.
	 * If the solution is not found method returns null.
	 * 
	 * @param s0   current state
	 * @param succ method that mapps current state to possible next state
	 * @param goal solution to the puzzle
	 * @return solution node
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {

		LinkedList<Node<S>> zaIstraziti = new LinkedList<>(Arrays.asList(new Node<S>(null, s0.get(), 0)));
		List<Transition<S>> transitionList;
		Set<S> posjećeno = new HashSet<S>(Arrays.asList(s0.get()));

		while (!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.pop();
			if (goal.test(ni.getState()))
				return ni;
			transitionList = succ.apply(ni.getState());

			transitionList.forEach(transitionState -> {
				if (!posjećeno.contains(transitionState.getState())) {
					zaIstraziti
							.add(new Node<S>(ni, transitionState.getState(), ni.getCost() + transitionState.getCost()));
					posjećeno.add(transitionState.getState());
				}
			});
		}
		return null;
	}

}
