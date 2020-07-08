package hr.fer.zemris.java.hw07.observer2;

/**
 * Instances of SquareValue class write a square of the integer stored in the
 * IntegerStorage to the standard output (but the stored integer itself is not
 * modified)
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * This method writes square value of value in IntegerStorageChange istorage
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println(
				"Provided new value: " + istorage.getStorage().getValue() + ", square is " +(int) Math.pow(istorage.getStorage().getValue(), 2));
	}

}
