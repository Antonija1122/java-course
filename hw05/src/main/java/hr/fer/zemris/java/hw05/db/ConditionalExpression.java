package hr.fer.zemris.java.hw05.db;

/**
 * Class gets through constructor three arguments: a reference to IFieldValueGetter
 * strategy, a reference to string literal and a reference to
 * IComparisonOperator strategy. Class models the complete conditional expression.
 * @author antonija
 *
 */
public class ConditionalExpression {

	/**
	 * Private IFieldValueGetter for comparing two strings
	 */
	private IFieldValueGetter strategy;

	/**
	 * Private value of string to be compared to 
	 */
	private String literal;

	/**
	 * Private instance of IComparisonOperator
	 */
	private IComparisonOperator comparator;

	/**
	 * Constructor initializes new ConditionalExpression with input values
	 * @param strategy
	 * @param literal
	 * @param comp
	 */
	public ConditionalExpression(IFieldValueGetter strategy, String literal, IComparisonOperator comp) {
		super();
		this.strategy = strategy;
		this.literal = literal;
		this.comparator = comp;
	}

	/**
	 * Getter method for strategy
	 * @return strategy
	 */
	public IFieldValueGetter getFieldGetter() {
		return this.strategy;
	}

	/**
	 * Getter method for literal
	 * @return literal
	 */
	public String getStringLiteral() {
		return literal;
	}

	/**
	 * Getter method for comparator
	 * @return comparator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparator;
	}

}
