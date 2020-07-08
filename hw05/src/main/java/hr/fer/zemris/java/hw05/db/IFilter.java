package hr.fer.zemris.java.hw05.db;

/**
 * Strategy IFilter has one method that defines if input StudentRecord instance
 * should be filtered or accepted.
 * 
 * @author antonija
 *
 */
public interface IFilter {

	/**
	 * Method filters data by returning true if input StudentRecord is accepted and
	 * false if input has to be filtered. Implemented here as an abstract method.
	 * 
	 * @param record
	 * @return
	 */
	public boolean accepts(StudentRecord record);

}
