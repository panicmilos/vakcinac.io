package vakcinac.io.civil.servant.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vakcina")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateVakcinaRequest {
	
    @XmlElement(name = "proizvodjac")
    private int proizvodjac;
    
    @XmlElement(name = "serija")
    private String serija;

	public int getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(int proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public String getSerija() {
		return serija;
	}

	public void setSerija(String serija) {
		this.serija = serija;
	}
    
}