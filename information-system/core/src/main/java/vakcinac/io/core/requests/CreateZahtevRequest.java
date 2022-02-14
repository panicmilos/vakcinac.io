package vakcinac.io.core.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "zahtev-za-izdavanje-zelenog-sertifikata")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateZahtevRequest {

    @XmlElement(name = "podnosilac")
    private String podnosilac;

    @XmlElement(name = "razlog")
    private String razlog;
    
    @XmlElement(name = "mesto")
    private String mesto;

	public String getPodnosilac() {
		return podnosilac;
	}

	public void setPodnosilac(String podnosilac) {
		this.podnosilac = podnosilac;
	}

	public String getRazlog() {
		return razlog;
	}

	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

}
