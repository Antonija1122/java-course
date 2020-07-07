package hr.fer.zemris.java.custom.collections;

/**
 * Collection class that has some methods implemented as empty methods but it is
 * expected that they will be overriden at some point
 * 
 * @author antonija
 *
 */
public class Collection extends Processor {

	protected Collection() {
	}

	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * Implemented here to determine result by utilizing method size()
	 * 
	 * @return true if collection contains no objects false if collection is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * Implemented here to always return 0
	 * 
	 * @return 0
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection at the end of array of Objects
	 * elements. Implemented here to do nothing. It is expected to be implemented
	 * later on
	 * 
	 * @param value Object that method adds to the collection
	 */
	public void add(Object value) {
		return;
	}

	/**
	 * Returns true only if the collection contains given value, as determined by
	 * equals method. Implemented here to always return false. It is OK to ask if
	 * collection contains null.
	 * 
	 * @param value Object that
	 * @return false 
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it (in this class it is not
	 * specified which one). Implemented here to always return false
	 * 
	 * @param value
	 * @return false
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null. Implemented here to throw UnsupportedOperationException.
	 * 
	 * @return array of Objects
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();

	}

	/**
	 * Method calls processor.process(.) for each element of this collection. The
	 * order in which elements will be sent is undefined in this class. Implemented
	 * here as an empty method.
	 * 
	 * @param processor 
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection. This other collection remains unchanged. Implemented here to
	 * define a local processor class whose method process adds each item into the
	 * current collection by calling method add, and then calling forEach on the
	 * other collection with this processor as argument.
	 * 
	 * @param other input Collection 
	 */
	public void addAll(Collection other) {
		//
		class LocalProcessor extends Processor {

			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}

		LocalProcessor pro = new LocalProcessor();
		other.forEach(pro);

	}

	/**
	 * Removes all elements from this collection. 
	 * Implemented it here as an empty method.
	 */
	public void clear() {

	}

}
