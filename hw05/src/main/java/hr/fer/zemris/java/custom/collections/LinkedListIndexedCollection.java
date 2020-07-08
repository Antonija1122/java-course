package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

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
public class LinkedListIndexedCollection<T> implements List<T> {

	private long modificationCount = 0;

	/**
	 * Size is a number of objects in collection
	 */
	private int size;
	/**
	 * first is pointer to the first Object in the collection
	 */
	private ListNode<T> first;
	/**
	 * last is pointer to last object in collection
	 */
	private ListNode<T> last;

	/**
	 * Private static class defines elements(nodes) of the list. Every node has
	 * pointer to previous and pointer to next node and it also has Object value.
	 * 
	 * @author antonija
	 *
	 */
	private static class ListNode<T> {
		ListNode<T> previous;
		ListNode<T> next;
		T value;
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
	public LinkedListIndexedCollection(Collection<T> other) {
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
	protected ListNode<T> firstForTesting() {
		return first;
	}

	/**
	 * Created only for test purposes.
	 * 
	 * @return pointer to last object on the list
	 */
	protected ListNode<T> lastForTesting() {
		return last;
	}

	/////// zadaca 3

	/**
	 * Private static class that implements ElementsGetter and has two public
	 * methods hasNextElement() and getNextElement(). LocalElementsGetter buids an
	 * object of Collection that makes it possible to take one by one element from
	 * the Collection.
	 * 
	 * @author antonija
	 *
	 */
	private static class LocalElementsGetter<T> implements ElementsGetter<T> {
		
		private LinkedListIndexedCollection<T> list;

		/**
		 * private variable of saved value of ModificationCount
		 */
		long savedModificationCount;
		/**
		 * Current ListNode with value and references to previous and next ListNode in
		 * the Collection
		 */
		private ListNode<T> current;
		/**
		 * size of the collection in the moment LocalElementsGetter is created
		 */
		private int size;

		/**
		 * Number of times method getNextElement() is called.
		 */
		private int numberOfCalls;

		/**
		 * Public constructor which buids an Object of LocalElementsGetter from input
		 * size, elements of Collection and variable modificationCount which changes if
		 * changes are made to the Collection
		 * 
		 * @param size              of the Collection in the moment Object
		 *                          LocalElementsGetter is made
		 * @param elements          pointer to first element of Collections elements
		 *                          field.
		 * @param modificationCount variable that increases every time changes are made
		 *                          to the Collection
		 */
		public LocalElementsGetter(LinkedListIndexedCollection<T> list) {

			this.list=list;
			this.savedModificationCount = list.modificationCount;
			this.current = list.first;
			this.size = list.size;
			numberOfCalls = 0;
		}

		/**
		 * Method that returns true if there are still elements available in the
		 * Collection, false if the Collection is empty or all elements were already
		 * given to the user
		 * 
		 * @throws ConcurrentModificationException() if changes were made to the
		 *                                           Collection since Object
		 *                                           LocalElementsGetter was first
		 *                                           created.
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != list.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (numberOfCalls == size)
				return false;
			return true;
		}

		/**
		 * Method that returns next element if there are still elements available in the
		 * Collection
		 * 
		 * @throws NoSuchElementException()          if the Collection is empty or all
		 *                                           elements were already given to the
		 *                                           user
		 * 
		 * @throws ConcurrentModificationException() if changes were made to the
		 *                                           Collection since Object
		 *                                           LocalElementsGetter was first
		 *                                           created.
		 * 
		 * @return next Object element from the Collection
		 */
		@Override
		public T getNextElement() {
			if (savedModificationCount != list.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (numberOfCalls == size) {
				throw new NoSuchElementException();
			} else {
				T send = current.value;
				current = current.next;
				numberOfCalls++;
				return send;
			}

		}

	}

	/**
	 * Public method that creates ElementsGetter using local class
	 * LocalElementsGetter.
	 * 
	 * @return ElementsGetter
	 */
	public ElementsGetter<T> createElementsGetter() {
		ElementsGetter<T> newElementsGetter = new LocalElementsGetter<T>(this);

		return newElementsGetter;
	}

	/////////////

	/**
	 * returns size of collection
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection at the end of collection; newly
	 * added element becomes the element at the biggest index.
	 * 
	 * @param Object value added element
	 * @throws NullPointerException for input Object null
	 */
	@Override
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (size == 0) {
			ListNode<T> newNode = new ListNode<T>();
			newNode.value = value;
			newNode.next = null;
			newNode.previous = null;
			first = last = newNode;
			size++;
			modificationCount++;
		} else {
			ListNode<T> newNode = new ListNode<T>();
			newNode.value = value;
			newNode.previous = this.last;
			newNode.next = null;
			last.next = newNode;
			this.last = newNode;
			size++;
			modificationCount++;
		}
	}

	/**
	 * Returns true only if the collection contains given value, as determined by
	 * equals method. Implemented here to always return false. It is OK to ask if
	 * collection contains null.
	 * 
	 * @return true if Object value is in the collection, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		for (ListNode<T> i = this.first; i != null; i = i.next) {
			if (i.value.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it (in this class it is not
	 * specified which one). Implemented here to always return false
	 * 
	 * @return true if Object value is in the collection and removes it from the
	 *         collection, false otherwise
	 */
	@Override
	public boolean remove(T value) {
		int index = -1;
		int counter = 0;
		for (ListNode<T> i = this.first; i != null; i = i.next, counter++) {
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
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 * 
	 * @return Object array of size this.size of Object values
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		int counter = 0;
		T[] newArray = (T[]) new Object[size];
		if (this.first == null) {
			return newArray;
		}
		for (ListNode<T> i = this.first; i != null; i = i.next, counter++) {
			// provjeri
			System.out.println("Vrijednost je:"+i.value);
			newArray[counter] = i.value;
		}
		return newArray;
	}

	/**
	 * Removes all elements from this collection. Implemented it here as an empty
	 * method.
	 */

	@Override
	public void clear() {
		first = null;
		last = null;
		this.size = 0;
		modificationCount++;
	}

	/**
	 * Method returns Object at position index in collection
	 * 
	 * @return Object at position index
	 */
	public T get(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException();
		}
		int counter;
		T returnObject = null;
		if (index < size / 2) {
			counter = 0;
			for (ListNode<T> i = this.first; i != null; i = i.next, counter++) {
				if (counter == index) {
					returnObject = i.value;
					return returnObject;
				}
			}
		} else {
			counter = size - 1;
			for (ListNode<T> i = this.last; i != null; i = i.previous, counter--) {
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
	public void insert(T value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode<T> newNode = new ListNode<T>();
		newNode.value = value;
		if (position == 0) {
			newNode.next = first;
			newNode.previous = null;
			first.previous = newNode;
			first = newNode;
			size++;
			modificationCount++;
			return;
		} else if (position == size) {
			add(value);
			return;
		} else if (position == size - 1) {
			newNode.next = last;
			newNode.previous = last.previous;
			last.previous.next = newNode;
			last.previous = newNode;
			last = newNode;
			size++;
			modificationCount++;
			return;
		} else {
			int counter = 0;
			for (ListNode<T> i = this.first; i != null; i = i.next, counter++) {
				if (counter == position) {
					newNode.previous = i.previous;
					newNode.next = i;
					i.previous.next = newNode;
					i.previous = newNode;
					size++;
					modificationCount++;
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
		for (ListNode<T> i = this.first; i != null; i = i.next, counter++) {
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
			modificationCount++;
			return;
		}
		if (position == 0) {
			first.next.previous = null;
			first = first.next;
			size--;
			modificationCount++;
			return;
		} else if (position == size - 1) {
			last.previous.next = null;
			last = last.previous;
			size--;
			modificationCount++;
			return;
		}
		for (ListNode<T> i = this.first; i != null; i = i.next, counter++) {
			if (counter == position) {
				i.previous.next = i.next;
				i.next.previous = i.previous;
				i.next = null;
				i.previous = null;
				size--;
				modificationCount++;
				break;
			}
		}
	}

}
