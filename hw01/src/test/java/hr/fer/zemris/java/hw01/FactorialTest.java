package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testiranje funkcije za izračun faktorijela iz razreda Factorial
 * 
 * @author antonija
 *
 */
class FactorialTest {

	@Test
	public void izracunFaktorijelaNaOdgovarajucemIntervalu() {

		long num = Factorial.izracunFaktorijela(4);
		assertEquals(24, num);
		if (num == -1) {
			throw new IllegalArgumentException("Unesen je negativan broj!");
		}

	}

	@Test
	public void negativanBroj() {

		long num = Factorial.izracunFaktorijela(-1);
		if (num == -1) {
			throw new IllegalArgumentException("Unesen je negativan broj!");
		}

	}

	@Test
	public void izracunZaPrevelikBroj() {

		// Factorial test2 = new Factorial();

		long num = Factorial.izracunFaktorijela(23);
		if (num == 0) {
			throw new IllegalArgumentException("Unesen je prevelik broj!");
		}

	}
}
