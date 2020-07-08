package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class UtilTest {

	@Test
	void tesHextobyte() {
				
		byte[] testBytes=Util.hextobyte("01aE22");
		
		
		
		assertEquals(3, testBytes.length);
		assertEquals(1, testBytes[0]);
		assertEquals(-82, testBytes[1]);
		assertEquals(34, testBytes[2]);
		
	}
	
	@Test
	void tesHextobyte2() {
				
		byte[] testBytes=Util.hextobyte("aEa548dc");
		assertEquals(4, testBytes.length);
		assertEquals(-82, testBytes[0]);
		assertEquals(-91, testBytes[1]);
		assertEquals(72, testBytes[2]);
		assertEquals(-36, testBytes[3]);
	}
	

	@Test
	void tesHextobyteThrows() {
			
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aE223"));
		
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("012š"));
		
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("012g"));
	}
	
	@Test
	void testBytetohex() {
		
		String testHex=Util.bytetohex(new byte[] {1, -82, 34});
		
		assertEquals(6, testHex.length());
		assertEquals("01ae22", testHex);
		
	}
	
	@Test
	void testBytetohex2() {
		
		String testHex=Util.bytetohex(new byte[] {-82, -91, 72, -36});
		
		assertEquals(8, testHex.length());
		assertEquals("aea548dc", testHex);
		
		
	}
	
}
