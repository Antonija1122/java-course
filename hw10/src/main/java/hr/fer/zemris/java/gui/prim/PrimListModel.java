package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
/**
 * This class implements ListModel<Integer> that contains prim number  
 */
public class PrimListModel implements ListModel<Integer> {
	
	/**
	 * array of prim numbers
	 */
	private List<Integer> elements = new ArrayList<>();
	/**
	 * list of listeners
	 */
	private List<ListDataListener> observers = new ArrayList<>();

	/**
	 * Variable that is used to generate prime numbers
	 */
	private Integer n;

	/**
	 * public constructors 
	 */
	public PrimListModel() {
		super();
		n = 1;
		this.elements.add(1);

	}

	/**
	 * Method finds the next prime number, adds it to the list of elements and
	 * notifies all observers.
	 */

	public void next() {
		n++;
		while (!isPrime()) {
			n++;
		}
		add(n);
	}

	/**
	 * Method checks if the number is prime
	 * 
	 * @param takes no argument
	 * 
	 * @return <code> true </code> if the number is prime, otherwise
	 *         <code> false </code>.
	 */
	private boolean isPrime() {

		for (int i = 2; i <= Math.sqrt(n); ++i) {
			if (n % i == 0)
				return false;
		}

		return true;
	}

	/**
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);
	}

	/**
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);
	}

	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return elements.size();
	}

	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	/**
	 * @see javax.swing.ListModel#add(Integer)
	 */
	public void add(Integer element) {
		int pos = elements.size();
		elements.add(element);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : observers) {
			l.intervalAdded(event);
		}
	}

	/**
	 * @see javax.swing.ListModel#add(int)
	 */
	public void remove(int pos) {
		elements.remove(pos);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, pos, pos);
		for (ListDataListener l : observers) {
			l.intervalRemoved(event);
		}
	}
}