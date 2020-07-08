package hr.fer.zemris.java.hw05.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * When started, program reads the data from current directory from file
 * database.txt. If in provided file there are duplicate jmbags, or if
 * finalGrade is not a number between 1 and 5, program should terminate with
 * appropriate message to user.
 * Program communicates with user from console.
 * User inputs query command and program returns information about students who
 * satisfy input request. Program is terminated when user writes "exit"
 * 
 * @author antonija
 *
 */
public class StudentDB {

	public static void main(String[] args) throws IOException {

		StudentDB.checkJmbagsAndGrades();

		List<String> lines = Files.readAllLines(Paths.get("src\\database"), StandardCharsets.UTF_8);

		Scanner sc = new Scanner(System.in);

		QueryParser parser;

		while (true) {

			System.out.printf("> ");
			if (sc.hasNextLine()) {
				String nextQuery = sc.nextLine();

				if (nextQuery.equals("exit")) {
					System.out.println("Goodbye!");
					break;

				} else {
					try {
						nextQuery = removeQuery(nextQuery);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}

					parser = new QueryParser(nextQuery);
					try {

						List<ConditionalExpression> conditions = parser.getQuery();
						List<StudentRecord> students = findStudents(conditions, lines);
						List<String> output = format(students, parser);
						print(output);
						System.out.println();

					} catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}
				}
			}
		}
		sc.close();
	}

	/**
	 * Method prints every line from input list of strings
	 * 
	 * @param output
	 */
	private static void print(List<String> output) {

		for (String line : output) {
			System.out.println(line);
		}

	}

	/**
	 * Method returns list of student records that satisfy conditions from user.
	 * 
	 * @param conditions
	 * @param allStudents
	 * @return
	 * @throws FileNotFoundException
	 */
	private static List<StudentRecord> findStudents(List<ConditionalExpression> conditions, List<String> allStudents)
			throws FileNotFoundException {

		StudentDatabase students = new StudentDatabase(allStudents);
		List<StudentRecord> allRecords = students.filter(new FilterTrue());

		List<StudentRecord> studAccepted = new ArrayList<>();

		QueryFilter filter = new QueryFilter(conditions);

		for (StudentRecord stud : allRecords) {
			if (filter.accepts(stud)) {
				studAccepted.add(stud);
			}
		}
		return studAccepted;
	}

	/**
	 * Method removes "query" from input expression. If there is no query in input
	 * beginning exception is thrown.
	 * 
	 * @param line
	 * @return input expression with "query" removed
	 */
	private static String removeQuery(String line) {

		line = line.trim();
		String[] array = line.split("\\s+");
		String queryRemoved = "";

		if (array[0].equals("query")) {

			for (int i = 1; i < array.length; i++) {
				queryRemoved = queryRemoved + " " + array[i];
			}
		} else {
			throw new IllegalArgumentException("Invalid input of query command. \"query\" ia mssing");
		}
		return queryRemoved;
	}

	/**
	 * Method creates new list of strings from input list of student records and
	 * formats lines for input.
	 * 
	 * @param students
	 * @param parser
	 * @return
	 */
	private static List<String> format(List<StudentRecord> students, QueryParser parser) {

		List<String> output = new ArrayList<String>();

		if (parser.isDirectQuery()) {
			output.add("Using index for record retrieval.");
		}

		int lengthJmbag = 0;
		int lengthLastName = 0;
		int lengthName = 0;

		for (StudentRecord s : students) {

			if (s.getJmbag().length() > lengthJmbag) {
				lengthJmbag = s.getJmbag().length();
			}
			if (s.getLastName().length() > lengthLastName) {
				lengthLastName = s.getLastName().length();
			}
			if (s.getName().length() > lengthName) {
				lengthName = s.getLastName().length();
			}
		}

		String firstLine = "+" + "=".repeat(lengthJmbag + 2) + "+" + "=".repeat(lengthLastName + 2) + "+"
				+ "=".repeat(lengthName + 2) + "+";
		if (students.size() != 0) {
			output.add(firstLine);
		}
		lengthJmbag = lengthJmbag + 2;
		lengthLastName = lengthLastName + 2;
		lengthName = lengthName + 2;

		for (StudentRecord s : students) {
			String student = "| " + s.getJmbag() + " ".repeat(lengthJmbag - s.getJmbag().length() - 1) + "| "
					+ s.getLastName() + " ".repeat(lengthLastName - s.getLastName().length() - 1) + "| " + s.getName()
					+ " ".repeat(lengthName - s.getName().length() - 1) + "|";

			output.add(student);
		}
		if (students.size() != 0) {
			output.add(firstLine);
		}
		output.add("Records selected: " + students.size());

		return output;
	}

	/**
	 * Class implements IFilter and returns true for every input StudentRecord. This
	 * way StudentDatabase creates List of all student records.
	 * 
	 * @author antonija
	 *
	 */
	static class FilterTrue implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
	}

	/**
	 * This method checks if all students have different jmbags and grades in range (1, 5); 
	 * @throws FileNotFoundException if input file is not found
	 */
	public static void checkJmbagsAndGrades() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("src\\database"));
		List<String> lines = new ArrayList<String>();
		String currentLine;
		String[] lineArray;
		String jmbag;
		int grade;

		while (sc.hasNextLine()) {

			currentLine = sc.nextLine();
			lineArray = currentLine.split("\\s+");
			jmbag = lineArray[0];
			grade = Integer.parseInt(lineArray[lineArray.length - 1]);

			if (lines.contains(jmbag)) {
				System.out.println("Jmbag " + jmbag + " is repeated. Error.");
				System.exit(0);
			}
			if (grade < 1 || grade > 5) {
				System.out.println("Student " + jmbag + " has illegal grade. Error.");
				System.exit(0);
			}
			lines.add(jmbag);
		}
		sc.close();
	}

}
