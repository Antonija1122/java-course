package hr.fer.zemris.java.custom.collections.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;
import hr.fer.zemris.java.custom.collections.Tester;

public class Demo {
	
	//testovi iz prosle zadace. Odkomentirajte jedan po jedan main za provjeru test po test 

//	public static void main(String[] args) {
//		Collection<String> col = new ArrayIndexedCollection<>();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter<String> getter = col.createElementsGetter();
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		}
//	

//	public static void main(String[] args) {
//		Collection<String> col = new LinkedListIndexedCollection<>();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter<String> getter = col.createElementsGetter();
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//	}

//	public static void main(String[] args) {
//		Collection<String> col = new ArrayIndexedCollection<>();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter<String> getter = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//	}

	
	
//	// Ivo, Ana, Ivo, Jasna, Ana
	
//	public static void main(String[] args) {
//		Collection<String> col = new LinkedListIndexedCollection<>();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter<String> getter1 = col.createElementsGetter();
//		ElementsGetter<String> getter2 = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
//	}

	
	
//	//Ispis treba biti: Ivo, Ana, Ivo, Jasmina, Štefanija
	
//	public static void main(String[] args) {
//		Collection<String> col1 = new ArrayIndexedCollection<>();
//		Collection<String> col2 = new ArrayIndexedCollection<>();
//		col1.add("Ivo");
//		col1.add("Ana");
//		col1.add("Jasna");
//		col2.add("Jasmina");
//		col2.add("Štefanija");
//		col2.add("Karmela");
//		ElementsGetter<String> getter1 = col1.createElementsGetter();
//		ElementsGetter<String> getter2 = col1.createElementsGetter();
//		ElementsGetter<String> getter3 = col2.createElementsGetter();
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
//	}

	
	
//	//Program treba ispisati Ivo, Ana pa se raspasti s iznimkom ConcurrentModificationExceptio
	
//	public static void main(String[] args) {
//		Collection<String> col = new ArrayIndexedCollection<>();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter<String> getter = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		col.clear();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		}

	
	
//	//Program treba ispisati Ivo, Ana pa se raspasti s iznimkom ConcurrentModificationException
	
//	public static void main(String[] args) {
//		Collection<String> col = new LinkedListIndexedCollection<>();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter<String> getter = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		col.clear();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		}

	
	
//	//ispis: Ana, Jasna
	
//	public static void main(String[] args) {
//		Collection<String> col = new ArrayIndexedCollection<>();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter<String> getter = col.createElementsGetter();
//		getter.getNextElement();
//		getter.processRemaining(System.out::println);
//	}

	
	
//	public static void main(String[] args) {
//		
//		//jedan od primjera iz prosle dz
//		
//		Collection<String> col = new ArrayIndexedCollection<String>();
//		//System.out.println(((ArrayIndexedCollection)col).get(0)); //kolakcija je prazna pa iznimku baca
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		
//		System.out.println(Arrays.toString(col.toArray()));
//		ElementsGetter<String> getter = col.createElementsGetter();
//		getter.getNextElement();
//		getter.processRemaining(System.out::println);
//		
//	}

}