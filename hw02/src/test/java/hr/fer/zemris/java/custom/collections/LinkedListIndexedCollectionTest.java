package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	@Test
	public void constructor1Test() {

		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertEquals(col.size(), 0);
		assertEquals(col.firstForTesting(), null);
		assertEquals(col.lastForTesting(), null);
	}

	@Test
	public void constructor2TestException() {

		LinkedListIndexedCollection col2 = null;
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(col2));
	}

	@Test
	public void constructor2Test() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("nesto");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		assertNotEquals(col.firstForTesting(), null);
		assertNotEquals(col.lastForTesting(), null);
		assertEquals(col2.size(), 1);

		Object[] content = col2.toArray();
		assertEquals(content[0], "nesto");

	}

	@Test
	public void isEmptyTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertTrue(col.isEmpty());
		col.add("bla");
		assertFalse(col.isEmpty());
	}

	@Test
	public void sizeTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertEquals(0, col.size());
		col.add("bla");
		assertEquals(1, col.size());
		col.add("bla2");
		assertEquals(2, col.size());
	}

	@Test
	// test ujedno pokazuje i da se realocira memorija da dodavanje vise početnog
	// broja elemenata tj vise od dva elementa
	public void addTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(20));
		col.add("bla2");
		col.add("bla3");
		assertEquals(3, col.size());
		Object[] content = col.toArray();
		assertEquals(content[0], Integer.valueOf(20));
		assertEquals(content[1], "bla2");
		assertEquals(content[2], "bla3");
	}

	@Test
	public void addTestNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("bla");
		assertThrows(NullPointerException.class, () -> col.add(null));

	}

	@Test
	public void containTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("bla");
		assertFalse(col.contains("bla23455"));
		assertTrue(col.contains("bla"));
		assertFalse(col.contains(null));
	}

	@Test
	public void removeObjectValueTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("bla");
		col.add("bla2");

		assertFalse(col.remove("bla23455"));// false jer ne sadrži taj objekt
		assertTrue(col.remove("bla"));
		assertEquals(1, col.size());

		Object[] content = col.toArray();
		assertEquals(content[0], "bla2");

		assertTrue(col.remove("bla2"));

	}

	@Test
	public void toArrayTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("bla");
		col.add("bla2");

		Object[] content = col.toArray();
		assertEquals(2, content.length);

		Object[] obj = new Object[] { "bla", "bla2" };
		assertEquals(content[0], obj[0]);
		assertEquals(content[1], obj[1]);
	}

	@Test
	public void toArrayTestNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(UnsupportedOperationException.class, () -> col.toArray());
	}

	@Test // KAKO??
	public void forEachTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(UnsupportedOperationException.class, () -> col.toArray());
	}

	@Test
	public void addAllTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();

		col.add(Integer.valueOf(20));
		other.add("bla");
		other.add("bla2");
		col.addAll(other);

		Object[] content = col.toArray();
		assertEquals(3, col.size());

		assertEquals(Integer.valueOf(20), content[0]);
		assertEquals("bla", content[1]);
		assertEquals("bla2", content[2]);

	}

	@Test 
	public void addAllTestNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		LinkedListIndexedCollection other = null;

		col.add(Integer.valueOf(20));

		assertThrows(NullPointerException.class, () -> col.addAll(other));

	}

	@Test
	public void clearTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(Integer.valueOf(20));
		col.add("bla");
		col.add("bla2");

		assertEquals(3, col.size());

		col.clear();
		assertEquals(0, col.size());

	}

	@Test
	public void getTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(Integer.valueOf(20));
		col.add("bla");
		col.add("bla2");

		assertEquals(col.get(0), Integer.valueOf(20));
		assertEquals(col.get(1), "bla");
		assertEquals(col.get(2), "bla2");

		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(3));
	}

	@Test
	public void insertTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(Integer.valueOf(20));
		col.add("bla");
		col.add("bla2");

		col.insert("prvi", 0);
		col.insert("drugi", 1);
		col.insert("srednji", 3);
		col.insert("zadnji", 6);

		Object[] content = col.toArray();
		Object[] obj = new Object[] { "prvi", "drugi", Integer.valueOf(20), "srednji", "bla", "bla2", "zadnji" };

		assertArrayEquals(content, obj);
		assertEquals(7, col.size());

		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("nesto", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("nesto", 10));
	}

	@Test
	public void indexOfTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("prvi");
		col.add("drugi");
		col.add("treci");

		int index = col.indexOf("prvi");
		int index2 = col.indexOf("treci");
		int nema = col.indexOf("bla");
		int returnZero = col.indexOf("bla");

		assertEquals(index, 0);
		assertEquals(index2, 2);
		assertEquals(nema, -1);
		assertEquals(returnZero, -1);

	}

	@Test
	public void removeAtIndexTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add("prvi");
		col.add("drugi");
		col.add("treci");
		col.add("cetvrti");
		col.add("peti");
		col.add("sesti");

		col.remove(0); // makni prvi
		col.remove(2); // makni cetvrti
		col.remove(col.size() - 1); // makni zadnji

		Object[] content = col.toArray();
		Object[] obj = new Object[] { "drugi", "treci", "peti" };

		assertArrayEquals(content, obj);
		assertEquals(3, col.size());

		col.remove(0);
		col.remove(0);
		col.remove(0);

		assertEquals(0, col.size());

		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(7));
	}
	

}
