package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class DemoIteratorConcurrent {

	public static void main(String[] args) {

		
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);

		//nadodano
		examMarks.put("Luka", 2);
		examMarks.put("Andrija", 1);

		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		

		System.out.println(examMarks);


		// zadnji primjer iz dz

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		
		examMarks.put("Kristina", 4); // nije doslo do promjene size-a pa nema iznimke 

		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());

	}

}
