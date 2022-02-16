package vakcinac.io.core.models.os;

import vakcinac.io.core.utils.adapters.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrimljenaDozaIzPotvrde {

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    protected LocalDate datum;

    @XmlElement
    protected String serija;

    @XmlAttribute(name = "redni-broj")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger redniBroj;

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }

    public BigInteger getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(BigInteger redniBroj) {
        this.redniBroj = redniBroj;
    }
}
