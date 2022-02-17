package vakcinac.io.civil.servant.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "decline-zahtev")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeclineZahtevRequest {
	@XmlElement(name = "zahtev")
    private String zahtev;
	
	@XmlElement(name = "razlog")
	private String razlog;
	
	public DeclineZahtevRequest() {}

	public String getZahtev() {
		return zahtev;
	}

	public void setZahtev(String zahtev) {
		this.zahtev = zahtev;
	}

	public String getRazlog() {
		return razlog;
	}

	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}
}
