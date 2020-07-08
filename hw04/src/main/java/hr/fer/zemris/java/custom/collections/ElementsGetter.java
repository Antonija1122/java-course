package hr.fer.zemris.java.custom.collections;



/**
 * Abstract class which implements two methods hasNextElement and getNextElement. 
 * 
 * @author antonija
 *
 */
public interface ElementsGetter<T> {
	
	
	/**
	 * Method that returns true if there are still elements available in the
	 * Collection, false if the Collection is empty or all elements were already
	 * given to the user
	 * Implemented here as an abstract method.
	 */
	public boolean hasNextElement();
	
	
	/**
	 * Method that returns next element if there are still elements available in the
	 * Collection
	 * Implemented here as an abstract method.
	 * 
	 */
	public T getNextElement();
	
	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * 
	 * @param processor
	 */
	default void processRemaining(Processor<? super T> processor) {
		while(hasNextElement()) {
			processor.process(this.getNextElement());
		}
	
	}

}
