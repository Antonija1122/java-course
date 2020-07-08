package hr.fer.zemris.java.custom.collections;

/**
 * Class that does not extend Collection but uses it's methods to implement
 * stack-like collection. Adaptee is the ArrayIndexedCollection class with its
 * methods add, insert etc. Implemented methods are pop, push, peek etc.
 * 
 * @author antonija
 *
 */
public class ObjectStack<T> {

	/**
	 * private variable used to be adapted is ArrayIndexedCollection stack
	 */
	private ArrayIndexedCollection<T> stack;

	/**
	 * Constructor creates new ArrayIndexedCollection instance
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection<T>();
	}

	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * Implemented here to determine result by utilizing method size()
	 * 
	 * @return true if collection contains no objects false if collection is empty
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * 
	 * @return number of elements on stack
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Method pushes Object value on stack and now value is at the peek of the stack
	 * 
	 * @param value object pushed on the stack
	 */

	public void push(T value) {
		stack.add(value);
	}

	/**
	 * Method pops peek value from the stack and returns it. It also removes that
	 * value from the peek of the stack.
	 * 
	 * @throws EmptyStackException if the stack is empty when the method is called
	 * @return last Object on the stack
	 */
	public T pop() {
		if (stack.size() == 0) {
			throw new EmptyStackException("Stack is empty!");
		} else {
			T lastObject = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);
			return lastObject;
		}

	}

	/**
	 * Method takes peek value from the stack and returns it but the value is not
	 * removes from the stack.
	 * 
	 * @throws EmptyStackException if the stack is empty when the method is called
	 * @return last Object on the stack
	 */
	public T peek() {
		if (stack.size() == 0) {
			throw new EmptyStackException("Stog je prazan!");
		}
		T lastObject = stack.get(stack.size() - 1);
		return lastObject;
	}

	public void clear() {
		stack.clear();
	};
}
