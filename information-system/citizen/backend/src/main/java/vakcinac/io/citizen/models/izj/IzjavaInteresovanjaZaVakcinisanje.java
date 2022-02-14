package vakcinac.io.citizen.models.izj;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "izjava-interesovanja-za-vakcinisanje")
@XmlAccessorType(XmlAccessType.FIELD)
public class IzjavaInteresovanjaZaVakcinisanje {
	@XmlElement(name = "drzavljanstvo")
    private int drzavljanstvo;

    @XmlElement(name = "podnosilac")
    private String podnosilac;
    
    @XmlElement(name = "broj-mobilnog-telefona")
    private String brojMobilnogTelefona;

    @XmlElement(name = "broj-fiksnog-telefona")
    private String brojFiksnogTelefona;

    @XmlElement(name = "opstina")
    private String opstina;

    @XmlElement(name = "dobrovoljan-davalac-krvi")
    private boolean dobrovoljanDavalacKrvi;
    
    @XmlElementWrapper(name = "zeljene-vakcine")
    @XmlElement(name = "proizvodjac")
    private List<Integer> proizvodjac = new ArrayList<Integer>();

	public int getDrzavljanstvo() {
		return drzavljanstvo;
	}

	public void setDrzavljanstvo(int drzavljanstvo) {
		this.drzavljanstvo = drzavljanstvo;
	}

	public String getPodnosilac() {
		return podnosilac;
	}

	public void setPodnosilac(String podnosilac) {
		this.podnosilac = podnosilac;
	}

	public String getBrojMobilnogTelefona() {
		return brojMobilnogTelefona;
	}

	public void setBrojMobilnogTelefona(String brojMobilnogTelefona) {
		this.brojMobilnogTelefona = brojMobilnogTelefona;
	}

	public String getBrojFiksnogTelefona() {
		return brojFiksnogTelefona;
	}

	public void setBrojFiksnogTelefona(String brojFiksnogTelefona) {
		this.brojFiksnogTelefona = brojFiksnogTelefona;
	}

	public String getOpstina() {
		return opstina;
	}

	public void setOpstina(String opstina) {
		this.opstina = opstina;
	}

	public boolean isDobrovoljanDavalacKrvi() {
		return dobrovoljanDavalacKrvi;
	}

	public void setDobrovoljanDavalacKrvi(boolean dobrovoljanDavalacKrvi) {
		this.dobrovoljanDavalacKrvi = dobrovoljanDavalacKrvi;
	}

	public List<Integer> getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(List<Integer> proizvodjac) {
		this.proizvodjac = proizvodjac;
	}
}
