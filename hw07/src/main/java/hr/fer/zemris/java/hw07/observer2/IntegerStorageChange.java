package hr.fer.zemris.java.hw07.observer2;

/**
 * This class represents change of IntegerStorage
 * @author antonija
 *
 */
public class IntegerStorageChange {

	/**
	 * stored IntegerStorage whose change is represented in this class 
	 */
	private IntegerStorage storage;

	/**
	 * old value of IntegerStorage
	 */
	private int oldValue;

	/**
	 * new value of IntegerStorage
	 */
	private int newValue;

	/**
	 * Public constructor saves old and new value of storage and reference to input IntegerStorage
	 * @param oldValue old value of IntegerStorage storage
	 * @param newValue new value of IntegerStorage storage
	 * @param storage IntegerStorage whose change is represented in this class
	 */
	public IntegerStorageChange(int oldValue, int newValue, IntegerStorage storage) {
		this.oldValue = oldValue;
		this.storage = storage;
		this.newValue = newValue;
	}

	/**
	 * Getter method for this IntegerStorage
	 * @return storage
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * Getter method for this oldValue
	 * @return oldValue
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Getter method for this newValue
	 * @return newValue
	 */
	public int getNewValue() {
		return newValue;
	}

}
