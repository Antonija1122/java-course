package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;
import hr.fer.zemris.java.custom.collections.Tester;

public class Demo2 {

	public static void main(String[] args) {
		
		class EvenIntegerTester<T> implements Tester<T> {
			 public boolean test(Object obj) {
			 if(!(obj instanceof Integer)) return false;
			 Integer i = (Integer)obj;
			 return i % 2 == 0;
			 }
			}


		//testovi za prvi zadatak...nastavak
		
		//false, true, false
			Tester<Object> t = new EvenIntegerTester<>();
			System.out.println(t.test("Ivo"));
			System.out.println(t.test(22));
			System.out.println(t.test(3));
	}

//	public static void main(String[] args) {
//		
//		//12, 2, 4, 6
//
//		class EvenIntegerTester<T> implements Tester<T> {
//			public boolean test(Object obj) {
//				if (!(obj instanceof Integer))
//					return false;
//				Integer i = (Integer) obj;
//				return i % 2 == 0;
//			}
//		}
//
//		Collection<Integer> col1 = new LinkedListIndexedCollection<>();
//		Collection<Integer> col2 = new ArrayIndexedCollection<>();
//		col1.add(2);
//		col1.add(3);
//		col1.add(4);
//		col1.add(5);
//		col1.add(6);
//		col2.add(12);
//		col2.addAllSatisfying(col1, new EvenIntegerTester<Integer>());
//		col2.forEach(System.out::println);
//	}

//	// Dio koji se prije nije mogao prevesti je sada napisan ispravno
//	public static void main(String[] args) {
//		List<String> col1 = new ArrayIndexedCollection<>();
//		List<String> col2 = new LinkedListIndexedCollection<>();
//		col1.add("Ivana");
//		col2.add("Jasna");
//		Collection<String> col3 = col1;
//		Collection<String> col4 = col2;
//		col1.get(0);
//		col2.get(0);
//
//		((ArrayIndexedCollection<String>) col3).get(0); // neće se prevesti! Razumijete li zašto?
//		((LinkedListIndexedCollection<String>) col4).get(0); // neće se prevesti! Razumijete li zašto?
//
//		col1.forEach(System.out::println); // Ivana
//		col2.forEach(System.out::println); // Jasna
//		col3.forEach(System.out::println); // Ivana
//		col4.forEach(System.out::println); // Jasna
//
//	}

}
