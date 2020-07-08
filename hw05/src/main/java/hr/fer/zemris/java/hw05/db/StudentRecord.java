package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class StudentRecord has internal String variables for jmbag, lastName, name
 * and int varible for grade. Instances of this class represent records for each
 * student
 * 
 * @author antonija
 *
 */
public class StudentRecord {

	/**
	 * private String value of students jmbag
	 */
	private String jmbag;

	/**
	 * private String value of students lastName
	 */
	private String lastName;

	/**
	 * private String value of students name
	 */
	private String name;

	/**
	 * private int value of students grade
	 */
	private int grade;

	/**
	 * Constructor initializes private variables to input values
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param name
	 * @param grade
	 */
	public StudentRecord(String jmbag, String lastName, String name, int grade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.name = name;
		this.grade = grade;
	}

	/**
	 * Getter method fot jmbag
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter method for lastName
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter method for name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method fot jmbag
	 * @return jmbag
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	/**
	 * {@inheritDoc}
	 * Student records are equal if their jmbags are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return jmbag == other.jmbag;
	}

	/**
	 * String representation of StudentRecord
	 */
	@Override
	public String toString() {

		return jmbag + " " + lastName + " " + name + " " + grade;
	}

}
