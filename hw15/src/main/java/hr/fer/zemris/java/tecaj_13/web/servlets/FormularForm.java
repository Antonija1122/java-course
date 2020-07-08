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
 * <p>Model formulara koji odgovara web-reprezentaciji domenskog objekta {@link BlogUser}.
 * U domenskom modelu, različita svojstva su različitih tipova; primjerice, {@link BlogUser#getId()}
 * je tipa {@link Long}. U formularu, sva su svojstva stringovi jer se tako prenose preko HTTP-a
 * i čuvaju u web-stranici u formularima.</p>
 * 
 * <p>Za svako svojstvo, mapa {@link #greske} omogućava da se pri validaciji (metoda {@link #validiraj()}) upiše 
 * je li došlo do pogreške u podatcima. Formular nudi sljedeće funkcionalnosti.</p>
 * 
 * <ol>
 * <li>Punjenje iz trenutnog zahtjeva metodom {@link #popuniIzHttpRequesta(HttpServletRequest)}. Čita parametre
 *     i upisuje odgovarajuća svojstva u formular.</li>
 * <li>Punjenje iz domenskog objekta metodom {@link #popuniIzRecorda(BlogUser)}. Prima {@link Record} kao argument
 *     i temeljem toga što je u njemu upisano popunjava ovaj formular.</li>
 * <li>Punjenje domenskog objekta temeljem upisanog sadržaja u formularu metodom {@link #popuniURecord(BlogUser)}.
 *     Ideja je da se ovo radi tek ako su podatci u formularu prošli validaciju. Pogledajte pojedine servlete koji
 *     su pripremljeni uz ovaj primjer za demonstraciju kako se to radi.</li>
 * </ol>
 * 
 * @author marcupic
 */
public class FormularForm {

	/**
	 * String verzija identifikatora.
	 */
	private String id;
	/**
	 * Prezime osobe.
	 */
	private String prezime;
	/**
	 * Ime osobe.
	 */
	private String ime;
	
	/**
	 * Ime osobe.
	 */
	private String nick;

	/**
	 * E-mail osobe.
	 */
	private String email;
	
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
	public FormularForm() {
	}

	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu pogrešku
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
		this.ime = pripremi(req.getParameter("ime"));
		this.prezime = pripremi(req.getParameter("prezime"));
		this.email = pripremi(req.getParameter("email"));
		this.nick=pripremi(req.getParameter("nick"));
		this.password=pripremi(req.getParameter("password"));
	}

	/**
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r objekt koji čuva originalne podatke
	 */
	public void popuniIzRecorda(BlogUser r) {
		
		this.ime = r.getFirstName();
		this.prezime = r.getLastName();
		this.nick = r.getNick();
		this.password = r.getPasswordHash();
		this.email = r.getEmail();
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
	 * @throws NoSuchAlgorithmException 
	 */
	public void popuniURecord(BlogUser r) throws NoSuchAlgorithmException {

		r.setFirstName(this.ime);
		r.setLastName(this.prezime);
		r.setEmail(this.email);
		r.setNick(this.nick);
		r.setPasswordHash(Utility.getPaswordHash(this.password));
	}
	
	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način potrebno
	 * napuniti. Metoda provjerava semantičku korektnost svih podataka te po potrebi
	 * registrira pogreške u mapu pogrešaka.
	 */
	public void validiraj() {
		greske.clear();
			
		if(this.ime.isEmpty()) {
			greske.put("ime", "Ime je obavezno!");
		} else if(this.ime.length()>50) {
			greske.put("ime", "Ime ne smije biti dulje od 50 znakova");
		}
		
		if(this.prezime.isEmpty()) {
			greske.put("prezime", "Prezime je obavezno!");
		} else if(this.prezime.length()>50) {
			greske.put("prezime", "Prezime ne smije biti dulje od 50 znakova");
		}
		
		if(this.nick.isEmpty()) {
			greske.put("nick", "Nickname je obavezno!");
		} else if(this.nick.length()>50) {
			greske.put("nick", "Nickname ne smije biti dulje od 50 znakova");
		} else if(nickAlreadyExists()) {
			greske.put("nick", "Nickname već postoji u bazi podataka");
		}
		
		if(this.password.isEmpty()) {
			greske.put("password", "password je obavezan!");
		} else if(this.password.length()>50 || this.password.length()<5) {
			greske.put("password", "password mora imati najmanje 5 i najviše 50 znakova!");
		}

		if(this.email.isEmpty()) {
			greske.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				greske.put("email", "EMail nije ispravnog formata.");
			}
		}
	}
	
	private boolean nickAlreadyExists() {
		List<BlogUser> users=DAOProvider.getDAO().getUsers();
		for(BlogUser user:users) {
			if(user.getNick().equals(this.nick)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne stringove, što je
	 * puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string inače.
	 */
	private String pripremi(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * Dohvat id-a.
	 * @return id
	 */
	public String getId() {
		return id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Setter za id. 
	 * @param id vrijednost na koju ga treba postaviti.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Dohvat prezimena.
	 * @return prezime
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Setter za prezime. 
	 * @param prezime vrijednost na koju ga treba postaviti.
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	/**
	 * Dohvat imena.
	 * @return ime
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Setter za ime. 
	 * @param ime vrijednost na koju ga treba postaviti.
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * Dohvat emaila.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter za email. 
	 * @param email vrijednost na koju ga treba postaviti.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
