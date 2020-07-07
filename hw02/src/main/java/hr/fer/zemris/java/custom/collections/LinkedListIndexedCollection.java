package hr.fer.zemris.java.custom.collections;

/**
 * LinkedListIndexedCollection collection extends Collection. This class defines
 * private static class ListNode with pointers to previous and next list node
 * and additional reference for value storage. This way it makes a linked list
 * of collections objects. Empty methods from Collection are correctly
 * implemented here
 * 
 * @author antonija
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Size is a number of objects in collection
	 */
	private int size;
	/**
	 * first is pointer to the first Object in the collection
	 */
	private ListNode first;
	/**
	 * last is pointer to last object in collection
	 */
	private ListNode last;

	/**
	 * Private static class defines elements(nodes) of the list. Every node has
	 * pointer to previous and pointer to next node and it also has Object value.
	 * 
	 * @author antonija
	 *
	 */
	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}

	/**
	 * Constructor defines first and last node pointers to null;
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}

	/**
	 * Constructor adds all elements from collection other to this collection
	 * 
	 * @param other input collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		if (other == null) {
			throw new NullPointerException();
		} else {
			addAll(other);
		}
	}

	/**
	 * Created only for test purposes.
	 * 
	 * @return pointer to first object on the list
	 */
	protected ListNode firstForTesting() {
		return first;
	}

	/**
	 * Created only for test purposes.
	 * 
	 * @return pointer to last object on the list
	 */
	protected ListNode lastForTesting() {
		return last;
	}

	/**
	 * returns size of collection
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * 
	 * Adds the given object into this collection at the end of collection; newly
	 * added element becomes the element at the biggest index. 
	 * @param Object value added element 
	 * @throws NullPointerException for input Object null 
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (size == 0) {
			ListNode newNode = new ListNode();
			newNode.value = value;
			newNode.next = null;
			newNode.previous = null;
			first = last = newNode;
			size++;
		} else {
			ListNode newNode = new ListNode();
			newNode.value = value;
			newNode.previous = this.last;
			newNode.next = null;
			last.next = newNode;
			this.last = newNode;
			size++;
		}
	}

	
	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 * @return true if Object value is in the collection, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		for (ListNode i = this.first; i != null; i = i.next) {
			if (i.value.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 * @return true if Object value is in the collection and removes it from the
	 *         collection, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		int index = -1;
		int counter = 0;
		for (ListNode i = this.first; i != null; i = i.next, counter++) {
			if (i.value.equals(value)) {
				index = counter;
				break;
			}
		}
		if (index != -1) {
			remove(index);
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 * @return Object array of size this.size of Object values
	 */
	@Override
	public Object[] toArray() {
		int counter = 0;
		Object[] newArray = new Object[size];
		if (this.first == null) {
			return newArray;
		}
		for (ListNode i = this.first; i != null; i = i.next, counter++) {
			// provjeri
			newArray[counter] = i.value;
		}
		return newArray;
	}

	
	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode i = this.first; i != null; i = i.next) {
			processor.process(i.value);
		}

	}
	
	
	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 */
	@Override
	public void clear() {
		first=null;
		last=null;
		this.size = 0;
	}

	
	/**
	 * Method returns Object at position index in collection
	 * 
	 * @return Object at position index
	 */
	public Object get(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException();
		}
		int counter;
		Object returnObject = null;
		if (index < size / 2) {
			counter = 0;
			for (ListNode i = this.first; i != null; i = i.next, counter++) {
				if (counter == index) {
					returnObject = i.value;
					return returnObject;
				}
			}
		} else {
			counter = size - 1;
			for (ListNode i = this.last; i != null; i = i.previous, counter--) {
				if (counter == index) {
					returnObject = i.value;
					return returnObject;
				}
			}
		}
		return returnObject;
	}
	
	
	/**
	 * Method Inserts (does not overwrite) the given value at the given position in
	 * array. Elements starting from this position are shifted one position. The
	 * legal positions are 0 to size. If position is invalid, an appropriate
	 * exception is be thrown. Average complexity is n/2+1
	 * 
	 * @throws IndexOutOfBoundsException for ilegal positions
	 * @param value    Object that is inserted in collection
	 * @param position position of new value
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode newNode = new ListNode();
		newNode.value = value;
		if (position == 0) {
			newNode.next = first;
			newNode.previous = null;
			first.previous = newNode;
			first = newNode;
			size++;
		} else if (position == size) {
			add(value);
		} else if (position == size - 1) {
			newNode.next = last;
			newNode.previous = last.previous;
			last.previous.next = newNode;
			last.previous = newNode;
			last = newNode;
			size++;
		} else {
			int counter = 0;
			for (ListNode i = this.first; i != null; i = i.next, counter++) {
				if (counter == position) {
					newNode.previous = i.previous;
					newNode.next = i;
					i.previous.next = newNode;
					i.previous = newNode;
					size++;
					break;
				}
			}
		}
	}

	/**
	 * Method searches the collection and returns the index of the first occurrence
	 * of the given value or -1 if the value is not found. The equality is
	 * determined using the equals method. Average complexity is n/2+1
	 * 
	 * @param value input parametar
	 * @return position of Object value, -1 if not found in collection
	 */
	public int indexOf(Object value) {
		int counter = 0;
		for (ListNode i = this.first; i != null; i = i.next, counter++) {
			if (i.value.equals(value)) {
				return counter;
			}
		}
		return -1;
	}
	
	
	
	/**
	 * Method removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1.
	 * 
	 * @throws IndexOutOfBoundsException for illegal index
	 * @param index position of Object that has to be removed from collection
	 */
	public void remove(int position) {
		if (position < 0 || position > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		int counter = 0;
		if (size == 1 && position == 0) {
			first = last = null;
			size = 0;
			return;
		}
		if (position == 0) {
			first.next.previous = null;
			first = first.next;
			size--;
			return;
		} else if (position == size - 1) {
			last.previous.next = null;
			last = last.previous;
			size--;
			return;
		}
		for (ListNode i = this.first; i != null; i = i.next, counter++) {
			if (counter == position) {
				i.previous.next = i.next;
				i.next.previous = i.previous;
				i.next = null;
				i.previous = null;
				size--;
				break;
			}
		}
	}

}
