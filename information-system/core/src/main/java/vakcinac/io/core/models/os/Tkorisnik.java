//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.16 at 02:01:11 PM CET 
//


package vakcinac.io.core.models.os;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * <p>Java class for Tkorisnik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tkorisnik">
 *   &lt;complexContent>
 *     &lt;extension base="{https://www.vakcinac-io.rs/osnovna-sema}Tosoba">
 *       &lt;sequence>
 *         &lt;element name="korisnicko-ime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lozinka" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datum-rodjenja" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="pol" type="{https://www.vakcinac-io.rs/osnovna-sema}Tpol"/>
 *         &lt;element name="email" type="{https://www.vakcinac-io.rs/osnovna-sema}Temail"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{https://www.vakcinac-io.rs/osnovna-sema}rdfMainDocument"/>
 *       &lt;anyAttribute/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tkorisnik", propOrder = {
    "korisnickoIme",
    "lozinka",
    "datumRodjenja",
    "pol",
    "email"
})
@XmlSeeAlso({
    Tgradjanin.class,
    Tzaposleni.class
})
public class Tkorisnik
    extends Tosoba
{

    @XmlElement(name = "korisnicko-ime", required = true)
    protected String korisnickoIme;
    @XmlElement(required = true)
    protected String lozinka;
    @XmlElement(name = "datum-rodjenja", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumRodjenja;
    protected int pol;
    @XmlElement(required = true)
    protected String email;
    @XmlAttribute(name = "about")
    @XmlSchemaType(name = "anySimpleType")
    protected String about;
    @XmlAttribute(name = "typeof")
    @XmlSchemaType(name = "anySimpleType")
    protected String typeof;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the korisnickoIme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Sets the value of the korisnickoIme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorisnickoIme(String value) {
        this.korisnickoIme = value;
    }

    /**
     * Gets the value of the lozinka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Sets the value of the lozinka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLozinka(String value) {
        this.lozinka = value;
    }

    /**
     * Gets the value of the datumRodjenja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumRodjenja() {
        return datumRodjenja;
    }

    /**
     * Sets the value of the datumRodjenja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumRodjenja(XMLGregorianCalendar value) {
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

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the about property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbout() {
        return about;
    }

    /**
     * Sets the value of the about property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbout(String value) {
        this.about = value;
    }

    /**
     * Gets the value of the typeof property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeof() {
        return typeof;
    }

    /**
     * Sets the value of the typeof property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeof(String value) {
        this.typeof = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
