package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class ValueWrapperTest {
	
	/**
	 * All test are writen for input examples: null, null; Double, Integer; Integer, Integer; Double, Double; Exception;
	 */

	@Test
	void testAdd() {
		
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		assertEquals(Double.valueOf(13), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		assertEquals(Integer.valueOf(13), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
		
		ValueWrapper v9 = new ValueWrapper("15.00");
		ValueWrapper v10 = new ValueWrapper(Double.valueOf(3));
		v9.add(v10.getValue()); // v9 now stores Integer(18); v10 still stores Integer(3).
		assertEquals(Double.valueOf(18), v9.getValue());
		assertEquals(Double.valueOf(3), v10.getValue());
		
		ValueWrapper v7 = new ValueWrapper(true);
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
	}
	
	@Test
	void testSubtract() {
		
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.subtract(v4.getValue()); // v3 now stores Double(11); v4 still stores Integer(1).
		assertEquals(Double.valueOf(11), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.subtract(v6.getValue()); // v5 now stores Integer(11); v6 still stores Integer(1).
		assertEquals(Integer.valueOf(11), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
		
		ValueWrapper v9 = new ValueWrapper("15.00");
		ValueWrapper v10 = new ValueWrapper(Double.valueOf(3));
		v9.subtract(v10.getValue()); // v9 now stores Integer(12); v10 still stores Integer(3).
		assertEquals(Double.valueOf(12), v9.getValue());
		assertEquals(Double.valueOf(3), v10.getValue());
		
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> v7.subtract(v8.getValue()));

	}
	
	@Test
	void testMultiply() {
		
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E2");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.multiply(v4.getValue()); // v3 now stores Double(120); v4 still stores Integer(1).
		assertEquals(Double.valueOf(120.0), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(3));
		v5.multiply(v6.getValue()); // v9 now stores Integer(36); v10 still stores Integer(3).
		assertEquals(Integer.valueOf(36), v5.getValue());
		assertEquals(Integer.valueOf(3), v6.getValue());
		
		ValueWrapper v9 = new ValueWrapper("15.00");
		ValueWrapper v10 = new ValueWrapper(Double.valueOf(3.0));
		v9.multiply(v10.getValue()); // v5 now stores Integer(45); v6 still stores Integer(3).
		assertEquals(Double.valueOf(45.0), v9.getValue());
		assertEquals(Double.valueOf(3.0), v10.getValue());
		
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> v7.multiply(v8.getValue()));

	}
	
	@Test
	void testDivide() {
		
		ValueWrapper v1 = new ValueWrapper("1.2E2");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.divide(v2.getValue()); // v3 now stores Double(120); v4 still stores Integer(1).
		assertEquals(Double.valueOf(120), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(3));
		v5.divide(v6.getValue()); // v5 now stores Integer(36); v6 still stores Integer(3).
		assertEquals(Integer.valueOf(4), v5.getValue());
		assertEquals(Integer.valueOf(3), v6.getValue());
		
		ValueWrapper v9 = new ValueWrapper("15.00");
		ValueWrapper v10 = new ValueWrapper(Double.valueOf(3));
		v9.divide(v10.getValue()); // v9 now stores Integer(45); v10 still stores Integer(3).
		assertEquals(Double.valueOf(5.0), v9.getValue());
		assertEquals(Double.valueOf(3.0), v10.getValue());
		
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> v7.divide(v8.getValue()));

	}
	
	@Test
	void testNumCompare() {
		
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		assertTrue(v1.numCompare(v2.getValue())==0);
		assertEquals(null, v1.getValue());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E2");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1)); 
		assertTrue(v3.numCompare(v4.getValue())>0);
		assertEquals("1.2E2", v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(56));
		assertTrue(v5.numCompare(v6.getValue())<0);
		assertEquals("12", v5.getValue());
		assertEquals(Integer.valueOf(56), v6.getValue());
		
		ValueWrapper v9 = new ValueWrapper("15.00");
		ValueWrapper v10 = new ValueWrapper(Double.valueOf(3));
		assertTrue(v9.numCompare(v10.getValue())>0);
		assertEquals("15.00", v9.getValue());
		assertEquals(Double.valueOf(3.0), v10.getValue());
		
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> v7.numCompare(v8.getValue()));
	}

}
