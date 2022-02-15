package vakcinac.io.civil.servant.models.sag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "update-saglasnost-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AzurirajSaglasnost {

    @XmlElement(name = "saglasnost-id")
    private String saglasnostId;

    @XmlElement(name = "jmbg-lekara")
    private String jmbgLekara;

    @XmlElement
    private String dijagnoza;

    @XmlElement(name = "odluka-komisije")
    private Boolean odlukaKomisije;

    public String getSaglasnostId() {
        return saglasnostId;
    }

    public void setSaglasnostId(String saglasnostId) {
        this.saglasnostId = saglasnostId;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public Boolean getOdlukaKomisije() {
        return odlukaKomisije;
    }

    public void setOdlukaKomisije(Boolean odlukaKomisije) {
        this.odlukaKomisije = odlukaKomisije;
    }

    public String getJmbgLekara() {
        return jmbgLekara;
    }

    public void setJmbgLekara(String jmbgLekara) {
        this.jmbgLekara = jmbgLekara;
    }

    @Override
    public String toString() {
        return "AzurirajSaglasnost{" +
                "saglasnostId='" + saglasnostId + '\'' +
                ", jmbgLekara='" + jmbgLekara + '\'' +
                ", dijagnoza='" + dijagnoza + '\'' +
                ", odlukaKomisije=" + odlukaKomisije +
                '}';
    }
}