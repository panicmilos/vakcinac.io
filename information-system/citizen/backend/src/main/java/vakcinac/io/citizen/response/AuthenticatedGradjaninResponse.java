package vakcinac.io.citizen.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authenticated-gradjanin")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthenticatedGradjaninResponse {
	
    @XmlElement(name = "korisnicko-ime")
	private String korisnickoIme;
    
    @XmlElement(name = "jwt")
  	private String jwt;
    
    public AuthenticatedGradjaninResponse() {}
    
    public AuthenticatedGradjaninResponse(String korisnickoIme, String jwt) {
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
