package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class QueryFilter has internal list of ConditionalExpression. Instances can
 * call one implemented method accept.
 * 
 * @author antonija
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Internal list of ConditionalExpression
	 */
	private List<ConditionalExpression> list;

	/**
	 * Constructor initializes internal list from input list
	 * 
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if input student record satisfies all ConditionalExpression from
	 *         internal list, false otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {

		boolean result = true;

		for (ConditionalExpression c : list) {
			if (c.getFieldGetter() == FieldValueGetters.FIRST_NAME) {

				result = result && c.getComparisonOperator().satisfied(record.getName(), c.getStringLiteral());

			} else if (c.getFieldGetter() == FieldValueGetters.LAST_NAME) {

				result = result && c.getComparisonOperator().satisfied(record.getLastName(), c.getStringLiteral());

			} else if (c.getFieldGetter() == FieldValueGetters.JMBAG) {

				result = result && c.getComparisonOperator().satisfied(record.getJmbag(), c.getStringLiteral());

			} else {

				throw new IllegalArgumentException("Illegal strategy in ConditionalExpression");
			}
		}

		return result;
	}

}
