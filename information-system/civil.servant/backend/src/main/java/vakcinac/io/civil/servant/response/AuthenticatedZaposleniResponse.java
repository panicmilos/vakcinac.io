package vakcinac.io.civil.servant.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authenticated-zaposleni")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthenticatedZaposleniResponse {
	
    @XmlElement(name = "korisnicko-ime")
	private String korisnickoIme;
    
    @XmlElement(name = "jwt")
  	private String jwt;
    
    public AuthenticatedZaposleniResponse() {}
    
    public AuthenticatedZaposleniResponse(String korisnickoIme, String jwt) {
    	setKorisnickoIme(korisnickoIme);
    	setJwt(jwt);
    }

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
