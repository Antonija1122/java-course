package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testCnstructorAndSize() {
		
		Dictionary<Long, String> dic=new Dictionary<Long, String> ();
		assertEquals(dic.size(), 0);
		assertEquals(dic.isEmpty(), true);
		
	}
	
	@Test
	void testSizeAndPutandIsEmpty() {
		
		Dictionary<Long, String> dic=new Dictionary<Long, String> ();
		assertEquals(dic.size(), 0);
		
		dic.put(Long.valueOf(0036456744), "Ivan");
		assertEquals(dic.size(), 1);
		assertEquals(dic.isEmpty(), false);

	}
	
	
	@Test
	void testPutAndGet() {
		
		Dictionary<Long, String> dic=new Dictionary<Long, String> ();
		assertEquals(dic.size(), 0);
		
		dic.put(Long.valueOf(0036456744), "Ivan");
		assertEquals(dic.size(), 1);
		
		assertEquals(dic.get(Long.valueOf(0036456744)), "Ivan");
		assertEquals(dic.get(Long.valueOf(45)), null);		
			
	}
	
	
	@Test
	void testPutNull() {
		
		Dictionary<Long, String> dic=new Dictionary<Long, String> ();
		assertEquals(dic.size(), 0);
		
		dic.put(Long.valueOf(36), "Ivan");
		assertEquals(dic.size(), 1);
		
		dic.put(Long.valueOf(34), null);
		assertEquals(dic.size(), 2);
		
		
		assertEquals(dic.get(Long.valueOf(36)), "Ivan");
		
		assertEquals(dic.get(Long.valueOf(34)), null);

		assertThrows(NullPointerException.class, () -> 	dic.put(null, "Mate"));
			
	}
	
	
	
	
	
	
	@Test
	void testPutAndGet2() {
		
		Dictionary<Long, String> dic=new Dictionary<Long, String> ();
		assertEquals(dic.size(), 0);
		
		dic.put(Long.valueOf(30), "Ivan");
		assertEquals(dic.size(), 1);
		
		dic.put(Long.valueOf(31), "Marko");
		assertEquals(dic.size(), 2);
		
		dic.put(Long.valueOf(32), "Mate");
		assertEquals(dic.size(), 3);
		
		dic.put(Long.valueOf(31), "Luka"); //preslikava Marka
		assertEquals(dic.size(), 3);
		
		dic.put(Long.valueOf(30), "Matija"); //preslikava Ivana
		assertEquals(dic.size(), 3);
		
		
		
		assertEquals(dic.get(Long.valueOf(30)), "Matija");
		assertEquals(dic.get(Long.valueOf(31)), "Luka");
		assertEquals(dic.get(Long.valueOf(32)), "Mate");
		
		assertEquals(dic.get(Long.valueOf(45)), null);	
	
	}
	
	@Test
	void testClear() {
		
		Dictionary<Long, String> dic=new Dictionary<Long, String> ();
		assertEquals(dic.size(), 0);
		
		dic.put(Long.valueOf(30), "Ivan");
		dic.put(Long.valueOf(31), "Marko");
		dic.put(Long.valueOf(32), "Mate");
		
		assertEquals(dic.size(), 3);
	
		dic.clear();
		assertEquals(dic.size(), 0);
			
	}
	

	
	

}
