package vakcinac.io.civil.servant.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlRootElement(name = "sluzbenik")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateSluzbenikRequest {
	
	@XmlElement(name = "ime")
	private String ime;

    @XmlElement(name = "prezime")
	private String prezime;

    @XmlElement(name = "korisnicko-ime")
	private String korisnickoIme;

    @XmlElement(name = "lozinka")
	private String lozinka;

    @XmlElement(name = "datum-rodjenja")
	private XMLGregorianCalendar datumRodjenja;

    @XmlElement(name = "pol")
	private Integer pol;

    @XmlElement(name = "email")
	private String email;
    
    @XmlElement(name = "jmbg")
	private String jmbg;
    
	public CreateSluzbenikRequest() {}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public XMLGregorianCalendar getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(XMLGregorianCalendar datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Integer getPol() {
		return pol;
	}

	public void setPol(Integer pol) {
		this.pol = pol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
}
