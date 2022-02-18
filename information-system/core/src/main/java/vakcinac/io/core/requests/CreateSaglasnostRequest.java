package vakcinac.io.core.requests;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "create-saglasnost-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateSaglasnostRequest {

    @XmlElement(name = "pacijent-jmbg")
    private String pacijentJmbg;

    @XmlElement(name = "naziv-drzavljanstva")
    protected String nazivDrzavljanstva;

    @XmlElement(name = "broj-pasosa")
    protected String brojPasosaEbs;

    @XmlElement(name = "broj-mobilnog")
    private String brojMobilnog;

    @XmlElement(name = "broj-fiksnog")
    private String brojFiknsog;

    @XmlElement(name = "radni-status")
    private int radniStatus;
    private int zanimanje;

    @XmlElement(name = "socijalna-zastita")
    private boolean korisnikSocijalneZastiste;

    @XmlElement(name = "opstina-sedista")
    private String nazivOpstinaSedista;
    private boolean izjava;

    @XmlElement(name = "imunoloski-lek")
    private String nazivImunoloskogLeka;

    @XmlElement(name = "mesto-stanovanje")
    private String mestoStanovanje;

    @XmlElement(name = "opstina-stanovanje")
    private String opstinaStanovanje;

    @XmlElement(name = "adresa-stanovanje")
    private String adresaStanovanje;


    public String getPacijentJmbg() {
        return pacijentJmbg;
    }

    public void setPacijentJmbg(String pacijentJmbg) {
        this.pacijentJmbg = pacijentJmbg;
    }

    public int getRadniStatus() {
        return radniStatus;
    }

    public void setRadniStatus(int radniStatus) {
        this.radniStatus = radniStatus;
    }

    public int getZanimanje() {
        return zanimanje;
    }

    public void setZanimanje(int zanimanje) {
        this.zanimanje = zanimanje;
    }

    public boolean isKorisnikSocijalneZastiste() {
        return korisnikSocijalneZastiste;
    }

    public void setKorisnikSocijalneZastiste(boolean korisnikSocijalneZastiste) {
        this.korisnikSocijalneZastiste = korisnikSocijalneZastiste;
    }

    public String getNazivOpstinaSedista() {
        return nazivOpstinaSedista;
    }

    public void setNazivOpstinaSedista(String nazivOpstinaSedista) {
        this.nazivOpstinaSedista = nazivOpstinaSedista;
    }

    public boolean isIzjava() {
        return izjava;
    }

    public void setIzjava(boolean izjava) {
        this.izjava = izjava;
    }

    public String getNazivImunoloskogLeka() {
        return nazivImunoloskogLeka;
    }

    public void setNazivImunoloskogLeka(String nazivImunoloskogLeka) {
        this.nazivImunoloskogLeka = nazivImunoloskogLeka;
    }

    public String getNazivDrzavljanstva() {
        return nazivDrzavljanstva;
    }

    public void setNazivDrzavljanstva(String nazivDrzavljanstva) {
        this.nazivDrzavljanstva = nazivDrzavljanstva;
    }

    public String getBrojPasosaEbs() {
        return brojPasosaEbs;
    }

    public void setBrojPasosaEbs(String brojPasosaEbs) {
        this.brojPasosaEbs = brojPasosaEbs;
    }

    public String getBrojMobilnog() {
        return brojMobilnog;
    }

    public void setBrojMobilnog(String brojMobilnog) {
        this.brojMobilnog = brojMobilnog;
    }

    public String getBrojFiknsog() {
        return brojFiknsog;
    }

    public void setBrojFiknsog(String brojFiknsog) {
        this.brojFiknsog = brojFiknsog;
    }

    public String getMestoStanovanje() {
        return mestoStanovanje;
    }

    public void setMestoStanovanje(String mestoStanovanje) {
        this.mestoStanovanje = mestoStanovanje;
    }

    public String getOpstinaStanovanje() {
        return opstinaStanovanje;
    }

    public void setOpstinaStanovanje(String opstinaStanovanje) {
        this.opstinaStanovanje = opstinaStanovanje;
    }

    public String getAdresaStanovanje() {
        return adresaStanovanje;
    }

    public void setAdresaStanovanje(String adresaStanovanje) {
        this.adresaStanovanje = adresaStanovanje;
    }
}
