//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.12 at 10:51:43 PM CET 
//


package vakcinac.io.citizen.models.pot;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import vakcinac.io.core.annotations.RegisterExistEntity;
import vakcinac.io.core.annotations.RegisterXmlScheme;
import vakcinac.io.core.annotations.RegisterXslFo;
import vakcinac.io.core.annotations.RegisterXslt;
import vakcinac.io.core.models.os.Tlink;
import vakcinac.io.core.models.os.Tmeta;
import vakcinac.io.core.utils.adapters.LocalDateAdapter;


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
 *         &lt;element name="link" type="{https://www.vakcinac-io.rs/osnovna-sema}Tlink" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="meta" type="{https://www.vakcinac-io.rs/osnovna-sema}Tmeta" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="podaci-o-vakcinisanom" type="{https://www.vakcinac-io.rs/potvrda}TpodaciOVakcinisanom"/>
 *         &lt;element name="podaci-o-vakcinaciji">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="zdravstvena-ustanova" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="naziv-vakcine" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="podaci-o-dozama">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="primljena-doza" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="datum" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
 *                                       &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="redni-broj" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="qr-kod" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{https://www.vakcinac-io.rs/osnovna-sema}rdfMainDocument"/>
 *       &lt;attribute name="datum-izdavanja" use="required" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString" />
 *       &lt;attribute name="broj-primljenih-doza" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="sifra-potvrde" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="\d{6}-\d{6}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "link",
    "meta",
    "podaciOVakcinisanom",
    "podaciOVakcinaciji",
    "qrKod"
})
@XmlRootElement(name = "potvrda-o-izvrsenoj-vakcinaciji")
@RegisterXmlScheme(
	schemePath="/data/schemes/potvrda_o_izvrsenoj_vakcinaciji.xsd",
	targetNamespace="https://www.vakcinac-io.rs/potvrda",
	shortNamespace="pot"
)
@RegisterExistEntity(
	collectionUri="db/potvrde"
)
@RegisterXslt(
	xslPath="/data/xslt/potvrda_o_izvrsenoj_vakcinaciji.xsl"
)
@RegisterXslFo(
	xslPath="/data/xsl-fo/potvrda_o_izvrsenoj_vakcinaciji.xsl"
)
public class PotvrdaOIzvrsenojVakcinaciji {

