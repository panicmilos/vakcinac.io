package vakcinac.io.core.requests;

import vakcinac.io.core.utils.adapters.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateSaglasnostRequest {

    @XmlElement(name = "datum-utvrdjivanja", required = true, type = String.class, nillable = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate datumUtvrdjivanja;

    @XmlElement(required = true, nillable = true)
    private String dijagnoza;

    @XmlElement(name = "odluka-komisije", required = true, type = Boolean.class, nillable = true)
    private Boolean odlukaKomisije;

    public LocalDate getDatumUtvrdjivanja() {
        return datumUtvrdjivanja;
    }

    public void setDatumUtvrdjivanja(LocalDate datumUtvrdjivanja) {
        this.datumUtvrdjivanja = datumUtvrdjivanja;
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
}
