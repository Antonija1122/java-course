package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void testFilterTrue() throws FileNotFoundException {
		StudentDatabase test=new StudentDatabase(checkJmbagsAndGrades());
		List<StudentRecord> list=test.filter(new FilterTrue());
		assertEquals(list.size(), 63);
	}
	
	
	@Test
	void testFilterFalse() throws FileNotFoundException {
		StudentDatabase test=new StudentDatabase(checkJmbagsAndGrades());
		List<StudentRecord> list=test.filter(new FilterFalse());
		assertEquals(list.size(), 0);
	}
	
	@Test
	void testForJMBAG() throws FileNotFoundException {
		StudentDatabase test=new StudentDatabase(checkJmbagsAndGrades());
		
		//0000000034	Majić	Diana	3
		
		StudentRecord student34=test.forJMBAG("0000000034");
		System.out.println(student34.getName());
		assertEquals(student34.getName(), "Diana");
		assertEquals(student34.getLastName(), "Majić");
		assertEquals(student34.getGrade(), 3);

	}

	
	class FilterTrue implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}

	}

	class FilterFalse implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
	}
	
	public static List<String> checkJmbagsAndGrades() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("src\\database"));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		sc.close();
		return lines;
		
	}
	
	
	
}
