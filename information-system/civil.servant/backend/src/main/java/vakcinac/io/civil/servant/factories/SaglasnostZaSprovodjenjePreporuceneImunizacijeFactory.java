package vakcinac.io.civil.servant.factories;

import org.springframework.stereotype.Component;

import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.civil.servant.models.sag.Tlekar;
import vakcinac.io.civil.servant.models.sag.TlicneInformacije;
import vakcinac.io.core.models.os.Tkontakt;
import vakcinac.io.core.requests.CreateSaglasnostRequest;

@Component
public class SaglasnostZaSprovodjenjePreporuceneImunizacijeFactory {

    public static SaglasnostZaSprovodjenjePreporuceneImunizacije create(CreateSaglasnostRequest request) {
        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = new SaglasnostZaSprovodjenjePreporuceneImunizacije();

        SaglasnostZaSprovodjenjePreporuceneImunizacije.IzjavaSaglasnosti izjavaSaglasnosti = new SaglasnostZaSprovodjenjePreporuceneImunizacije.IzjavaSaglasnosti();
        izjavaSaglasnosti.setIzjava(request.isIzjava());
        izjavaSaglasnosti.setNazivImunoloskogLeka(request.getNazivImunoloskogLeka());
        saglasnost.setIzjavaSaglasnosti(izjavaSaglasnosti);

        SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji evidencijaOVakcinaciji = new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji();
        evidencijaOVakcinaciji.setVakcinacijskiPunkt("/");
        evidencijaOVakcinaciji.setZdravstvenaUstanova("/");

        Tlekar tlekar = new Tlekar();
        tlekar.setFaksimil("/");
        tlekar.setBrojTelefona("/");
        tlekar.setIme("/");
        tlekar.setPrezime("/");

        evidencijaOVakcinaciji.setLekar(tlekar);
        SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac obrazac = new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac();
        SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije privremeneKontraindikacije = new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije();
        privremeneKontraindikacije.setDijagnoza(null);
        privremeneKontraindikacije.setDatumUtvrdjivanja(null);
        privremeneKontraindikacije.setOdlukaKomisije(null);

        obrazac.setPrivremeneKontraindikacije(privremeneKontraindikacije);

        evidencijaOVakcinaciji.setObrazac(new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac());
        saglasnost.setEvidencijaOVakcinaciji(evidencijaOVakcinaciji);

        SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent pacijent = new SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent();
        SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo drzavljanstvo = new SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo();

        if (!request.getPacijentJmbg().trim().isEmpty()) {
            SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo.Domace domace = new SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo.Domace();
            domace.setJmbg(request.getPacijentJmbg());
            drzavljanstvo.setDomace(domace);

        }
        else {
            SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo.Strano strano = new SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo.Strano();
            strano.setNazivDrzavljanstva(request.getNazivDrzavljanstva());
            strano.setBrojPasosaEbs(request.getBrojPasosaEbs());
            drzavljanstvo.setStrano(strano);
        }

        pacijent.setDrzavljanstvo(drzavljanstvo);


        TlicneInformacije tlicneInformacije = new TlicneInformacije();
        tlicneInformacije.setZanimanje(request.getZanimanje());
        tlicneInformacije.setRadniStatus(request.getRadniStatus());

        pacijent.setLicneInformacije(tlicneInformacije);

        saglasnost.setPacijent(pacijent);

        saglasnost.getPacijent().setKontakt(new Tkontakt());
        saglasnost.getPacijent().getKontakt().setBrojMobilnogTelefona(request.getBrojMobilnog());
        saglasnost.getPacijent().getKontakt().setBrojFiksnogTelefona(request.getBrojFiknsog());
        saglasnost.getPacijent().setUstanovaSocijalneZastite(new SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.UstanovaSocijalneZastite());
        saglasnost.getPacijent().getUstanovaSocijalneZastite().setKorisnik(request.isIzjava());
        saglasnost.getPacijent().getUstanovaSocijalneZastite().setNazivOpstinaSedista(request.getNazivOpstinaSedista());
        saglasnost.getPacijent().setStanovanje(new SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Stanovanje());
        saglasnost.getPacijent().getStanovanje().setOpstina(request.getOpstinaStanovanje());
        saglasnost.getPacijent().getStanovanje().setAdresa(request.getAdresaStanovanje());
        saglasnost.getPacijent().getStanovanje().setMesto(request.getMestoStanovanje());

        return saglasnost;
    }

}
