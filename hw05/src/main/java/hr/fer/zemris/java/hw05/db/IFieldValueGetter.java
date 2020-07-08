package hr.fer.zemris.java.hw05.db;

/**
 * Strategy interface with one abstract method get();
 * @author antonija
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Method gets string value from input StudentRecord instance and returns it. 
	 * Implemented here as an abstract method
	 * @param record input StudentRecord
	 * @return
	 */
	public String get(StudentRecord record);


}
