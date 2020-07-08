package hr.fer.zemris.java.hw07.observer2;

/**
 * Instances of DoubleValue class write to the standard output double value
 * (i.e. “value * 2”) of the current value which is stored in subject(IntegerStorage), but only
 * first n times since its registration with the subject (n is given in
 * constructor); after writing the double value for the n-th time, the observer
 * automatically de-registers itself from the subject. 
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times that this instance writes out double value of current value in IntegerStorage
	 */
	private int n;

	/**
	 * Public constructor sets n to input value n
	 * @param n
	 */
	public DoubleValue(int n) {
		this.n = n;
	}

	/**
	 * This method writes out to the user double value of current value and decrements n by 1. 
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (n == 0) {
			istorage.getStorage().removeObserver(this);
		} else {
			System.out.println("Double value: " + istorage.getStorage().getValue() * 2);
			n--;
		}
	}

}
