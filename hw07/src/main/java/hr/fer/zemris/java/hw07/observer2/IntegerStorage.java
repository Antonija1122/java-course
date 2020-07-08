package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements subject that stores observers and executes their method
 * valueChanged if private value is changed. Class also provides functions:
 * addObserver, removeObserver, clearObservers, getValue().
 * 
 * @author antonija
 *
 */
public class IntegerStorage {
	/**
	 * Private int value
	 */
	private int value;
	/**
	 * Private list of observers
	 */
	private List<IntegerStorageObserver> observers;
	/**
	 * Private Iterator used for removing observers during iteration
	 */
	private Iterator<IntegerStorageObserver> iter;

	/**
	 * Public constructor sets private value to input value and creates array list
	 * for storage of observers
	 * 
	 * @param initialValue input value for this value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers= new ArrayList<>();
	}

	/**
	 * This method adds input observer to private list of observers unless observer
	 * already exist
	 * 
	 * @param observer observer that is added to the list
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!this.observers.contains(observer)) {
			this.observers.add(observer);
		}
	}

	/**
	 * This method removes input observer
	 * 
	 * @param observer that has to be removed from this list of observers
	 */
	public void removeObserver(IntegerStorageObserver observer) {		
		if (this.observers.contains(observer)) {
			if (iter == null) {
				this.observers.remove(observer);				
			} else {
				iter.remove();
			}
		}
	}

	/**
	 * This method removes all observers from this list of observers
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for this value
	 * 
	 * @return this value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * This method sets this value to input value and iterates on this list of
	 * observers and executes their methods valueChanged(),
	 * 
	 * @param value new value for this value
	 */
	public void setValue(int value) {
		if (this.value != value) {
			
			IntegerStorageChange change=new IntegerStorageChange(this.value, value, this);
			this.value = value;
			if (observers != null) {
				iter=observers.iterator();
				while(iter.hasNext()) {
					IntegerStorageObserver observer=iter.next();
					observer.valueChanged(change);
				}
				iter=null;
			}
		}
	}

}
