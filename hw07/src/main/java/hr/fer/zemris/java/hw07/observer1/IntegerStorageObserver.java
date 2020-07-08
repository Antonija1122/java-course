package hr.fer.zemris.java.hw07.observer1;

/**
 * Interface for creating observers for IntegerStorge class.
 * @author antonija
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called every time that value in input IntegerStorage is changed
	 * @param istorage
	 */
	public void valueChanged(IntegerStorage istorage);


}
