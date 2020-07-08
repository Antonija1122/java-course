package hr.fer.zemris.java.custom.collections;

/**
 * Dictionary class is a adapter of Collection. Adaptee is
 * ArrayIndexedCollection. Dictionary is a collection that stores paired values
 * of key and value. Key is of type K and value is of type V. Class offers
 * methods: boolean isEmpty(); int size(); void clear(); void put(K key, V
 * value) and V get(Object key). Pairs (K key, V value) are created with private class NewPair<K, V>
 * 
 * @author antonija
 *
 * @param <K> type of value key
 * @param <V> type of value value
 */
public class Dictionary<K, V> {

	/**
	 * private variable used to be adapted is ArrayIndexedCollection dictionary
	 */
	private ArrayIndexedCollection<NewPair<K, V>> dictionary;

	/**
	 * Constructor creates new ArrayIndexedCollection instance
	 */
	public Dictionary() {
		dictionary = new ArrayIndexedCollection<NewPair<K, V>>();
	}

	/**
	 * 
	 * @author antonija
	 *
	 * @param <K> type of input for key
	 * @param <V> type of input for value
	 */
	@SuppressWarnings("hiding")
	private class NewPair<K, V> {

		/**
		 * Key value for paired objects. Type of key is K
		 */
		K key;

		/**
		 * Value of new pair. Type of value is V
		 */
		V value;

		/**
		 * Constructor that creates new pairs of (key, value).
		 * 
		 * @throws NullPointerException if user tries to creates null key.
		 * @param key
		 * @param value
		 */
		public NewPair(K key, V value) {
			if (key == null) {
				throw new NullPointerException();
			}

			this.key = key;
			this.value = value;
		}

		@Override
		public boolean equals(Object key) {
			return (this.key).equals(key);
		}
	}


	/**
	 * Returns true if dictionary contains no pair objects and false otherwise.
	 * 
	 * @return true if dictionary contains no objects false if collection is empty
	 */
	public boolean isEmpty() {
		return dictionary.isEmpty();
	};

	/**
	 * Returns number of paired elements in dictionary
	 * 
	 * @return
	 */
	public int size() {
		return dictionary.size();
	}

	/**
	 * Removes all pair elements from dictionary
	 */
	public void clear() {
		dictionary.clear();
	}

	/**
	 * Method adds pair (K key, V value) in dictionary. If pair with value key
	 * already exists old pair is removed and new one is added.
	 * 
	 * @param key   Input key
	 * @param value Input value
	 */
	public void put(K key, V value) { // "gazi" eventualni postojeći zapis
		NewPair<K, V> newPair = new NewPair<K, V>(key, value);
		int index;
		
		if(dictionary.contains(newPair.key)) {
			index = dictionary.indexOf(newPair.key);
			dictionary.remove(index);
			dictionary.insert(newPair, index);
			return;
		}
		
		dictionary.add(newPair);
	};

	/**
	 * Method returns value of type V that is paired with input key. If there is no
	 * pair with key value of input key null is returned.
	 * 
	 * @param key Input key
	 * @return V value that is paired with input key
	 */
	public V get(Object key) { // ako ne postoji pripadni value, vraća null

		ElementsGetter<Dictionary<K, V>.NewPair<K, V>> getter = dictionary.createElementsGetter();
		NewPair<K, V> returnPair;

		while (getter.hasNextElement()) {
			returnPair = getter.getNextElement();
			if (returnPair.key.equals(key)) {
				return returnPair.value;
			}
		}
		return null;
	};

}
