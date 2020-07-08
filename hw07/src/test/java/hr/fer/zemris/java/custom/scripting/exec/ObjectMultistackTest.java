package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	void testPushAndPeek() {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Marko", new ValueWrapper("98.4"));
		multistack.push("Marko", new ValueWrapper("22.4"));
		multistack.push("Ivan", new ValueWrapper("13.4"));
		multistack.push("Lana", new ValueWrapper("78.9"));

		assertEquals("22.4", multistack.peek("Marko").getValue());
		assertEquals("13.4", multistack.peek("Ivan").getValue());
		assertEquals("78.9", multistack.peek("Lana").getValue());

		assertThrows(EmptyStackException.class, () -> multistack.peek("Karlo").getValue());

	}

	@Test
	void testPop() {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Marko", new ValueWrapper("98.4"));
		multistack.push("Marko", new ValueWrapper("22.4"));
		multistack.push("Ivan", new ValueWrapper("13.4"));
		multistack.push("Lana", new ValueWrapper("78.9"));

		assertEquals("22.4", multistack.pop("Marko").getValue());
		assertEquals("98.4", multistack.peek("Marko").getValue());
		
		assertEquals("13.4", multistack.pop("Ivan").getValue());
		assertEquals("78.9", multistack.pop("Lana").getValue());	
		
		assertThrows(EmptyStackException.class, () -> multistack.pop("Ivan"));
		assertThrows(EmptyStackException.class, () -> multistack.pop("Lana"));
		assertThrows(EmptyStackException.class, () -> multistack.pop("Karlo"));
	}
	
	@Test
	void testisEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertTrue(multistack.isEmpty("Marko"));
		assertTrue(multistack.isEmpty("Ivan"));
		assertTrue(multistack.isEmpty("Lana"));

		multistack.push("Marko", new ValueWrapper("98.4"));
		multistack.push("Ivan", new ValueWrapper("13.4"));
		multistack.push("Lana", new ValueWrapper("78.9"));
		assertFalse(multistack.isEmpty("Marko"));
		assertFalse(multistack.isEmpty("Ivan"));
		assertFalse(multistack.isEmpty("Lana"));	
		
		multistack.pop("Marko");
		multistack.pop("Ivan");
		multistack.pop("Lana");
		assertTrue(multistack.isEmpty("Marko"));
		assertTrue(multistack.isEmpty("Ivan"));
		assertTrue(multistack.isEmpty("Lana"));
		assertTrue(multistack.isEmpty("Karlo"));

	}
	
	

}
