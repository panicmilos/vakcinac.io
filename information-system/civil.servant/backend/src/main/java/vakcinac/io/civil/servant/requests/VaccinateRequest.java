package vakcinac.io.civil.servant.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "vaccinate-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class VaccinateRequest {
	
    @XmlValue
    private String jmbgOsobe;

	public String getJmbgOsobe() {
		return jmbgOsobe;
	}

	public void setJmbgOsobe(String jmbgOsobe) {
		this.jmbgOsobe = jmbgOsobe;
	}
    
}
