package hr.fer.zemris.java.custom.collections;

/**
 * Interface List that extends interface Collection 
 * @author antonija
 *
 */
public interface List<T> extends Collection<T> {
	
	/**
	 * Method returns Object at position index in collection
	 * 
	 * Implemented here as an abstract method.
	 */
	T get(int index);
	
	/**
	 * Method Inserts (does not overwrite) the given value at the given position in
	 * array. Elements starting from this position are shifted one position. The
	 * legal positions are 0 to size. If position is invalid, an appropriate
	 * exception is be thrown. 
	 * Implemented here as an abstract method.
	 * 
	 */
	void insert(T value, int position);
	/**
	 * Method searches the collection and returns the index of the first occurrence
	 * of the given value or -1 if the value is not found. The equality is
	 * determined using the equals method. Average complexity is n/2+1
	 * Implemented here as an abstract method.
	 */
	int indexOf(Object value);
	
	/**
	 * Method removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1.
	 * 
	 *Implemented here as an abstract method.
	 */
	void remove(int index);

}
