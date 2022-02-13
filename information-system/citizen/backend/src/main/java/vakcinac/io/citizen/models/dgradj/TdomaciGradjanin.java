//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.13 at 02:53:48 PM CET 
//


package vakcinac.io.citizen.models.dgradj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import vakcinac.io.core.models.os.Tgradjanin;


/**
 * <p>Java class for TdomaciGradjanin complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TdomaciGradjanin">
 *   &lt;complexContent>
 *     &lt;extension base="{https://www.vakcinac-io.rs/osnovna-sema}Tgradjanin">
 *       &lt;sequence>
 *         &lt;element name="jmbg" type="{https://www.vakcinac-io.rs/osnovna-sema}Tjmbg"/>
 *         &lt;element name="broj-mobilnog-telefona" type="{https://www.vakcinac-io.rs/osnovna-sema}TbrMob"/>
 *         &lt;element name="broj-fiksnog-telefona" type="{https://www.vakcinac-io.rs/osnovna-sema}TbrFiks"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TdomaciGradjanin", propOrder = {
    "jmbg",
    "brojMobilnogTelefona",
    "brojFiksnogTelefona"
})
public class TdomaciGradjanin
    extends Tgradjanin
{

    @XmlElement(required = true)
    protected String jmbg;
    @XmlElement(name = "broj-mobilnog-telefona", required = true)
    protected String brojMobilnogTelefona;
    @XmlElement(name = "broj-fiksnog-telefona", required = true)
    protected String brojFiksnogTelefona;

    /**
     * Gets the value of the jmbg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmbg() {
        return jmbg;
    }

    /**
     * Sets the value of the jmbg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmbg(String value) {
        this.jmbg = value;
    }

    /**
     * Gets the value of the brojMobilnogTelefona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojMobilnogTelefona() {
        return brojMobilnogTelefona;
    }

    /**
     * Sets the value of the brojMobilnogTelefona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojMobilnogTelefona(String value) {
        this.brojMobilnogTelefona = value;
    }

    /**
     * Gets the value of the brojFiksnogTelefona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojFiksnogTelefona() {
        return brojFiksnogTelefona;
    }

    /**
     * Sets the value of the brojFiksnogTelefona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojFiksnogTelefona(String value) {
        this.brojFiksnogTelefona = value;
    }

}
