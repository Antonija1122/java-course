package hr.fer.zemris.java.hw07.demo4;

/**
 * Class StudentRecord has internal String variables for jmbag, lastName, name,
 * double variable MI for results of mid-semester exam, double variable ZI for
 * results of mid-semester exam and double variable LAB for results of
 * Laboratory exercises. and int varible for grade. Instances of this class
 * represent records for each student
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
	 * private int value of students result in mid-semester exam
	 */
	private double MI;

	/**
	 * private int value of students result in end-semester exam
	 */
	private double ZI;
	/**
	 * private int value of students result in Laboratory exercises
	 */
	private double LAB;

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
	public StudentRecord(String jmbag, String lastName, String name, double MI, double ZI, double LAB, int grade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.MI = MI;
		this.ZI = ZI;
		this.LAB = LAB;
		this.name = name;
		this.grade = grade;
	}

	/**
	 * Getter method fot jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter method for lastName
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter method for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method for MI
	 * 
	 * @return MI
	 */
	public double getMI() {
		return MI;
	}

	/**
	 * Getter method for ZI
	 * 
	 * @return ZI
	 */
	public double getZI() {
		return ZI;
	}

	/**
	 * Getter method for LAB
	 * 
	 * @return LB
	 */
	public double getLAB() {
		return LAB;
	}

	/**
	 * Getter method fot jmbag
	 * 
	 * @return jmbag
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * String representation of StudentRecord
	 */
	@Override
	public String toString() {
		return jmbag + "\t" + lastName + "\t" + name + "\t" + MI + "\t" + ZI + "\t" + LAB + "\t" + grade;
	}

}
