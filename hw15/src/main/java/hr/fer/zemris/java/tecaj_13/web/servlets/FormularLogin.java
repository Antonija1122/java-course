package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.Utility;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Model formulara koji odgovara web-reprezentaciji login akcije
 * </p>
 * 
 * <p>
 * Za svako svojstvo, mapa {@link #greske} omogućava da se pri validaciji
 * (metoda {@link #validiraj()}) upiše je li došlo do pogreške u podatcima.
 * Formular nudi sljedeće funkcionalnosti.
 * </p>
 * 
 * 
 * @author marcupic
 */
public class FormularLogin {

	/**
	 * nick osobe.
	 */
	private String nick;

	/**
	 * E-mail osobe.
	 */
	private String password;

	/**
	 * Mapa s pogreškama. Očekuje se da su ključevi nazivi svojstava a vrijednosti
	 * tekstovi pogrešaka.
	 */
	Map<String, String> greske = new HashMap<>();

	/**
	 * Konstruktor.
	 */
	public FormularLogin() {
	}

	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu
	 *         pogrešku
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}

	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}

	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku.
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}

	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req objekt s parametrima
	 * @throws NoSuchAlgorithmException
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) throws NoSuchAlgorithmException {
		this.nick = pripremi(req.getParameter("nick"));
		this.password = pripremi(req.getParameter("password"));
	}

	/**
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r objekt koji čuva originalne podatke
	 */
	public void popuniIzRecorda(BlogUser r) {

		this.nick = r.getNick();
		this.password = r.getPasswordHash();
	}

	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način
	 * potrebno napuniti. Metoda provjerava semantičku korektnost svih podataka te
	 * po potrebi registrira pogreške u mapu pogrešaka.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void validiraj() throws NoSuchAlgorithmException {
		greske.clear();

		if (this.nick.isEmpty()) {
			greske.put("nick", "Upisite nick!");
		} else if (!nickExist()) {
			greske.put("nick", "Nickname ne postoji u bazi podataka");
		}

		if (this.password.isEmpty()) {
			greske.put("password", "Upisite password!");
		} else if (WrongPassword()) {
			greske.put("password", "Wrong Password");
		}

	}

	/**
	 * This method checks if password is wrong
	 * 
	 * @return true is password is wrong false otherwise
	 * @throws NoSuchAlgorithmException
	 */
	private boolean WrongPassword() throws NoSuchAlgorithmException {
		System.out.println(password + "ovo je od objekta ");
		if (nickExist()) {
			BlogUser user = DAOProvider.getDAO().getUser(this.nick);
			System.out
					.println("ovo je od spremljenog" + user.getPasswordHash() + Utility.getPaswordHash(this.password));
			if (user.getPasswordHash().equals(Utility.getPaswordHash(this.password))) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * This method checks if user with nick nick exists
	 * 
	 * @return
	 */
	private boolean nickExist() {
		List<BlogUser> users = DAOProvider.getDAO().getUsers();
		for (BlogUser user : users) {
			if (user.getNick().equals(this.nick)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne
	 * stringove, što je puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string
	 *         inače.
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick input nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password
	 * 
	 * @param password input password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
