package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void test() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);

		StudentRecord record = getSomehowOneRecord();
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
																										
				expr.getStringLiteral() // returns "Bos*"
		);
		assertFalse(recordSatisfies);

		record = getSomehowOneRecord2();
		recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
		
				expr.getStringLiteral() // returns "Bos*"
		);
		assertTrue(recordSatisfies);

	}

	private static StudentRecord getSomehowOneRecord() {
		// 0000000013 Gagić Mateja 2

		StudentRecord record = new StudentRecord("0000000013", "Gagić", "Mateja", 2);

		return record;
	}

	private static StudentRecord getSomehowOneRecord2() {
		// 0000000013 Bosnić Mateja 2

		StudentRecord record = new StudentRecord("0000000013", "Bosnić", "Mateja", 2);

		return record;
	}

}
