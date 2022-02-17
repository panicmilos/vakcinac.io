package vakcinac.io.civil.servant.models.pot;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "create-potvrda-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class KreiranjePotvrde {

    @XmlElement(name = "jmbg")
    private String jmbgOsobe;

    @XmlElement
    private String serija;

    @XmlElement(name = "naziv-vakcine")
    private String nazivVakcine;

    public String getJmbgOsobe() {
        return jmbgOsobe;
    }

    public void setJmbgOsobe(String jmbgOsobe) {
        this.jmbgOsobe = jmbgOsobe;
    }

    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }

    public String getNazivVakcine() {
        return nazivVakcine;
    }

    public void setNazivVakcine(String nazivVakcine) {
        this.nazivVakcine = nazivVakcine;
    }
}
