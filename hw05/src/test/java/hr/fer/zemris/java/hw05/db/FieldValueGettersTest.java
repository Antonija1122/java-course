package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	@Test
	void test() {
		StudentRecord record = getSomehowOneRecord();
		
		assertEquals(FieldValueGetters.FIRST_NAME.get(record), "Mateja");
		assertEquals(FieldValueGetters.LAST_NAME.get(record), "Gagić");
		assertEquals(FieldValueGetters.JMBAG.get(record), "0000000013");



	}

	private StudentRecord getSomehowOneRecord() {
		//0000000013	Gagić	Mateja	2
		
		StudentRecord record=new StudentRecord("0000000013", "Gagić", "Mateja", 2);
		
		return record;
	}

}
