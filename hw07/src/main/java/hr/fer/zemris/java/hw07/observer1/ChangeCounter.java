package hr.fer.zemris.java.hw07.observer1;

/**
 * 
 * instances of ChangeCounter counts (and writes to the standard output) the
 * number of times the value stored has been changed since this observer’s
 * registration.
 * 
 * @author antonija
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * private counter of changes in Integer storage
	 */
	private int counter;

	/**
	 * Public constructor sets counter to zero.
	 */
	public ChangeCounter() {
		counter = 0;
	}

	/**
	 * This method writes to the user number of times the value stored has been changed since
	 * this observer’s registration.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
