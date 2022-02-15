package vakcinac.io.civil.servant.models.dig;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import vakcinac.io.core.utils.adapters.LocalDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Test {
	@XmlElement(name = "ime")
    protected String ime;
	
    @XmlElement(name = "vrsta-uzorka")
    protected String vrstaUzorka;
    
    @XmlElement(name = "proizvodjac")
    protected String proizvodjac;
    
    @XmlElement(name = "datum-uzorka")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    protected LocalDate datumUzorka;
    
    @XmlElement(name = "datum-izdavanja")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    protected LocalDate datumIzdavanja;
    
    @XmlElement(name = "rezultat")
    protected String rezultat;
    
    @XmlElement(name = "labaratorija")
    protected String labaratorija;
    
    @XmlAttribute(name = "broj")
    protected BigInteger broj;
    
    public Test() {}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getVrstaUzorka() {
		return vrstaUzorka;
	}

	public void setVrstaUzorka(String vrstaUzorka) {
		this.vrstaUzorka = vrstaUzorka;
	}

	public String getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public LocalDate getDatumUzorka() {
		return datumUzorka;
	}

	public void setDatumUzorka(LocalDate datumUzorka) {
		this.datumUzorka = datumUzorka;
	}

	public LocalDate getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(LocalDate datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}

	public String getRezultat() {
		return rezultat;
	}

	public void setRezultat(String rezultat) {
		this.rezultat = rezultat;
	}

	public String getLabaratorija() {
		return labaratorija;
	}

	public void setLabaratorija(String labaratorija) {
		this.labaratorija = labaratorija;
	}

	public BigInteger getBroj() {
		return broj;
	}

	public void setBroj(BigInteger broj) {
		this.broj = broj;
	}
}
