package coloring.algorithms;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This Class implement three methods for going through all pixels from certain
 * areas of pictures.
 * 
 * @author antonija
 *
 */
public class SubspaceExploreUtil {

	/**
	 * This method implements the way to check all subspace for pixels that have to
	 * be processed and process them. This method adds pixels to the end of the list
	 * to be checked and processed
	 * 
	 * @param s0         Supplier<S> current pixel
	 * @param process    method that processes this current pixel and colors it
	 * @param succ       input Function<S, List<S>> that finds neighbors and adds
	 *                   them to the list for checking if they have to be colored to
	 * @param acceptable tests pixels if they have to be processed
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> zaIstraziti = new LinkedList<S>(Arrays.asList(s0.get()));

		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.pop();
			if (!acceptable.test(si))
				continue;
			process.accept(si);
			zaIstraziti.addAll(succ.apply(si));
		}
	}

	/**
	 * This method implements the way to check all subspace for pixels that have to
	 * be processed and process them. This method adds pixels at the begining of the
	 * list to be checked and processed
	 * 
	 * @param s0         Supplier<S> current pixel
	 * @param process    method that processes this current pixel and colors it
	 * @param succ       input Function<S, List<S>> that finds neighbors and adds
	 *                   them to the list for checking if they have to be colored to
	 * @param acceptable tests pixels if they have to be processed
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> zaIstraziti = new LinkedList<S>(Arrays.asList(s0.get()));

		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.pop();
			if (!acceptable.test(si))
				continue;
			process.accept(si);
			zaIstraziti.addAll(0, succ.apply(si));
		}
	}

	/**
	 * This method implements the way to check all subspace for pixels that have to
	 * be processed and process them. This method adds to the list to be checked and
	 * processed only pixels that already haven't been processed. This method is
	 * more efficient because it doesn't do the same job more than once.
	 * 
	 * @param s0         Supplier<S> current pixel
	 * @param process    method that processes this current pixel and colors it
	 * @param succ       input Function<S, List<S>> that finds neighbors and adds
	 *                   them to the list for checking if they have to be colored to
	 * @param acceptable tests pixels if they have to be processed
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> zaIstraziti = new LinkedList<S>(Arrays.asList(s0.get()));
		Set<S> posjećeno = new HashSet<S>(Arrays.asList(s0.get()));

		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.pop();
			if (!acceptable.test(si))
				continue;
			process.accept(si);
			LinkedList<S> djeca = new LinkedList<S>(succ.apply(si));
			djeca.removeIf(s -> posjećeno.contains(s));
			zaIstraziti.addAll(djeca);
			posjećeno.addAll(djeca);
		}
	}

}
