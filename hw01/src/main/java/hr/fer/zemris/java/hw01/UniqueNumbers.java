package hr.fer.zemris.java.hw01;
/**
 * Program u glavnom programu korisniku omogućava da unosi brojeve pomoću tipkovnice 
 * i šalje odgovarajuće poruke ako je broj dodan.
 * Brojeve slaže u binarno stablo od najmanjeg s lijeva do najvećeg s desna. 
 * Ako broj već postoji kao čvor stabla također se ispisuje odgovarajuća poruka.  
 * Unosom s tipkovnice "kraj" na ekran se ispisuju svi čvorovi stabla od najmanjeg do najvećeg i obrnuto. 
 * @author Antonija Ćesić
 * @version 1.0
 */

import java.util.Scanner;

public class UniqueNumbers {
	
	/**
	 * Klasa koja se sastoji od vrijednosti value i pokazivača na dva djeteta, lijevo i desno
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	static class MyBoolean {
		boolean value = false;
	}

	/**
	 * Metoda se poziva pokretanjem programa
	 * 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {

		TreeNode korijen = null;
		Scanner sc = new Scanner(System.in);
		MyBoolean kraj = new MyBoolean();

		while (true) {
			int newNode = ucitavanjeNoda(korijen, sc, kraj);
			if (!kraj.value) {
				if (!cointainsValue(korijen, newNode)) {
					korijen = addNode(korijen, newNode);
					System.out.println("Dodano.");
				} else {
					System.out.println("Broj već postoji. Preskačem.");
				}
			} else {
				break;
			}
		}
		sc.close();
	}

	/**
	 * Metoda dodaje argument newNode u odgovarajući čvor u stablu, lijevo manje, a
	 * desno veće vrijednosti.
	 * 
	 * @param korijen pokazivač na korijen stabla
	 * @param newNode novi čvor koji funkcija dodaje u stablo
	 * @return rezultat funkcije je novo stablo sa dodanom vrijednosti
	 */
	public static TreeNode addNode(TreeNode korijen, int newNode) {
		TreeNode korijenNovi = new TreeNode();
		if (korijen == null) {
			korijenNovi.value = newNode;
			korijenNovi.left = null;
			korijenNovi.right = null;
			return korijenNovi;
		} else if (newNode > korijen.value) {
			korijen.right = addNode(korijen.right, newNode);
		} else if (newNode < korijen.value) {
			korijen.left = addNode(korijen.left, newNode);
		}
		return korijen;

	}

	/**
	 * Metoda računa veličinu stabla tj. broj čvorova
	 * 
	 * @param korijen pokazivač na korijen stabla
	 * @return funkcija vraća broj čvorova
	 */
	public static int treeSize(TreeNode korijen) {
		if (korijen == null) {
			return 0;
		}
		return 1 + treeSize(korijen.left) + treeSize(korijen.right);
	}

	/**
	 * Metoda printa sve čvorove stabla od najmanjeg do najvećeg
	 * 
	 * @param korijen pokazivač na korijen stabla
	 */
	public static void printajOdNajmanjeg(TreeNode korijen) {
		if (korijen == null) {
			return;
		}
		printajOdNajmanjeg(korijen.left);
		System.out.printf("%d ", korijen.value);
		printajOdNajmanjeg(korijen.right);
	}

	/**
	 * Metoda printa sve čvorove stabla od najvećeg do najmanjeg
	 * 
	 * @param korijen pokazivač na korijen stabla
	 */
	public static void printajOdNajveceg(TreeNode korijen) {
		if (korijen == null) {
			return;
		}
		printajOdNajveceg(korijen.right);
		System.out.printf("%d ", korijen.value);
		printajOdNajveceg(korijen.left);
	}

	/**
	 * Metoda provjerava nalazi li se argument newNode već u stablu.
	 * 
	 * @param korijen pokazivač na korijen stabla
	 * @param newNode nova vrijednost
	 * @return vraća se boolean vrijednost True ili False ukoliko čvor već postoji
	 *         tj. ne postoji
	 */
	public static boolean cointainsValue(TreeNode korijen, int newNode) {
		if (korijen == null) {
			return false;
		} else if (newNode == korijen.value) {
			return true;
		} else {
			return (cointainsValue(korijen.right, newNode) || cointainsValue(korijen.left, newNode));
		}

	}

	/**
	 * Metoda omogućava korisniku unos novog čvora. Metoda se vrti sve dok korisnik
	 * ispravno ne unese cijeli broj ili upiše "kraj". Ako upiše kraj postavlja se
	 * globalna zastavica kraj i ispisuje se cijelo stablo od najmanje do najveće
	 * vrijednosti i obrnuto.
	 * 
	 * @param korijen pokazivač na korijen stabla
	 * @param sc      Scanner
	 * @param kraj    globalna zastavica koja označava kraj programa
	 * @return vraća int vrijednost koju je korisnik unio preko tipkovnice
	 */
	public static int ucitavanjeNoda(TreeNode korijen, Scanner sc, MyBoolean kraj) {
		int current;
		while (true) {
			System.out.printf("Unesite broj > ");
			if (sc.hasNextInt()) {
				current = sc.nextInt();
				return current;
			} else {
				String elem = sc.next();
				if (elem.equals("kraj")) {
					kraj.value = true;
					System.out.printf("Ispis od najmanjeg: ");
					printajOdNajveceg(korijen);
					System.out.printf("%n");
					System.out.printf("Ispis od najveceg: ");
					printajOdNajmanjeg(korijen);
					System.out.printf("%n");
					return 0;
				} else {
					System.out.printf("'%s' nije cijeli broj!%n", elem);
				}
			}
		}

	}

}
