package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program korisniku omogućava da unosi brojeve i zatim
 * računa faktorijele unesenih brojeva sve dok korisnik ne unese riječ "kraj".
 * 
 * @author Antonija Ćesić
 * @version 1.0
 */
public class Factorial {

	/**
	 * Metoda koja se poziva pokretanjem programa.
	 * @param args argumenti iz komandne linje
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String kraj = "kraj";

		while (true) {
			System.out.printf("Unesite broj >");
			if (sc.hasNextInt()) {
				long current = sc.nextLong();
				// long faktorijel=1;
				if (current < 3 || current > 20) {
					System.out.format("Broj %d nije u odgovarajućem rasponu - zanemarit ćemo ga.%n", current);
				} else {
					long faktorijel = izracunFaktorijela(current);
					System.out.format("%d! = %d%n", current, faktorijel);
				}
			} else {
				String elem = sc.next();
				if (elem.equals(kraj)) {
					break;
				}
				System.out.format("'%s' nije cijeli broj%n", elem);
			}
		}

		System.out.printf("Doviđenja!");

		sc.close();
	}

	/**
	 * 
	 * @param a argument funkcije
	 * @return iznos funkcije tj. faktorijel ulaznog argumenta a
	 */
	public static long izracunFaktorijela(long a) {

		if (a < 0) {
			return -1;
		}
		long fakt = 1;

		for (int i = 1; i <= a; i++) {
			fakt *= i;
			if (fakt < 0) {
				return 0;
			}
		}
		return fakt;
	}

}
