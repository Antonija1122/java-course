package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	void test1() {

		QueryParser parser = new QueryParser("firstName>\"Ana\" and lastName LIKE \"Bo*ić\"");

		StudentRecord st = new StudentRecord("000034", "Bosnić", "Jasna", 1); //jasna>Ana

		QueryFilter filter = new QueryFilter(parser.getQuery());

		assertTrue(filter.accepts(st));

		assertEquals(parser.getQuery().size(), 2);

	}
	
	
	
	@Test
	void test2() {

		QueryParser parser = new QueryParser("firstName>\"Ana\" and lastName LIKE \"Bo*ić\"");

		StudentRecord st = new StudentRecord("000034", "Bolinovac", "Jasna", 1);

		QueryFilter filter = new QueryFilter(parser.getQuery());

		assertFalse(filter.accepts(st));

		assertEquals(parser.getQuery().size(), 2);
	}
	
	@Test
	void test3() {

		QueryParser parser = new QueryParser("jmbag=\"0000000003\"");
		
		StudentRecord st = new StudentRecord("0000000003", "Jelenac", "Jasna", 3);

		QueryFilter filter = new QueryFilter(parser.getQuery());

		assertTrue(filter.accepts(st));

	}
	
	
	@Test
	void test4() {

		QueryParser parser = new QueryParser("jmbag=\"0000000003\" and lastName LIKE \"*ić\"");
		
		StudentRecord st = new StudentRecord("0000000003", "Markić", "Marko", 3);

		QueryFilter filter = new QueryFilter(parser.getQuery());

		assertTrue(filter.accepts(st));

	}
	
	

}
