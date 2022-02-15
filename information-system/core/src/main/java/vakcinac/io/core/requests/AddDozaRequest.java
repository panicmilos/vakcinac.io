package vakcinac.io.core.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "add-doza-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddDozaRequest {

    @XmlElement(name = "potvrda-id")
    private String potvrdaId;

    @XmlElement
    private String serija;

    public String getPotvrdaId() {
        return potvrdaId;
    }

    public void setPotvrdaId(String potvrdaId) {
        this.potvrdaId = potvrdaId;
    }

    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }
}
