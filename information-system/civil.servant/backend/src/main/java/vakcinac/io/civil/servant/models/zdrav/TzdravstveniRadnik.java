//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.13 at 02:52:47 PM CET 
//


package vakcinac.io.civil.servant.models.zdrav;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import vakcinac.io.core.models.os.Tkorisnik;


/**
 * <p>Java class for TzdravstveniRadnik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TzdravstveniRadnik">
 *   &lt;complexContent>
 *     &lt;extension base="{https://www.vakcinac-io.rs/osnovna-sema}Tkorisnik">
 *       &lt;sequence>
 *         &lt;element name="faksimil" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="broj-telefona" type="{https://www.vakcinac-io.rs/osnovna-sema}TbrMob"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TzdravstveniRadnik", propOrder = {
    "faksimil",
    "brojTelefona"
})
public class TzdravstveniRadnik
    extends Tkorisnik
{

    @XmlElement(required = true)
    protected String faksimil;
    @XmlElement(name = "broj-telefona", required = true)
    protected String brojTelefona;

    /**
     * Gets the value of the faksimil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaksimil() {
        return faksimil;
    }

    /**
     * Sets the value of the faksimil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaksimil(String value) {
        this.faksimil = value;
    }

    /**
     * Gets the value of the brojTelefona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojTelefona() {
        return brojTelefona;
    }

    /**
     * Sets the value of the brojTelefona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojTelefona(String value) {
        this.brojTelefona = value;
    }

}
