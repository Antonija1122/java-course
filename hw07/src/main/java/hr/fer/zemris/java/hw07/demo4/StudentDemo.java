package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Razred StudentDemo u glavnom programu ispisuje rezultate zadataka 1-8 sedme
 * zadace u zadanom formatu: 
 * Zadatak 1 
 * ========= 
 * xx 
 * Zadatak 2 
 * ========= 
 * xx
 * 
 * @author antonija
 *
 */
public class StudentDemo {

	public static void main(String[] args) {

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/studenti.txt"));
		} catch (IOException e) {
			System.out.println("Unable to find input file");
			System.exit(1);
		}
		List<StudentRecord> records = convert(lines);

		System.out.printf("Zadatak 1%n" + "=".repeat(9) + "%n");
		System.out.println("Broj studenata koji imaju ukupno više od 25 bodova : " + vratiBodovaViseOd25(records));

		System.out.printf("%nZadatak 2%n" + "=".repeat(9) + "%n");
		System.out.println("Broj odlikaša : " + vratiBrojOdlikasa(records));

		System.out.printf("%nZadatak 3%n" + "=".repeat(9) + "%n");
		System.out.println("Popis studenata s odličnom ocjenom : ");
		vratiListuOdlikasa(records).forEach(System.out::println);

		System.out.printf("%nZadatak 4%n" + "=".repeat(9) + "%n");
		System.out.println(
				"Popis studenata s odličnom ocjenom, sortiran od studenta s najvećim do studenta s najmanjim brojem bodova: ");
		vratiSortiranuListuOdlikasa(records).forEach(System.out::println);

		System.out.printf("%nZadatak 5%n" + "=".repeat(9) + "%n");
		System.out.println("Studenti koji nisu položili predmet: ");
		vratiPopisNepolozenih(records).forEach(System.out::println);

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.printf("%nZadatak 6%n" + "=".repeat(9) + "%n");
		for (Map.Entry<Integer, List<StudentRecord>> entry : mapaPoOcjenama.entrySet()) {
			System.out.println("Popis studenata s ocjenom " + entry.getKey() + " : ");
			entry.getValue().forEach(System.out::println);
		}

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.printf("%nZadatak 7%n" + "=".repeat(9) + "%n");
		for (Map.Entry<Integer, Integer> entry : mapaPoOcjenama2.entrySet()) {
			System.out.println("Broj studenata s ocjenom " + entry.getKey() + " je : " + entry.getValue());
		}

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.printf("%nZadatak 8%n" + "=".repeat(9) + "%n");
		System.out.println("Studenti s prolaznom ocjenom : ");
		prolazNeprolaz.get(true).forEach(System.out::println);

		System.out.println("Studenti koji su pali : ");
		prolazNeprolaz.get(false).forEach(System.out::println);
	}

	/**
	 * Metoda vraća mapu s ključevima true/false i vrijednostima koje su liste
	 * studenata koji su prošli (za ključ true) odnosno koji nisu prošli kolegij (za
	 * ključ false).
	 * 
	 * @param records ukupna lista studenata
	 * @return mapu s ključevima true/false (prolaz/ne prolaz)
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(stud -> stud.getGrade() > 1));
	}

	/**
	 * Metoda vraća mapu čiji su ključevi ocjene, a vrijednosti broj studenata s tim
	 * ocjenama. Za ovo je korištena sljedeća metodu razreda Collectors:
	 * Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
	 * Function<? super T, ? extends U> valueMapper, BinaryOperator<U>
	 * mergeFunction)
	 * 
	 * @param records ukupna lista studenata
	 * @return mapa
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, stud -> 1, (stud1, stud2) -> stud1 + 1));
	}

	/**
	 * Metoda vraća mapu čiji su ključevi ocjene a vrijednosti liste studenata s tim
	 * ocjenama
	 * 
	 * @param records ukupna lista studenata
	 * @return mapa
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Metoda vraća listu JMBAG-ova studenata koji nisu položili kolegij, sortiranu
	 * prema JMBAG-u
	 * 
	 * @param records ukupna lista studenata
	 * @return sortirana lista JMBAG-ova studenata koji nisu položili kolegij
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(stud -> stud.getGrade() == 1)
				.sorted((stud1, stud2) -> stud1.getJmbag().compareTo(stud2.getJmbag())).map(stud -> stud.getJmbag())
				.collect(Collectors.toList());
	}

	/**
	 * Metoda vraća listu studenata koji su dobili ocjenu 5 pri čemu redosljed u
	 * listi mora biti takav da je na prvom mjestu student koji je ukupno ostvario
	 * najviše bodova a na zadnjem mjestu onaj koji je ukupno ostvario najmanje
	 * (dakle, sortirano po bodovima)
	 * 
	 * @param records ukupna lista studenata
	 * @return sortirana lista studenata koji su dobili ocjenu 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(stud -> stud.getGrade() == 5)
				.sorted((stud1, stud2) -> -Double.compare(stud1.getLAB() + stud1.getMI() + stud1.getZI(),
						stud2.getLAB() + stud2.getMI() + stud2.getZI()))
				.collect(Collectors.toList());
	}

	/**
	 * Metoda vraća listu studenata koji su dobili ocjenu 5 (redosljed u listi nije
	 * bitan)
	 * 
	 * @param records ukupna lista studenata
	 * @return lista odličnih studenata
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(stud -> stud.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Metoda vraća broj studenata koji su dobili ocjenu 5
	 * 
	 * @param records ukupna lista studenata
	 * @return broj odličnih studenata
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(stud -> stud.getGrade() == 5).count();
	}

	/**
	 * Metoda vraća broj studenata koji u sumi MI+ZI+LAB imaju više od 25 bodova
	 * 
	 * @param records ukupna lista studenata
	 * @return broj studenata s MI+ZI+LAB>25
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(stud -> stud.getMI() + stud.getZI() + stud.getLAB() > 25).count();
	}

	/**
	 * Metoda prima listu stringova s podacima o studentu. Metoda stvara
	 * StudentRecord razred za svakog studenta i sprema podatke u listu
	 * StudentRecord-a koja je na kraju vraćena pozivatelju funkcije. Ako postoji
	 * krivi opis studenata (fale neki podaci) bačena je iznimka
	 * IllegalArgumentException.
	 * 
	 * @param lines lista ulaznih stringova
	 * @return lista StudentRecord-a s podacima o svakom studentu.
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		String[] lineArgs;
		List<StudentRecord> students = new ArrayList<>();

		for (String line : lines) {
			lineArgs = line.split("\\s+");
			if (lineArgs.length != 7) {
				throw new IllegalArgumentException(
						"Error reading the file. Students " + lineArgs[0] + " informations are not correctly stored.");
			} else {
				students.add(new StudentRecord(lineArgs[0], lineArgs[1], lineArgs[2], Double.parseDouble(lineArgs[3]),
						Double.parseDouble(lineArgs[4]), Double.parseDouble(lineArgs[5]),
						Integer.parseInt(lineArgs[6])));
			}
		}
		return students;
	}

}
