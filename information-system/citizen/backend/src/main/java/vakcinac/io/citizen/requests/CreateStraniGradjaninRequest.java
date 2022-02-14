package vakcinac.io.citizen.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlRootElement(name = "domaci-gradjanin")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateStraniGradjaninRequest {

    @XmlElement(name = "ime")
	private String ime;

    @XmlElement(name = "prezime")
	private String prezime;

    @XmlElement(name = "korisnicko-ime")
	private String korisnickoIme;

    @XmlElement(name = "lozinka")
	private String lozinka;

    @XmlElement(name = "datum-rodjenja")
	private XMLGregorianCalendar datumRodjenja;

    @XmlElement(name = "pol")
	private Integer pol;

    @XmlElement(name = "email")
	private String email;

    @XmlElement(name = "ime-roditelja")
	private String imeRoditelja;

    @XmlElement(name = "mesto-rodjenja")
	private String mestoRodjenja;

    @XmlElement(name = "adresa")
	private String adresa;

    @XmlElement(name = "mesto")
	private String mesto;

    @XmlElement(name = "opstina")
	private String opstina;

    @XmlElement(name = "identifikacioni-dokument")
	private Integer identifikacioniDokument;

    @XmlElement(name = "broj-pasosa")
	private String brojPasosa;
    
    @XmlElement(name = "ebs")
	private String ebs;

    @XmlElement(name = "drzava")
	private String drzava;

	public CreateStraniGradjaninRequest() {}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public XMLGregorianCalendar getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(XMLGregorianCalendar datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Integer getPol() {
		return pol;
	}

	public void setPol(Integer pol) {
		this.pol = pol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImeRoditelja() {
		return imeRoditelja;
	}

	public void setImeRoditelja(String imeRoditelja) {
		this.imeRoditelja = imeRoditelja;
	}

	public String getMestoRodjenja() {
		return mestoRodjenja;
	}

	public void setMestoRodjenja(String mestoRodjenja) {
		this.mestoRodjenja = mestoRodjenja;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getOpstina() {
		return opstina;
	}

	public void setOpstina(String opstina) {
		this.opstina = opstina;
	}

	public Integer getIdentifikacioniDokument() {
		return identifikacioniDokument;
	}

	public void setIdentifikacioniDokument(Integer identifikacioniDokument) {
		this.identifikacioniDokument = identifikacioniDokument;
	}

	public String getBrojPasosa() {
		return brojPasosa;
	}

	public void setBrojPasosa(String brojPasosa) {
		this.brojPasosa = brojPasosa;
	}

	public String getEbs() {
		return ebs;
	}

	public void setEbs(String ebs) {
		this.ebs = ebs;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
}
