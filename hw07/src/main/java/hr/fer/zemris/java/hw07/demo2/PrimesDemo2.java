package hr.fer.zemris.java.hw07.demo2;

/**
 * Second demo program for PrimesCollection that uses two iterators
 * @author antonija
 *
 */
public class PrimesDemo2 {

	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
