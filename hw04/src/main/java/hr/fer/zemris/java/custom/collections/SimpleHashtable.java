package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class SimpleHashtable implements a map for storing objects. It uses input key
 * value to determine in what address input value will be stored.
 * 
 * @author antonija
 *
 * @param <K> Type of key
 * @param <V> Type of value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Default size of table unless constructor with input capacity is called
	 */
	private static int DEFAULT_CAPACITY = 16;

	/**
	 * Table which contains first element of linked list in every slot
	 */
	private TableEntry<K, V>[] table; // polje slotova tablice,

	/**
	 * current number of elements in table
	 */
	private int size; // broj parova koji su pohranjeni u tablici

	private int modificationCount;

	/**
	 * Default constructor, initializes table capacity to DEFAULT_CAPACITY, and size
	 * to 0
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_CAPACITY];
		this.modificationCount = 0;
		this.size = 0;
	};

	/**
	 * Constructor, initializes table capacity to first power of 2 greater than
	 * input capacity, and size to 0
	 * 
	 * @throws IllegalArgumentException() is input capacity is less than 1.
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {

		if (capacity < 1) {
			throw new IllegalArgumentException();
		}

		int inititalSize = sizeOfTable(capacity);
		this.table = (TableEntry<K, V>[]) new TableEntry[inititalSize];
		this.size = 0;
		this.modificationCount = 0;
	}
	
	private int sizeOfTable(int capacity) {
		int size = 1;
		while(size < capacity) {
			size *= 2;
		}
		return size;
		
	}

	/**
	 * Private static class that creates elements stored in table of SimpleHashtable
	 * class. Elements TableEntry contain key, value and reference to next object
	 * element of the list.
	 * 
	 * @author antonija
	 *
	 * @param <K1> type of input key
	 * @param <V1> type of input value
	 */
	public static class TableEntry<K1, V1> {

		private K1 key;

		private V1 value;
		/**
		 * Reference to next TableEntry<K, V> element
		 */
		TableEntry<K1, V1> next;
								
		/**
		 * Constructor sets private key to keyInput, private value to valueInput and
		 * reference next to tableNext
		 * 
		 * @param keyInput
		 * @param valueInput
		 * @param tableNext
		 */
		public TableEntry(K1 keyInput, V1 valueInput, TableEntry<K1, V1> tableNext) {
			if (keyInput.equals(null)) {
				throw new NullPointerException();
			} else {
				this.key = keyInput;
				this.value = valueInput;
				this.next = tableNext;
			}
		}

		/**
		 * Getter method for private key.
		 * 
		 * @return key of this instance
		 */
		public K1 getKey() {
			return key;
		}

		/**
		 * Getter method for private value.
		 * 
		 * @return value of this instance
		 */
		public V1 getValue() {
			return value;
		}

		/**
		 * Setter method for private value. Sets this value to input value.
		 */
		public void setValue(V1 value) {
			this.value = value;
		}

		/**
		 * Method returns string representation of TableEntry
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			sb.append(this.key.toString());
			sb.append("=");
			if (this.value == null) {
				sb.append("null");
			} else {
				sb.append(this.value.toString());
			}
			return sb.toString();
		}
	}

	/**
	 * Helper method created only for test purposes.
	 * 
	 * @return length of this table
	 */
	public int getCapacity() {
		return table.length;
	}

	/**
	 * Method adds pair (K key, V value) in this map. If pair with value key already
	 * exists old pair is removed and new one is added.
	 * 
	 * @param key   Input key
	 * @param value Input value
	 */
	public void put(K key, V value) {

		if (key.equals(null)) {
			throw new NullPointerException();
		}

		TableEntry<K, V> newPair = new TableEntry<>(key, value, null);

		if (putInTableBoolean(newPair)) {

			modificationCount++;
			size++;
		}
		if (checkForOverload()) {
			realocate();
			modificationCount++;
		}

	};

	/**
	 * Method returns value of type V that is paired with input key. If there is no
	 * pair with key value of input key null is returned.
	 * 
	 * @param key Input key
	 * @return V value that is paired with input key
	 */
	public V get(Object key) {

		if (key == null) {
			return null;
		}

		int slot = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> i = this.table[slot];
		if (i == null) {
			return null;
		}
		while (i != null) {
			if (i.key.equals(key)) {
				return i.value;
			}
			i = i.next;
		}
		return null;
	}

	/**
	 * Returns number of paired elements in dictionary
	 * 
	 * @return size of this map
	 */
	public int size() {
		return this.size;
	};

	/**
	 * Method checks if this table contains TableEntry with key value of input key.
	 * 
	 * @param key input key value
	 * @return True if table contains input key, false otherwise
	 */
	public boolean containsKey(Object key) {
		return (this.get(key) == null) ? false : true;
	}


	/**
	 * Method checks if this table contains TableEntry with value of input value.
	 * 
	 * @param value input value
	 * @return True if table contains input value, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (int j = 0; j < table.length; j++) {
			TableEntry<K, V> i = this.table[j];
			if (i == null) {
				continue;
			}
			while (i != null) {
				if (i.value == null) {
					if (value == null) {
						return true;
					}

				} else if (i.value.equals(value)) {
					return true;
				}
				i = i.next;
			}
		}
		return false;
	}

	/**
	 * Method removes TableEntry from this table if its key value equal to input key
	 * value. If table doesn't contain TableEntry whit input key value method does
	 * nothing.
	 * 
	 * @param key key of TableEntry that has to be removes from the table
	 */
	public void remove(Object key) {
		TableEntry<K, V> previous;

		if (this.containsKey(key)) {
			modificationCount++;
		} else {
			return;
		}

		if (key == null)
			return;

		for (int j = 0; j < table.length; j++) {
			TableEntry<K, V> i = this.table[j];
			if (i == null) {
				continue;
			}
			if (this.table[j].key.equals(key)) {
				if (i.next == null) {
					this.table[j] = null;
				} else {
					this.table[j] = i.next;
					i = null;
				}
				size--;
				return;
			}
			previous = this.table[j];
			while (i != null) {
				if (i.key.equals(key)) {
					previous.next = i.next;
					i.next = null;
					size--;
					return;
				}
				previous = i;
				i = i.next;
			}
		}
		return;
	};

	/**
	 * Returns true if this map contains no pair objects and false otherwise.
	 * 
	 * @return true if map contains no objects false if map is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method removes all elements from this table.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			this.table[i] = null;
		}
		modificationCount++;
		size = 0;
	};

	/**
	 * Method checks if this table is overloaded. Table is Overloaded if 75% of its
	 * slots is taken.
	 * 
	 * @return true if this table is overloaded, false otherwise
	 */
	private boolean checkForOverload() {

		if (size >= 0.75 * table.length) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method is called every time this table is overloaded. Method creates
	 * new two times bigger table and places all elements of old table in this new
	 * one.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void realocate() {

		int slot;
		TableEntry<K, V>[] oldTable = table;

		this.table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		for (int i = 0; i < table.length / 2; ++i) {

			TableEntry<K, V> slotPair = oldTable[i];

			while (slotPair != null) {

				slot = Math.abs(slotPair.key.hashCode()) % table.length;

				if (table[slot] == null) {
					table[slot] = slotPair;
					slotPair = slotPair.next;
					table[slot].next = null;
				} else {

					TableEntry<K, V> slotPairNewTable = table[slot];

					while (slotPairNewTable.next != null) {
						slotPairNewTable = slotPairNewTable.next;
					}
					slotPairNewTable.next = slotPair;
					slotPair = slotPair.next;
					slotPairNewTable.next.next = null;
				}
			}
		}
	}

	/**
	 * Helper method that creates TableEntry from input key and input value and puts
	 * it in this table.
	 * 
	 * @param key   input key
	 * @param value input value
	 * @return true if new TableEntry is added and false if new TableEntry element
	 *         only changed value of an old one that had the same key.
	 */
	private boolean putInTableBoolean(TableEntry<K, V> newPair) {

		int slot = Math.abs(newPair.key.hashCode()) % table.length;

		TableEntry<K, V> i = table[slot];

		if (table[slot] == null) {
			table[slot] = newPair;
			return true;
		}
		if (table[slot].next == null) {
			if (table[slot].key.equals(newPair.key)) {
				table[slot] = newPair;
				return false;
			}
			table[slot].next = newPair;
			return true;
		}
		while (i.next != null) {
			if (i.key.equals(newPair.key)) {
				i.value = newPair.value;
				return false;
			}
			i = i.next;
		}
		if (i.key.equals(newPair.key)) {
			i.value = newPair.value;
			return false;
		}
		i.next = newPair;
		return true;
	}

	/**
	 * Method returns string representation of SimpleHashtable instance
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int j = 0; j < table.length; j++) {
			TableEntry<K, V> i = this.table[j];
			if (i == null) {
				continue;
			}
			while (i != null) {
				sb.append(i.toString());
				if (j == table.length - 1 && i.next == null) {
				} else {
					sb.append(", ");
				}
				i = i.next;
			}
		}
		sb.append(" ]");
		return sb.toString();
	}

	/**
	 * Function creates and returns new instance of iterator of this map.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	};

	/**
	 * Private class IteratorImpl implements Iterator. Class creates iterator for
	 * SimpleHashtable map. Objects that this Iterator operates on are
	 * SimpleHashtable.TableEntry<K, V>. Iterator offers functions hasNext(), next()
	 * and remove().
	 * 
	 * @author antonija
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Variable saves value of initial modificationCount
		 */
		private int savedModificationCount;

		/**
		 * Index of next Object to be returned with function next()
		 */
		private int nextIndex;

		/**
		 * Last returned element
		 */
		private SimpleHashtable.TableEntry<K, V> current;

		/**
		 * Next element to be returned
		 */
		private SimpleHashtable.TableEntry<K, V> nextPair;

		/**
		 * Current slot that iterator operates on.
		 */
		private int slot = 0;

		/**
		 * Constructor sets initial values of private variables.
		 * 
		 * @param map
		 */
		public IteratorImpl() {
			this.savedModificationCount = modificationCount;
			this.nextIndex = 0;
			this.current = table[0];
			this.slot = 0;
		}

		/**
		 * Method returns true if maps has next element. False if last element of the
		 * map was already sent with method next()
		 * 
		 * @throws ConcurrentModificationException() if changes were made to the map
		 *                                           outside of this class
		 */
		@Override
		public boolean hasNext() {
			if (modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex < size();
		}

		/**
		 * Method returns next element of map.
		 * 
		 * @throws NoSuchElementException()          if method was called and all
		 *                                           elements were already returned
		 * @throws ConcurrentModificationException() if changes were made to this map
		 *                                           outside of this class
		 * 
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (nextPair == null) {
				while (nextPair == null) {
					slot++;
					nextPair = table[slot];
				}
			}
			current = nextPair;
			nextIndex++;
			nextPair = nextPair.next;
			return current;
		}

		/**
		 * Method removes current element of map.
		 * 
		 * @throws ConcurrentModificationException() if changes were made to this map
		 *                                           outside of this class
		 */
		public void remove() {
			if (modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			if (!containsKey(current.key)) {
				throw new IllegalStateException("Last element was already removed");
			}
			SimpleHashtable.this.remove(current.key);
			nextIndex--;
			savedModificationCount++;
		}

	}

}
