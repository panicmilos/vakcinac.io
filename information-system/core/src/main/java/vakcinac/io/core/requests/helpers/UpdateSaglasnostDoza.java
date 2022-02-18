package vakcinac.io.core.requests.helpers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateSaglasnostDoza {

    @XmlElement(name = "nacin-davanja-vakcine")
    protected String nacinDavanjaVakcine;

    @XmlElement
    protected Integer ekstremitet;

    @XmlElement(name = "serija-vakcine")
    protected String serijaVakcine;

    @XmlElement(name = "nezeljena-reakcija")
    protected String nezeljenaReakcija;
    
    public UpdateSaglasnostDoza() {}

	public String getNacinDavanjaVakcine() {
		return nacinDavanjaVakcine;
	}

	public void setNacinDavanjaVakcine(String nacinDavanjaVakcine) {
		this.nacinDavanjaVakcine = nacinDavanjaVakcine;
	}

	public Integer getEkstremitet() {
		return ekstremitet;
	}

	public void setEkstremitet(Integer ekstremitet) {
		this.ekstremitet = ekstremitet;
	}

	public String getSerijaVakcine() {
		return serijaVakcine;
	}

	public void setSerijaVakcine(String serijaVakcine) {
		this.serijaVakcine = serijaVakcine;
	}

	public String getNezeljenaReakcija() {
		return nezeljenaReakcija;
	}

	public void setNezeljenaReakcija(String nezeljenaReakcija) {
		this.nezeljenaReakcija = nezeljenaReakcija;
	}
}
