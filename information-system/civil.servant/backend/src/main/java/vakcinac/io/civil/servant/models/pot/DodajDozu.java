package vakcinac.io.civil.servant.models.pot;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "add-doza-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class DodajDozu {

    @XmlElement(name = "jmbg")
    private String jmbg;

    @XmlElement
    private String serija;

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }
}
