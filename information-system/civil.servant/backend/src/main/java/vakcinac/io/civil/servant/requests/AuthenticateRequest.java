package vakcinac.io.civil.servant.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authenticate")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthenticateRequest {
	
	@XmlElement(name = "korisnicko-ime")
	private String korisnickoIme;

    @XmlElement(name = "lozinka")
	private String lozinka;
    
    public AuthenticateRequest() {}

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
}
