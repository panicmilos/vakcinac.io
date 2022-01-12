//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.12 at 10:51:43 PM CET 
//


package vakcinac.io.citizen.models.pot;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import vakcinac.io.citizen.models.os.TosobaSaJmbg;
import vakcinac.io.citizen.utils.adapters.LocalDateAdapter;


/**
 * <p>Java class for TpodaciOVakcinisanom complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TpodaciOVakcinisanom">
 *   &lt;complexContent>
 *     &lt;extension base="{https://www.vakcinac-io.rs/osnovna-sema}TosobaSaJmbg">
 *       &lt;sequence>
 *         &lt;element name="datum-rodjenja" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
 *         &lt;element name="pol" type="{https://www.vakcinac-io.rs/osnovna-sema}Tpol"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TpodaciOVakcinisanom", propOrder = {
    "datumRodjenja",
    "pol"
})
public class TpodaciOVakcinisanom
    extends TosobaSaJmbg
{

    @XmlElement(name = "datum-rodjenja", required = true, type = String.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    protected LocalDate datumRodjenja;
    protected int pol;

    /**
     * Gets the value of the datumRodjenja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    /**
     * Sets the value of the datumRodjenja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatumRodjenja(LocalDate value) {
        this.datumRodjenja = value;
    }

    /**
     * Gets the value of the pol property.
     * 
     */
    public int getPol() {
        return pol;
    }

    /**
     * Sets the value of the pol property.
     * 
     */
    public void setPol(int value) {
        this.pol = value;
    }

}
