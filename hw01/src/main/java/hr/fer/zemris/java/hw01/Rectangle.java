package hr.fer.zemris.java.hw01;

/**
 * Program u omogućuje korisniku unos širine i visine pravokutnika preko argumenata komandne linije 
 * i preko tipkovnice. U slučaju da korisnik unosi podatke preko komandne linije odmah računa opseg i
 * površinu i ispisuje ih. Ako argumente korisnik unosi podatke preko tipkovnice unosi ih sv dok ih ne 
 * unese ispravno i nakon toga program ispisuje opseg i površinu na ekran.  
 * @author Antonija Ćesić
 * @version 1.0
 */
import java.util.Scanner;

public class Rectangle {

	/**
	 * Metoda se poziva pokretanjem programa
	 * 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			Scanner sc = new Scanner(System.in);
			double sirina, visina;
			sirina = ucitavanjeStranice2("sirinu", sc);
			visina = ucitavanjeStranice2("visinu", sc);
			double opseg = opseg(sirina, visina);
			double povrsina = povrsina(sirina, visina);
			
			// pretvoriti u stringove da isprinta tocku a ne zarez
			String sirinatxt = Double.toString(sirina);
			String visinatxt = Double.toString(visina);
			String opsegtxt = Double.toString(opseg);
			String povrsinatxt = Double.toString(povrsina);
			
			System.out.printf("Pravokutnik sirine %s i visine %s ima povrsinu %s i opseg %s", sirinatxt, visinatxt,
					povrsinatxt, opsegtxt);
			sc.close();
		} else if (args.length == 2) {

			double sirina = Double.parseDouble(args[0]);
			double visina = Double.parseDouble(args[1]);
			double opseg = opseg(sirina, visina);
			double povrsina = povrsina(sirina, visina);
			System.out.printf("Pravokutnik sirine %.1f i visine %.1f ima povrsinu %.1f i opseg %.1f", sirina, visina,
					povrsina, opseg);

		} else {
			System.out.printf("Argumenti nisu dobro zadani");
		}
	}

	/**
	 * Metoda omogućava komunikaciju s korisnikom. 
	 * Korisnik mora unijeti pozitivan decimalni broj s točkom inače ponovo unosi sve dok ne unese broj točno.
	 * @param strDimenzija argument tipa string koji označava koja se dimenzija
	 *                     unosi
	 * @param sc scanner
	 * @return int vrijednost koju vraća funkcija tj. iznos dimenzije
	 */
	public static double ucitavanjeStranice2(String strDimenzija, Scanner sc) {
		while (true) {
			System.out.printf("Unesite %s pravokutnika >", strDimenzija);
			String text = sc.next(); // example String
			try {
				double current = Double.parseDouble(text);
				if (current>0) {
						return current;
				} else {
					System.out.println("Unijeli ste negativan broj.");
				}
			} catch(NumberFormatException ignore) {
				System.out.printf("'%s' se ne može protumačiti kao broj!%n", text);
			}
		}
	
	}
	

	/**
	 * Metoda vraća opseg pravokutnika s dimenzijama danim preko argumenata
	 * funkcije.
	 * 
	 * @param d debljina pravokutnika
	 * @param s širina pravokutnika
	 * @return opseg pravokutnika
	 */
	public static double opseg(double d, double s) {
		return 2 * d + 2 * s;
	}

	/**
	 * Metoda vraća površinu pravokutnika s dimenzijama danim preko argumenata
	 * funkcije.
	 * 
	 * @param d debljina pravokutnika
	 * @param s širina pravokutnika
	 * @return površina pravokutnika
	 */
	public static double povrsina(double d, double s) {
		return d * s;
	}

}