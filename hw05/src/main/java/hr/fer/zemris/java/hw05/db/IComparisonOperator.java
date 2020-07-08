package hr.fer.zemris.java.hw05.db;

/**
 * Strategy IComparisonOperator defines one method
 * @author antonija
 *
 */
public interface IComparisonOperator {
	
	
	/**
	 * Method return true if input parameters satisfy certain conditions.
	 * Implemented here as an abstract method.
	 * @param value1 input string 
	 * @param value2 input string
	 * @return 
	 */
	public boolean satisfied(String value1, String value2);

}
