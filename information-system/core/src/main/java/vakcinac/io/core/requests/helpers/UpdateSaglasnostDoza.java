package vakcinac.io.core.requests.helpers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateSaglasnostDoza {

    @XmlElement(name = "nacin-davanja-vakcine")
    protected String nacinDavanjaVakcine;

    @XmlElement
    protected Integer ekstremitet;

    @XmlElement(name = "serija-vakcine")
    protected String serijaVakcine;

    @XmlElement(name = "nezeljena-reakcija")
    protected String nezeljenaReakcija;
}
