package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

class QueryParserTest {

	@Test
	void testDirectQuery() {
		
		QueryParser parser = new QueryParser("jmbag=\"0000000003\"");
		
		assertTrue(parser.isDirectQuery());
		
		assertEquals(parser.getQueriedJMBAG(), "0000000003");
		
		assertEquals(parser.getQuery().size(), 1);
		
		assertEquals(parser.getQuery().get(0).getStringLiteral(), "0000000003");
	}
	
	@Test
	void testNonDirect() {
		
		QueryParser parser = new QueryParser("firstName>\"A\" and lastName LIKE \"B*ć\"");
		
		assertFalse(parser.isDirectQuery());
		assertEquals(parser.getQuery().size(), 2);
		
		assertEquals(parser.getQuery().get(0).getFieldGetter(), FieldValueGetters.FIRST_NAME);
		assertEquals(parser.getQuery().get(0).getStringLiteral(), "A");
		assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.GREATER);
		
		assertEquals(parser.getQuery().get(1).getFieldGetter(), FieldValueGetters.LAST_NAME);
		assertEquals(parser.getQuery().get(1).getStringLiteral(), "B*ć");
		assertEquals(parser.getQuery().get(1).getComparisonOperator(), ComparisonOperators.LIKE);

		
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());

	}
	
	
	@Test
	void testThrows() {
		
		QueryParser parser = new QueryParser("query jmbag=\"0000000003\"");
		
		QueryParser parser2 = new QueryParser("and jmbag=\"0000000003\" and nesto");
		
		
		
		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalArgumentException.class, () -> parser.getQuery());
		
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("query .l jmbag=\"0000000003\""));
		
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("++ .l jmbag=\"0000000003\""));
		

		assertThrows(IllegalArgumentException.class, () -> parser2.getQuery());


	}
	
	
	

}
