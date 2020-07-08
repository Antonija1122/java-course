package hr.fer.zemris.java.hw07.observer1;

/**
 * Instances of SquareValue class write a square of the integer stored in the
 * IntegerStorage to the standard output (but the stored integer itself is not
 * modified)
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * This method writes square value of value in IntegerStorage istorage
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println(
				"Provided new value: " + istorage.getValue() + ", square is " +(int) Math.pow(istorage.getValue(), 2));
	}

}
