package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * ArrayIndexedCollection is exended Collection collection
 * 
 * Collection has two private variables, size of collection (number of objects
 * in collection) and array of value objects. Empty methods from Collection are
 * correctly implemented here
 * 
 * @author antonija
 *
 */

public class ArrayIndexedCollection<T> implements List<T> {

	/**
	 * 
	 */
	private long modificationCount = 0;

	/**
	 * number of Object elements in collection
	 */
	private int size;
	/**
	 * array of Object elements in collection
	 */
	private T[] elements;

	/**
	 * Constructor which allocates memory of size initialCapacity*sizeOfObject and
	 * constructs new object of type ArrayIndexedCollection
	 * 
	 * @throws IllegalArgumentException for initialCapacity=zero
	 * @param initialCapacity initial size of elements array
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.size = 0;
		this.elements = (T[]) new Object[initialCapacity];
	}

	/**
	 * Constructor which allocates memory of size 16*sizeOfObject and constructs new
	 * object of type ArrayIndexedCollection
	 * 
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Constructor which allocates memory of size initialCapacity*sizeOfObject,
	 * constructs new object of type ArrayIndexedCollection and adds al Objects from
	 * Collection other to newly constructed Collection
	 * 
	 * @throws IllegalArgumentException for initialCapacity=zero
	 * @throws NullPointerException     exception for empty Collection other
	 * @param initialCapacity initial size of elements array
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> other, int initialCapacity) {
		this(initialCapacity);
		if (other == null) {
			throw new NullPointerException();
		} else {
			if (initialCapacity < other.size()) {
				this.elements = (T[]) new Object[other.size()];
			}
		}
		addAll(other);
	}

	/**
	 * Constructor which allocates memory of size 16*sizeOfObject, constructs new
	 * object of type ArrayIndexedCollection and adds al Objects from Collection
	 * other to newly constructed Collection
	 * 
	 * @throws NullPointerException exception for empty Collection other
	 */
	public ArrayIndexedCollection(Collection<T> other) {
		this(other, 16);
	}

	/////// zadaca 3.

	/**
	 * Private static class that implements ElementsGetter and has two public
	 * methods hasNextElement() and getNextElement(). LocalElementsGetter buids an
	 * object of Collection that makes it possible to take one by one element from
	 * the Collection.
	 * 
	 * @author antonija
	 *
	 */
	private static class LocalElementsGetter<T1> implements ElementsGetter<T1> {
		
		private ArrayIndexedCollection<T1> list;
		
		/**
		 * private variable of saved value of ModificationCount
		 */
		private long savedModificationCount;

		/**
		 * Current value in the Collection
		 */
		private T1[] current;
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
		public LocalElementsGetter(ArrayIndexedCollection<T1> list) {
			this.list=list;
			this.savedModificationCount = list.modificationCount;
			this.current = list.elements;
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
		public T1 getNextElement() {
			if (savedModificationCount != list.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (numberOfCalls == size) {
				throw new NoSuchElementException();
			} else {
				T1 send = current[numberOfCalls];
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
		LocalElementsGetter<T> newElementsGetter = new LocalElementsGetter<T>(this);
		return newElementsGetter;
	}

	/////////////

	/**
	 * Created only for test purposes.
	 * 
	 * @return size of elements array. NOT SIZE OF COLLECTION.
	 */
	protected int capacity() {
		return elements.length;
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * 
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Method reallocates memory and doubles the size od array elements
	 * 
	 * @param elements array of collection elements
	 * @return Object[] bigger array with old elements
	 */
	@SuppressWarnings("unchecked")
	private T[] reallocate(T[] elements) {
		T[] newElements = (T[]) new Object[elements.length * 2];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		return newElements;
	}

	/**
	 * Adds the given object into this collection (reference is added into first
	 * empty place in the elements array). Complexity is n with reallocation, else
	 * 1.
	 * 
	 * @throws NullPointerException for input Object with null reference;
	 */
	@Override
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (size == elements.length) {
			elements = reallocate(elements);
		}
		modificationCount++;
		elements[size] = value;
		size++;
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
	
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
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
		
		for (int i = 0; i < size; i++) {
			if (this.elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null. Implemented here to throw UnsupportedOperationException.
	 * 
	 * @return Object array of size this.size of Object values
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		T[] newArray = (T[]) new Object[size];
		if (this.elements[0] == null) {
			return newArray;
		}
		for (int i = 0; i < this.size; i++) {
			if (elements[i] == null) {
				break;
			}
			newArray[i] = elements[i];
		}
		return newArray;
	}

	/**
	 * Removes all elements from this collection. Implemented it here as an empty
	 * method.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			this.elements[i] = null;
		}
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
		return this.elements[index];
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
		if (size == elements.length) {
			elements = reallocate(elements);
		}
		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;
		modificationCount++;
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
		for (int i = 0; i < size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
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
	public void remove(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = index; i < size - 1; i++) {
			elements[i] = this.elements[i + 1];
		}
		size--;
		modificationCount++;
	}
	
		


}
