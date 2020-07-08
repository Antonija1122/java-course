package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * This class represents demo program for solving puzzle with SearchUtil.bfs or
 * SearchUtil.bfsv methods. Program writes all states of solving puzzle and
 * total cost of solving puzzle. This program excepts initial state in input
 * arguments for main method. Also this program demonstrates puzzle solving with
 * slagalica-gui-1.0.jar SlagalicaViewer.display(). 
 * 
 * @author antonija
 *
 */
public class SlagalicaMain {

	public static void main(String[] args) {

		if (args.length != 1 || !args[0].trim().matches("^(?!.*(.).*\\1)\\d{9}")) {
			System.out.println("Argument must be 9 digit number with no repeat digits.");
			System.exit(-1);
		}

		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(getArray(args[0].trim())));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});

			SlagalicaViewer.display(rješenje);
		}
	}

	/**
	 * This method returns array of int from input arguments
	 * @param string
	 * @return
	 */
	private static int[] getArray(String string) {

		int[] intArray = new int[9];
		for (int i = 0; i < 9; ++i) {
			intArray[i] = string.charAt(i) - 48;
		}

		return intArray;
	}
}
