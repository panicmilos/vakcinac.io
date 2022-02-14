//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.14 at 08:50:43 PM CET 
//


package vakcinac.io.citizen.models.sgradj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import vakcinac.io.core.annotations.RegisterExistEntity;
import vakcinac.io.core.annotations.RegisterXmlScheme;
import vakcinac.io.core.models.os.Tgradjanin;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{https://www.vakcinac-io.rs/osnovna-sema}Tgradjanin">
 *       &lt;sequence>
 *         &lt;element name="identifikacioni-dokument" type="{https://www.vakcinac-io.rs/strani-gradjanin}TidentifikacioniDokument"/>
 *         &lt;element name="ebs" type="{https://www.vakcinac-io.rs/osnovna-sema}Tebs"/>
 *         &lt;element name="drzava" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="boravak-u-rs" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "identifikacioniDokument",
    "ebs",
    "drzava",
    "boravakURs"
})
@XmlRootElement(name = "strani-gradjanin")
@RegisterXmlScheme(
	schemePath="/data/schemes/strani-gradjanin.xsd",
	targetNamespace="https://www.vakcinac-io.rs/strani-gradjanin",
	shortNamespace="sgradj"
)
@RegisterExistEntity(
	collectionUri="db/strani-gradjani"
)
public class StraniGradjanin
    extends Tgradjanin
{

    @XmlElement(name = "identifikacioni-dokument")
    protected int identifikacioniDokument;
    @XmlElement(required = true, nillable = true)
    protected String ebs;
    @XmlElement(required = true)
    protected String drzava;
    @XmlElement(name = "boravak-u-rs")
    protected boolean boravakURs;

    /**
     * Gets the value of the identifikacioniDokument property.
     * 
     */
    public int getIdentifikacioniDokument() {
        return identifikacioniDokument;
    }

    /**
     * Sets the value of the identifikacioniDokument property.
     * 
     */
    public void setIdentifikacioniDokument(int value) {
        this.identifikacioniDokument = value;
    }

    /**
     * Gets the value of the ebs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEbs() {
        return ebs;
    }

    /**
     * Sets the value of the ebs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEbs(String value) {
        this.ebs = value;
    }

    /**
     * Gets the value of the drzava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * Sets the value of the drzava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrzava(String value) {
        this.drzava = value;
    }

    /**
     * Gets the value of the boravakURs property.
     * 
     */
    public boolean isBoravakURs() {
        return boravakURs;
    }

    /**
     * Sets the value of the boravakURs property.
     * 
     */
    public void setBoravakURs(boolean value) {
        this.boravakURs = value;
    }

}
