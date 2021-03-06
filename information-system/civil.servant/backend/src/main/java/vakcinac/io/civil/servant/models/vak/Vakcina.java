//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.13 at 03:22:59 PM CET 
//


package vakcinac.io.civil.servant.models.vak;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import vakcinac.io.core.annotations.RegisterExistEntity;
import vakcinac.io.core.annotations.RegisterXmlScheme;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proizvodjac" type="{https://www.vakcinac-io.rs/osnovna-sema}Tproizvodjac"/>
 *         &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "proizvodjac",
    "serija",
    "periodCekanja"
})
@XmlRootElement(name = "vakcina")
@RegisterXmlScheme(
	schemePath="/data/schemes/vakcina.xsd",
	targetNamespace="https://www.vakcinac-io.rs/vakcina",
	shortNamespace="vak"
)
@RegisterExistEntity(
	collectionUri="db/vakcine"
)
public class Vakcina {

    protected int proizvodjac;
    @XmlElement(required = true)
    protected String serija;
    @XmlElement(name = "period-cekanja", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger periodCekanja;


    /**
     * Gets the value of the proizvodjac property.
     * 
     */
    public int getProizvodjac() {
        return proizvodjac;
    }

    /**
     * Sets the value of the proizvodjac property.
     * 
     */
    public void setProizvodjac(int value) {
        this.proizvodjac = value;
    }

    /**
     * Gets the value of the serija property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerija() {
        return serija;
    }

    /**
     * Sets the value of the serija property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerija(String value) {
        this.serija = value;
    }

	public BigInteger getPeriodCekanja() {
		return periodCekanja;
	}

	public void setPeriodCekanja(BigInteger periodCekanja) {
		this.periodCekanja = periodCekanja;
	}
    
    
    
    

}
