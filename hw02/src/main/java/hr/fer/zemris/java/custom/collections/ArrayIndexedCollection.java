package hr.fer.zemris.java.custom.collections;

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

public class ArrayIndexedCollection extends Collection {
	
	
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * number of Object elements in collection
	 */
	private int size;
	/**
	 * array of Object elements in collection
	 */
	private Object[] elements;

	/**
	 * Constructor which allocates memory of size initialCapacity*sizeOfObject and
	 * constructs new object of type ArrayIndexedCollection
	 * 
	 * @throws IllegalArgumentException for initialCapacity=zero
	 * @param initialCapacity initial size of elements array
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}

	/**
	 * Constructor which allocates memory of size 16*sizeOfObject and constructs new
	 * object of type ArrayIndexedCollection
	 * 
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
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
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		if (other == null) {
			throw new NullPointerException();
		} else {
			if (initialCapacity < other.size()) {
				this.elements = new Object[other.size()];
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
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Created only for test purposes.
	 * 
	 * @return size of elements array. NOT SIZE OF COLLECTION.
	 */
	protected int capacity() {
		return elements.length;
	}

	/**
	 * {@inheritDoc}
	 * returns size of collection
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
	private Object[] reallocate(Object[] elements) {
		Object[] newElements = new Object[elements.length * 2];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		return newElements;
	}

	/**
	 * Adds the given object into this collection (reference is added into first
	 * empty place in the elements array).
	 * Complexity is n with reallocation, else 1.  
	 * @throws NullPointerException for input Object with null reference;
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (size == elements.length) {
			elements = reallocate(elements);
		}
		elements[size] = value;
		size++;
	}

	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
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
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 * @return true if Object value is in the collection and removes it from the
	 *         collection, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < size; i++) {
			if (this.elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 * @return Object array of size this.size of Object values
	 */
	@Override
	public Object[] toArray() {
		if (this.elements[0] == null) {
			throw new UnsupportedOperationException();
		}
		Object[] newArray = new Object[size];
		for (int i = 0; i < this.size; i++) {
			if (elements[i] == null) {
				break;
			}
			newArray[i] = elements[i];
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
		for (int i = 0; i < size; i++) {
			if (this.elements[i] == null) {
				break;
			}
			processor.process(this.elements[i]);
		}
	}

	/**
	 *{@inheritDoc}
	 * Method is correctly implemented here.
	 * 
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			this.elements[i] = null;
		}
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
	public void insert(Object value, int position) {
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
	}

}
