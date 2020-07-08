package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * This class uses map to implement multiple stacks. While Map allows you only
 * to store for each key a single value, ObjectMultistack allows the user to
 * store multiple values for same key and it provides a stack-like abstraction.
 * Keys for this ObjectMultistack are instances of the class String. Values that
 * are be associated with those keys are instances of class ValueWrapper. This
 * class offers methods: push, pop, peek and isEmpty().
 * 
 * @author antonija
 *
 */
public class ObjectMultistack {

	/**
	 * Private map that stores heads of each stack
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();

	/**
	 * This class instances are entries for implemented stack.
	 * 
	 * @author antonija
	 *
	 */
	private static class MultistackEntry {

		/**
		 * Wrap is stored value of MultistackEntry
		 */
		private ValueWrapper wrap;

		/**
		 * Next is reference to next MultistackEntry
		 */
		private MultistackEntry next;

		/**
		 * Public constructor initializes instance of this class
		 * 
		 * @param wrap
		 * @param next
		 */
		public MultistackEntry(ValueWrapper wrap, MultistackEntry next) {
			this.wrap = wrap;
			this.next = next;
		}
	}

	/**
	 * This method pushes input valueWrapper to the stack associated with key
	 * keyName.
	 * 
	 * @param keyName      key value for stack that valueWrapper has to be added to
	 * @param valueWrapper ValueWrapper that has to be added to this
	 *                     ObjectMultistack
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		if (keyName == null || valueWrapper == null) {
			throw new NullPointerException("It is not allowed to add pair with key or value set to null!");
		}
		if (!map.containsKey(keyName)) {
			MultistackEntry entry = new MultistackEntry(valueWrapper, null);
			map.put(keyName, entry);
		} else {
			MultistackEntry first = map.get(keyName);
			map.remove(keyName);
			MultistackEntry entry = new MultistackEntry(valueWrapper, first);
			map.put(keyName, entry);
		}
	}

	/**
	 * This method pops last ValueWrapper added to the stack associated with key
	 * keyName. This method returns last ValueWrapper and removes it from this
	 * ObjectMultistack
	 * 
	 * @param keyName key for wanted stack
	 * @return last ValueWrapper from stack
	 */
	public ValueWrapper pop(String keyName) {

		if (map.containsKey(keyName)) {
			MultistackEntry last = map.get(keyName);
			map.remove(keyName);
			if (last.next != null) {
				map.put(keyName, last.next);
			}
			return last.wrap;
		} else {
			throw new EmptyStackException("Invalid key input. Given key doesn't exist.");
		}

	}

	/**
	 * This method returns last ValueWrapper added to the stack associated with key
	 * keyName without removing it from the stack. .
	 * @param keyName key for wanted stack
	 * @return last ValueWrapper from stack
	 */
	public ValueWrapper peek(String keyName) {
		if (map.containsKey(keyName)) {
			MultistackEntry last = map.get(keyName);
			return last.wrap;
		} else {
			throw new EmptyStackException("Invalid key input. Given key doesn't exist.");
		}
	}

	/**
	 * This method checks if stack associated with key keyName is empty. 
	 * @param keyName key for wanted stack
	 * @return true if stack associated with key keyName is empty, false otherwise
	 */
	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName);
	}

}
