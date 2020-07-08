package hr.fer.zemris.java.hw05.db;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Class StudentDatabase has internal List of student records and also a map of
 * the same records.
 * 
 * @author antonija
 *
 */
public class StudentDatabase {

	/**
	 * Reference to input list of strings
	 */
	List<String> studentStrings;

	/**
	 * Internal list of StudentRecord
	 */
	List<StudentRecord> studentRecords;

	/**
	 * Internal map of StudentRecord, key is String representation of students jmbag
	 * 
	 */
	Map<String, StudentRecord> studentRecordsMap;

	/**
	 * Constructor creates internal list and map from input list of strings
	 * 
	 * @param list input list
	 * @throws FileNotFoundException
	 */
	public StudentDatabase(List<String> list) throws FileNotFoundException {
		this.studentStrings = list;
		this.studentRecords = createStudenRecordsList(list);
		this.studentRecordsMap = createStudenRecordsMap(studentRecords);

	}

	/**
	 * Private method createStudenRecordsList() creates new list of student records
	 * from input list of strings and returns it
	 * 
	 * @param lista input list
	 * @return list of student records
	 */
	private List<StudentRecord> createStudenRecordsList(List<String> lista) {
		List<StudentRecord> helper = new ArrayList<>();

		Iterator<String> iter = studentStrings.iterator();
		String student;
		String[] current;

		while (iter.hasNext()) {

			student = iter.next();
			current = student.split("\\s+");
			if (current.length == 4) {
				StudentRecord s = new StudentRecord(current[0], current[1], current[2], Integer.parseInt(current[3]));
				helper.add(s);
			} else if (current.length == 5) {
				StudentRecord s = new StudentRecord(current[0], current[1] + " " + current[2], current[3],
						Integer.parseInt(current[4]));
				helper.add(s);
			} else {
				System.out.println(student);
				throw new IllegalArgumentException();
			}
		}

		return helper;

	}

	/**
	 * Private method createStudenRecordsMap() creates new map with jmbag key of
	 * student records from input list of strings and returns it
	 * 
	 * @param lista input list
	 * @return map of student records
	 */
	private Map<String, StudentRecord> createStudenRecordsMap(List<StudentRecord> lista) {

		Map<String, StudentRecord> mapa = lista.stream()
				.collect(Collectors.toMap(StudentRecord::getJmbag, UnaryOperator.identity()));
		return mapa;
	}

	/**
	 * Getter method for StudentRecord from students jmbag
	 * 
	 * @param jmbag input jmbag of student
	 * @return StudentRecord
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentRecordsMap.get(jmbag);
	}

	/**
	 * Method returns list of student records that satisfied conditions from input
	 * filter.
	 * 
	 * @param filter instance of IFilter
	 * @return list of students record 
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> helper = new ArrayList<>();
		Iterator<StudentRecord> iter = studentRecords.iterator();
		StudentRecord student;
		while (iter.hasNext()) {
			student = iter.next();
			if (filter.accepts(student)) {
				helper.add(student);
			}
		}
		return helper;
	}
}
