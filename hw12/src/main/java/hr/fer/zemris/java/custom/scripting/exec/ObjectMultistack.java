package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;

public class ObjectMultistack {

	private HashMap<String, MultistackEntry> map = new HashMap<String, MultistackEntry>();

	public void push(String keyName, ValueWrapper valueWrapper) {

		if (keyName == null || valueWrapper == null) {
			throw new NullPointerException("It is not allowed to add pair with key or value set to null!");
		}
		// Provjeriti je li čvor već postoji,
		// ako postoji dodati na POČETAK liste novi član
		if (!isEmpty(keyName)) {
			// Uzmi pokazivač na početak liste
			MultistackEntry node = map.get(keyName);
			// Stvori novi node koji pokazuje na početak liste
			MultistackEntry newNode = new MultistackEntry(valueWrapper, node);
			// Izbriši stari pokazivač na početak liste
			map.remove(keyName);
			// Postavi novi pokazivač na početak liste
			map.put(keyName, newNode);
		} else {
			map.put(keyName, new MultistackEntry(valueWrapper, null));
		}

	}

	public ValueWrapper pop(String keyName) {

		if (!isEmpty(keyName)) {
			// Uzmi referencu na početak liste
			MultistackEntry node = map.get(keyName);
			// Uzmi vrijednsot s početka liste
			ValueWrapper value = node.value;
			// Izbriši staru referencu na početak liste
			map.remove(keyName);
			// Postavi novu referencu na početak liste
			map.put(keyName, node.next);
			return value;

		} else {
			throw new IllegalArgumentException("Ne postoji sadržaj pod danim ključem");
		}

	}

	public ValueWrapper peek(String keyName) {

		if (!isEmpty(keyName)) {
			return map.get(keyName).value;

		} else {
			throw new IllegalArgumentException("Ne postoji sadržaj pod danim ključem.");
		}
	}

	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName);
	}

	private static class MultistackEntry {
		private ValueWrapper value;
		private MultistackEntry next;

		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}
	}
}
