package vakcinac.io.core.models.os;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "informacije-o-primljenim-dozama-iz-potvrde")
@XmlAccessorType(XmlAccessType.FIELD)
public class InformacijeOPrimljenimDozamaIzPotvrde {
    @XmlElement(name = "zdravstvena-ustanova")
    protected String zdravstvenaUstanova;

    @XmlElement(name = "naziv-vakcine")
    protected String nazivVakcine;

    @XmlElementWrapper(name = "podaci-o-dozama")
    @XmlElement(name = "doza")
    private List<PrimljenaDozaIzPotvrde> primljenaDozaIzPotvrde;

    public String getZdravstvenaUstanova() {
        return zdravstvenaUstanova;
    }

    public void setZdravstvenaUstanova(String zdravstvenaUstanova) {
        this.zdravstvenaUstanova = zdravstvenaUstanova;
    }

    public String getNazivVakcine() {
        return nazivVakcine;
    }

    public void setNazivVakcine(String nazivVakcine) {
        this.nazivVakcine = nazivVakcine;
    }

    public List<PrimljenaDozaIzPotvrde> getPrimljenaDozaIzPotvrde() {
        return primljenaDozaIzPotvrde;
    }

    public void setPrimljenaDozaIzPotvrde(List<PrimljenaDozaIzPotvrde> primljenaDozaIzPotvrde) {
        this.primljenaDozaIzPotvrde = primljenaDozaIzPotvrde;
    }
}
