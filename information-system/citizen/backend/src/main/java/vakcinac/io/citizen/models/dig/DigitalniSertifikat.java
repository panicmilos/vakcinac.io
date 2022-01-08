//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.07 at 03:26:09 PM CET 
//


package vakcinac.io.citizen.models.dig;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import vakcinac.io.citizen.annotations.RegisterExistEntity;
import vakcinac.io.citizen.annotations.RegisterXmlScheme;
import vakcinac.io.citizen.annotations.RegisterXslFo;
import vakcinac.io.citizen.annotations.RegisterXslt;
import vakcinac.io.citizen.utils.adapters.LocalDateAdapter;
import vakcinac.io.citizen.utils.adapters.LocalDateTimeAdapter;


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
 *         &lt;element name="validacija" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="nosilac-sertifikata" type="{https://www.vakcinac-io.rs/digitalni-sertifikat}TnosilacSertifikata"/>
 *         &lt;element name="vakcinacije">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="vakcinacija" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="tip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="proizvodjac" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
 *                             &lt;element name="datum" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
 *                             &lt;element name="zdravstvena-ustanova" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="doza" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="broj-doza" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="testovi">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="test" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="vrsta-uzorka" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="proizvodjac" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="datum-uzorka" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
 *                             &lt;element name="datum-izdavanja" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
 *                             &lt;element name="rezultat" type="{https://www.vakcinac-io.rs/osnovna-sema}Trezultat"/>
 *                             &lt;element name="labaratorija" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="broj" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="broj-testova" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="broj-sertifikata" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="\d{7}/\d{2}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="datum-izdavanja" use="required" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateTimeString" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "validacija",
    "nosilacSertifikata",
    "vakcinacije",
    "testovi"
})
@XmlRootElement(name = "digitalni-sertifikat")
@RegisterXmlScheme(
	schemePath="/data/schemes/digitalni_sertifikat.xsd",
	targetNamespace="https://www.vakcinac-io.rs/digitalni-sertifikat",
	shortNamespace="dig"
)
@RegisterExistEntity(
	collectionUri="db/digitalni-sertifikati"
)
@RegisterXslt(
	xslPath="/data/xslt/digitalni_sertifikat.xsl"
)
@RegisterXslFo(
	xslPath="/data/xsl-fo/digitalni_sertifikat.xsl"
)
public class DigitalniSertifikat {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String validacija;
    @XmlElement(name = "nosilac-sertifikata", required = true)
    protected TnosilacSertifikata nosilacSertifikata;
    @XmlElement(required = true)
    protected DigitalniSertifikat.Vakcinacije vakcinacije;
    @XmlElement(required = true)
    protected DigitalniSertifikat.Testovi testovi;
    @XmlAttribute(name = "broj-sertifikata", required = true)
    protected String brojSertifikata;
    @XmlAttribute(name = "datum-izdavanja", required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    protected LocalDateTime datumIzdavanja;

    /**
     * Gets the value of the validacija property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidacija() {
        return validacija;
    }

    /**
     * Sets the value of the validacija property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidacija(String value) {
        this.validacija = value;
    }

    /**
     * Gets the value of the nosilacSertifikata property.
     * 
     * @return
     *     possible object is
     *     {@link TnosilacSertifikata }
     *     
     */
    public TnosilacSertifikata getNosilacSertifikata() {
        return nosilacSertifikata;
    }

    /**
     * Sets the value of the nosilacSertifikata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TnosilacSertifikata }
     *     
     */
    public void setNosilacSertifikata(TnosilacSertifikata value) {
        this.nosilacSertifikata = value;
    }

    /**
     * Gets the value of the vakcinacije property.
     * 
     * @return
     *     possible object is
     *     {@link DigitalniSertifikat.Vakcinacije }
     *     
     */
    public DigitalniSertifikat.Vakcinacije getVakcinacije() {
        return vakcinacije;
    }

    /**
     * Sets the value of the vakcinacije property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigitalniSertifikat.Vakcinacije }
     *     
     */
    public void setVakcinacije(DigitalniSertifikat.Vakcinacije value) {
        this.vakcinacije = value;
    }

    /**
     * Gets the value of the testovi property.
     * 
     * @return
     *     possible object is
     *     {@link DigitalniSertifikat.Testovi }
     *     
     */
    public DigitalniSertifikat.Testovi getTestovi() {
        return testovi;
    }

    /**
     * Sets the value of the testovi property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigitalniSertifikat.Testovi }
     *     
     */
    public void setTestovi(DigitalniSertifikat.Testovi value) {
        this.testovi = value;
    }

    /**
     * Gets the value of the brojSertifikata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojSertifikata() {
        return brojSertifikata;
    }

    /**
     * Sets the value of the brojSertifikata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojSertifikata(String value) {
        this.brojSertifikata = value;
    }

    /**
     * Gets the value of the datumIzdavanja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDateTime getDatumIzdavanja() {
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
    public void setDatumIzdavanja(LocalDateTime value) {
        this.datumIzdavanja = value;
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
     *         &lt;element name="test" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ime" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="vrsta-uzorka" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="proizvodjac" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="datum-uzorka" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
     *                   &lt;element name="datum-izdavanja" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
     *                   &lt;element name="rezultat" type="{https://www.vakcinac-io.rs/osnovna-sema}Trezultat"/>
     *                   &lt;element name="labaratorija" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="broj" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="broj-testova" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "test"
    })
    public static class Testovi {

        protected List<DigitalniSertifikat.Testovi.Test> test;
        @XmlAttribute(name = "broj-testova", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger brojTestova;

        /**
         * Gets the value of the test property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the test property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DigitalniSertifikat.Testovi.Test }
         * 
         * 
         */
        public List<DigitalniSertifikat.Testovi.Test> getTest() {
            if (test == null) {
                test = new ArrayList<DigitalniSertifikat.Testovi.Test>();
            }
            return this.test;
        }

        /**
         * Gets the value of the brojTestova property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getBrojTestova() {
            return brojTestova;
        }

        /**
         * Sets the value of the brojTestova property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setBrojTestova(BigInteger value) {
            this.brojTestova = value;
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
         *         &lt;element name="ime" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="vrsta-uzorka" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="proizvodjac" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="datum-uzorka" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
         *         &lt;element name="datum-izdavanja" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
         *         &lt;element name="rezultat" type="{https://www.vakcinac-io.rs/osnovna-sema}Trezultat"/>
         *         &lt;element name="labaratorija" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="broj" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "ime",
            "vrstaUzorka",
            "proizvodjac",
            "datumUzorka",
            "datumIzdavanja",
            "rezultat",
            "labaratorija"
        })
        public static class Test {

            @XmlElement(required = true, nillable = true)
            protected String ime;
            @XmlElement(name = "vrsta-uzorka", required = true, nillable = true)
            protected String vrstaUzorka;
            @XmlElement(required = true, nillable = true)
            protected String proizvodjac;
            @XmlElement(name = "datum-uzorka", required = true, type = String.class, nillable = true)
            @XmlJavaTypeAdapter(LocalDateAdapter.class)
            protected LocalDate datumUzorka;
            @XmlElement(name = "datum-izdavanja", required = true, type = String.class, nillable = true)
            @XmlJavaTypeAdapter(LocalDateAdapter.class)
            protected LocalDate datumIzdavanja;
            @XmlElement(required = true, nillable = true)
            protected String rezultat;
            @XmlElement(required = true, nillable = true)
            protected String labaratorija;
            @XmlAttribute(name = "broj", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger broj;

            /**
             * Gets the value of the ime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIme() {
                return ime;
            }

            /**
             * Sets the value of the ime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIme(String value) {
                this.ime = value;
            }

            /**
             * Gets the value of the vrstaUzorka property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVrstaUzorka() {
                return vrstaUzorka;
            }

            /**
             * Sets the value of the vrstaUzorka property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVrstaUzorka(String value) {
                this.vrstaUzorka = value;
            }

            /**
             * Gets the value of the proizvodjac property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProizvodjac() {
                return proizvodjac;
            }

            /**
             * Sets the value of the proizvodjac property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProizvodjac(String value) {
                this.proizvodjac = value;
            }

            /**
             * Gets the value of the datumUzorka property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public LocalDate getDatumUzorka() {
                return datumUzorka;
            }

            /**
             * Sets the value of the datumUzorka property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDatumUzorka(LocalDate value) {
                this.datumUzorka = value;
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
             * Gets the value of the rezultat property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRezultat() {
                return rezultat;
            }

            /**
             * Sets the value of the rezultat property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRezultat(String value) {
                this.rezultat = value;
            }

            /**
             * Gets the value of the labaratorija property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLabaratorija() {
                return labaratorija;
            }

            /**
             * Sets the value of the labaratorija property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLabaratorija(String value) {
                this.labaratorija = value;
            }

            /**
             * Gets the value of the broj property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getBroj() {
                return broj;
            }

            /**
             * Sets the value of the broj property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setBroj(BigInteger value) {
                this.broj = value;
            }

        }

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
     *         &lt;element name="vakcinacija" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="tip" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="proizvodjac" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
     *                   &lt;element name="datum" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
     *                   &lt;element name="zdravstvena-ustanova" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="doza" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="broj-doza" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "vakcinacija"
    })
    public static class Vakcinacije {

        protected List<DigitalniSertifikat.Vakcinacije.Vakcinacija> vakcinacija;
        @XmlAttribute(name = "broj-doza", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger brojDoza;

        /**
         * Gets the value of the vakcinacija property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vakcinacija property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVakcinacija().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DigitalniSertifikat.Vakcinacije.Vakcinacija }
         * 
         * 
         */
        public List<DigitalniSertifikat.Vakcinacije.Vakcinacija> getVakcinacija() {
            if (vakcinacija == null) {
                vakcinacija = new ArrayList<DigitalniSertifikat.Vakcinacije.Vakcinacija>();
            }
            return this.vakcinacija;
        }

        /**
         * Gets the value of the brojDoza property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getBrojDoza() {
            return brojDoza;
        }

        /**
         * Sets the value of the brojDoza property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setBrojDoza(BigInteger value) {
            this.brojDoza = value;
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
         *         &lt;element name="tip" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="proizvodjac" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="serija" type="{https://www.vakcinac-io.rs/osnovna-sema}Tserija"/>
         *         &lt;element name="datum" type="{https://www.vakcinac-io.rs/osnovna-sema}TdateString"/>
         *         &lt;element name="zdravstvena-ustanova" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="doza" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "tip",
            "proizvodjac",
            "serija",
            "datum",
            "zdravstvenaUstanova"
        })
        public static class Vakcinacija {

            @XmlElement(required = true)
            protected String tip;
            @XmlElement(required = true)
            protected String proizvodjac;
            @XmlElement(required = true)
            protected String serija;
            @XmlElement(required = true, type = String.class)
            @XmlJavaTypeAdapter(LocalDateAdapter.class)
            protected LocalDate datum;
            @XmlElement(name = "zdravstvena-ustanova", required = true)
            protected String zdravstvenaUstanova;
            @XmlAttribute(name = "doza", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger doza;

            /**
             * Gets the value of the tip property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTip() {
                return tip;
            }

            /**
             * Sets the value of the tip property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTip(String value) {
                this.tip = value;
            }

            /**
             * Gets the value of the proizvodjac property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProizvodjac() {
                return proizvodjac;
            }

            /**
             * Sets the value of the proizvodjac property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProizvodjac(String value) {
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
             * Gets the value of the doza property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getDoza() {
                return doza;
            }

            /**
             * Sets the value of the doza property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setDoza(BigInteger value) {
                this.doza = value;
            }

        }

    }

}
