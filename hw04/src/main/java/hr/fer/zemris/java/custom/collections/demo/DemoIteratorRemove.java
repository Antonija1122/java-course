package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class DemoIteratorRemove {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Luka", 5);


		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		

		System.out.println(examMarks);

		// prvi primjer iz zadace za remove() - dodala sam da mkne i luku i na kraju sve
		// ispise pomocu iteratora

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
			if (pair.getKey().equals("Luka")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		
		
		// sljedeci primjer s iznimkom

//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove();
//				System.out.println("Maknuta je Ivana ->" + examMarks);
//
//				iter.remove();
//			}
//		}

		// zadnji primjer iz dz

//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		
//		examMarks.put("Kristina", 4); // nije doslo do promjene size-a pa nema iznimke 
//
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//			iter.remove();
//		}
//		System.out.printf("Veličina: %d%n", examMarks.size());

		
		
	}

}
