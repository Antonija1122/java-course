package hr.fer.zemris.java.custom.collections;

import hr.fer.zemris.java.custom.collections.ElementsGetter;

/**
 * Collection class that has some methods implemented as empty methods but it is
 * expected that they will be overriden at some point
 * 
 * @author antonija
 *
 */
public interface Collection {

	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * Implemented here to determine result by utilizing method size()
	 * 
	 * @return true if collection contains no objects false if collection is empty
	 */
	default boolean isEmpty() {
		if (this.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * Implemented here as an abstract method.
	 */
	int size();

	/**
	 * Adds the given object into this collection at the end of array of Objects
	 * elements. Implemented here as an abstract method.
	 */
	void add(Object value);

	/**
	 * Returns true only if the collection contains given value, as determined by
	 * equals method. Implemented here as an abstract method.
	 */
	boolean contains(Object value);

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it (in this class it is not
	 * specified which one). Implemented here as an abstract method.
	 * 
	 */
	boolean remove(Object value);

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null. Implemented here as an abstract method.
	 */
	Object[] toArray();

	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * 
	 * @param processor
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();

		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
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
	default void addAll(Collection other) {
		//
		class LocalProcessor implements Processor {

			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}

		LocalProcessor pro = new LocalProcessor();
		other.forEach(pro);

	}

	/**
	 * Removes all elements from this collection. Implemented here as an abstract
	 * method.
	 */
	void clear();

	// treca zadaca

	/**
	 * Method creates instance of ElementsGetter and returns it.
	 * 
	 * @return instance of ElementsGetter
	 */
	ElementsGetter createElementsGetter();

	/**
	 * Method addAllSatisfying() adds every Object from the given Collection that
	 * passes the Tester tester to this Collection. 
	 * 
	 * @param col input Collection from which the elements are added to this Collection
	 * @param tester Tester that returns true if input element meets his conditions 
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		Object element;

		while (getter.hasNextElement()) {
			element = getter.getNextElement();
			if (tester.test(element)) {
				this.add(element);
			}
		}
	}

}