    protected List<Tlink> link;
    protected List<Tmeta> meta;
    @XmlElement(name = "podaci-o-vakcinisanom", required = true)
    protected TpodaciOVakcinisanom podaciOVakcinisanom;
    @XmlElement(name = "podaci-o-vakcinaciji", required = true)
    protected PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji podaciOVakcinaciji;
    @XmlElement(name = "qr-kod", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String qrKod;
    @XmlAttribute(name = "datum-izdavanja", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    protected LocalDate datumIzdavanja;
    @XmlAttribute(name = "broj-primljenih-doza", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger brojPrimljenihDoza;
    @XmlAttribute(name = "sifra-potvrde", required = true)
    protected String sifraPotvrde;
    @XmlAttribute(name = "about")
    @XmlSchemaType(name = "anySimpleType")
    protected String about;
    @XmlAttribute(name = "typeof")
    @XmlSchemaType(name = "anySimpleType")
    protected String typeof;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    /**
     * Gets the value of the link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tlink }
     * 
     * 
     */
    public List<Tlink> getLink() {
        if (link == null) {
            link = new ArrayList<Tlink>();
        }
        return this.link;
    }

    /**
     * Gets the value of the meta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tmeta }
     * 
     * 
     */
    public List<Tmeta> getMeta() {
        if (meta == null) {
            meta = new ArrayList<Tmeta>();
        }
        return this.meta;
    }

    /**
     * Gets the value of the podaciOVakcinisanom property.
     * 
     * @return
     *     possible object is
     *     {@link TpodaciOVakcinisanom }
     *     
     */
    public TpodaciOVakcinisanom getPodaciOVakcinisanom() {
        return podaciOVakcinisanom;
    }

    /**
     * Sets the value of the podaciOVakcinisanom property.
     * 
     * @param value
     *     allowed object is
     *     {@link TpodaciOVakcinisanom }
     *     
     */
    public void setPodaciOVakcinisanom(TpodaciOVakcinisanom value) {
        this.podaciOVakcinisanom = value;
    }

    /**
     * Gets the value of the podaciOVakcinaciji property.
     * 
     * @return
     *     possible object is
     *     {@link PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji }
     *     
     */
    public PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji getPodaciOVakcinaciji() {
        return podaciOVakcinaciji;
    }

    /**
     * Sets the value of the podaciOVakcinaciji property.
     * 
     * @param value
     *     allowed object is
     *     {@link PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji }
     *     
     */
    public void setPodaciOVakcinaciji(PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji value) {
        this.podaciOVakcinaciji = value;
    }

    /**
     * Gets the value of the qrKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQrKod() {
        return qrKod;
    }

    /**
     * Sets the value of the qrKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQrKod(String value) {
        this.qrKod = value;
    }

    /**
     * Gets the value of the datumIzdavanja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    /**
     * Sets the value of the datumIzdavanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatumIzdavanja(LocalDate value) {
        this.datumIzdavanja = value;
    }

    /**
     * Gets the value of the brojPrimljenihDoza property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrojPrimljenihDoza() {
        return brojPrimljenihDoza;
    }

    /**
     * Sets the value of the brojPrimljenihDoza property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrojPrimljenihDoza(BigInteger value) {
        this.brojPrimljenihDoza = value;
    }

    /**
     * Gets the value of the sifraPotvrde property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSifraPotvrde() {
        return sifraPotvrde;
    }

    /**
     * Sets the value of the sifraPotvrde property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSifraPotvrde(String value) {
        this.sifraPotvrde = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="zdravstvena-ustanova" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="naziv-vakcine" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="podaci-o-dozama">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="primljena-doza" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="datum" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
     *                             &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="redni-broj" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "zdravstvenaUstanova",
        "nazivVakcine",
        "podaciODozama"
    })
    public static class PodaciOVakcinaciji {

        @XmlElement(name = "zdravstvena-ustanova", required = true)
        protected String zdravstvenaUstanova;
        @XmlElement(name = "naziv-vakcine", required = true)
        protected String nazivVakcine;
        @XmlElement(name = "podaci-o-dozama", required = true)
        protected PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama podaciODozama;

        /**
         * Gets the value of the zdravstvenaUstanova property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZdravstvenaUstanova() {
            return zdravstvenaUstanova;
        }

        /**
         * Sets the value of the zdravstvenaUstanova property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZdravstvenaUstanova(String value) {
            this.zdravstvenaUstanova = value;
        }

        /**
         * Gets the value of the nazivVakcine property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNazivVakcine() {
            return nazivVakcine;
        }

        /**
         * Sets the value of the nazivVakcine property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNazivVakcine(String value) {
            this.nazivVakcine = value;
        }

        /**
         * Gets the value of the podaciODozama property.
         * 
         * @return
         *     possible object is
         *     {@link PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama }
         *     
         */
        public PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama getPodaciODozama() {
            return podaciODozama;
        }

        /**
         * Sets the value of the podaciODozama property.
         * 
         * @param value
         *     allowed object is
         *     {@link PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama }
         *     
         */
        public void setPodaciODozama(PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama value) {
            this.podaciODozama = value;
        }


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
         *         &lt;element name="primljena-doza" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="datum" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
         *                   &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
         *                 &lt;/sequence>
         *                 &lt;attribute name="redni-broj" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
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
            "primljenaDoza"
        })
        public static class PodaciODozama {

            @XmlElement(name = "primljena-doza", required = true)
            protected List<PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza> primljenaDoza;

            /**
             * Gets the value of the primljenaDoza property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the primljenaDoza property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPrimljenaDoza().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza }
             * 
             * 
             */
            public List<PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza> getPrimljenaDoza() {
                if (primljenaDoza == null) {
                    primljenaDoza = new ArrayList<PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza>();
                }
                return this.primljenaDoza;
            }


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
             *         &lt;element name="datum" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
             *         &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
             *       &lt;/sequence>
             *       &lt;attribute name="redni-broj" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlRootElement(name = "primljena-doza")
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "datum",
                "serija"
            })
            public static class PrimljenaDoza {

                @XmlElement(required = true, type = String.class, nillable = true)
                @XmlJavaTypeAdapter(LocalDateAdapter.class)
                protected LocalDate datum;
                @XmlElement(required = true, nillable = true)
                protected String serija;
                @XmlAttribute(name = "redni-broj", required = true)
                @XmlSchemaType(name = "positiveInteger")
                protected BigInteger redniBroj;

                /**
                 * Gets the value of the datum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public LocalDate getDatum() {
                    return datum;
                }

                /**
                 * Sets the value of the datum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDatum(LocalDate value) {
                    this.datum = value;
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

                /**
                 * Gets the value of the redniBroj property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getRedniBroj() {
                    return redniBroj;
                }

                /**
                 * Sets the value of the redniBroj property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setRedniBroj(BigInteger value) {
                    this.redniBroj = value;
                }

            }

        }

    }

}
