package vakcinac.io.civil.servant.results.vak;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import vakcinac.io.civil.servant.models.vak.Vakcina;


@XmlRootElement(name = "vakcine")
@XmlAccessorType(XmlAccessType.FIELD)
public class VakcineResult {
	
    @XmlElement(name = "vakcina")
    private List<Vakcina> vakcina = new ArrayList<Vakcina>();

	public List<Vakcina> getVakcina() {
		return vakcina;
	}

	public void setVakcina(List<Vakcina> value) {
		this.vakcina = value;
	}

}
