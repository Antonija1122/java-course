package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements collection of first n prime numbers. N is given in
 * constructor.
 * 
 * @author antonija
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Size of this collection
	 */
	int numberOfPrimes;

	/**
	 * Public constructor sets size of this collection to n
	 * 
	 * @param n
	 */
	public PrimesCollection(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Input number of primes has to be greater than zero.");
		}
		this.numberOfPrimes = n;
	}

	/**
	 * Getter method for IteratorImpl
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}

	/**
	 * IteratorImpl implements Iterator<Integer> for iterating over this collection
	 * of prim numbers.
	 * 
	 * @author antonija
	 *
	 */
	private class IteratorImpl implements Iterator<Integer> {

		/**
		 * this int number represents last prim nuber in collection
		 */
		int lastPrimIter;

		/**
		 * this number represents how many prim numbers are left in this collection
		 */
		int numberOfPrimesIter;

		/**
		 * Public constructor for iterator sets lastPrimIter to 1 and numberOfPrimesIter
		 * to size of collection
		 */
		public IteratorImpl() {
			lastPrimIter = 1;
			numberOfPrimesIter = numberOfPrimes;
		}

		/**
		 * This method returns true if there are still prim numbers left in collection,
		 * fase otherwise
		 */
		@Override
		public boolean hasNext() {
			return numberOfPrimesIter > 0;
		}

		/**
		 * This method calculates, checks if there are still prim numbers left in this
		 * collection and returns next prim number
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There are no more elements in this collection");
			}
			lastPrimIter++;

			while (true) {
				if (checkIfPrim(lastPrimIter)) {
					break;
				} else {
					lastPrimIter++;
				}
			}
			numberOfPrimesIter--;
			return lastPrimIter;
		}

		/**
		 * This private method checks if input number lastPrimIter2 is an prim number
		 * 
		 * @param lastPrimIter2 input number
		 * @return true if input number is prim number, false otherwise
		 */
		private boolean checkIfPrim(int lastPrimIter2) {
			boolean flag = false;
			for (int i = 2; i <= Math.pow(lastPrimIter2, 0.5); i++) {
				if (lastPrimIter2 % i == 0) {
					flag = true;
					break;
				}
			}
			return !flag;
		}

		/**
		 * Method remove throws UnsupportedOperationException() if called beacuse it is
		 * impossible to remove prim numbers from this collection.
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Brisanje primarnih brojeva nije moguće.");
		}

	}

}
