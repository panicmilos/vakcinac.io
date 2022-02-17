package vakcinac.io.core.requests;

import vakcinac.io.core.requests.helpers.UpdateSaglasnostDoza;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "update-saglasnost-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateSaglasnostRequest {

    @XmlElement(name = "saglasnost-id")
    private String saglasnostId;

    @XmlElement(name = "jmbg-lekara")
    private String jmbgLekara;

    @XmlElement
    private String dijagnoza;

    @XmlElement(name = "odluka-komisije")
    private Boolean odlukaKomisije;

    @XmlElementWrapper(name = "doze")
    @XmlElement(name = "doza")
    private List<UpdateSaglasnostDoza> testovi;

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
        return "UpdateSaglasnostRequest{" +
                "saglasnostId='" + saglasnostId + '\'' +
                ", jmbgLekara='" + jmbgLekara + '\'' +
                ", dijagnoza='" + dijagnoza + '\'' +
                ", odlukaKomisije=" + odlukaKomisije +
                '}';
    }
}
