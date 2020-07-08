package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void testLESS() {
		
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testLIKE() {
		
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		
		assertFalse(oper.satisfied("AAA", "AA*AA"));

		assertTrue(oper.satisfied("AAAA", "AA*AA"));
	}
	
	
	void testGREATER() {
		
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertTrue(oper.satisfied("Jasna","Ana"));
	}
	
	void testEQUALS() {
		
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		// true, since Ana < Jasna
		
		assertFalse(oper.satisfied("Jasna","Ana"));
		
		assertTrue(oper.satisfied("Jasna","Jasna"));
	}
	
	void testNOT_EQUALS() {
		
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertFalse(oper.satisfied("Jasna","Jasna"));
		
		assertTrue(oper.satisfied("Jasna","Ana"));
		
	}
	
	void testGREATER_OR_EQUALS() {
		
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertTrue(oper.satisfied("Jasna","Jasna"));
		
		assertTrue(oper.satisfied("Jasna","Ana"));
		
		assertFalse(oper.satisfied("Ana", "Jasna"));

	}
	
	void testLESS_OR_EQUALS() {
		
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue(oper.satisfied("Jasna","Jasna"));
		
		assertFalse(oper.satisfied("Jasna","Ana"));
		
		assertTrue(oper.satisfied("Ana", "Jasna"));

	}

}
