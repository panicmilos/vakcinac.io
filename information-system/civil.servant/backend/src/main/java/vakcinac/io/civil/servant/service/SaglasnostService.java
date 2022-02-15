package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;
import vakcinac.io.civil.servant.models.sag.AzurirajSaglasnost;
import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.civil.servant.models.sag.Tlekar;
import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.civil.servant.repository.SaglasnostRepository;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.models.os.Tkontakt;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.time.LocalDate;

@Service
public class SaglasnostService extends BaseService<SaglasnostZaSprovodjenjePreporuceneImunizacije> {

    private GradjaninService gradjaninService;

    private ZaposleniService zaposleniService;

    public SaglasnostService(GradjaninService gradjaninService, SaglasnostRepository saglasnostRepository, ZaposleniService zaposleniService) {
        super(saglasnostRepository);

        this.gradjaninService = gradjaninService;
        this.zaposleniService = zaposleniService;
    }

    @Override
    public SaglasnostZaSprovodjenjePreporuceneImunizacije create(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) throws Exception {

        String gradjaninId = "";

        if (saglasnost.getPacijent().getDrzavljanstvo().getDomace() != null) {
            gradjaninId = saglasnost.getPacijent().getDrzavljanstvo().getDomace().getJmbg();
        } else {
            gradjaninId = saglasnost.getPacijent().getDrzavljanstvo().getStrano().getBrojPasosaEbs();
        }

        Tgradjanin gradjanin = gradjaninService.read(gradjaninId);

        fillOutPacijent(saglasnost, gradjanin);

        String id = String.format("%s_%d", gradjaninId, baseRepository.count(gradjaninId) + 1);

        return create(id, saglasnost);
    }

    public SaglasnostZaSprovodjenjePreporuceneImunizacije update(AzurirajSaglasnost noviPodaci) throws Exception {

        String fileName = String.format("%s_%d", noviPodaci.getSaglasnostId(), baseRepository.count(noviPodaci.getSaglasnostId()));
        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = read(fileName);

        fillOutEvidencija(saglasnost, noviPodaci);

        JaxBParser parser = JaxBParserFactory.newInstanceFor(SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.class, Boolean.FALSE);
        String serializedObj = parser.marshall(saglasnost.getEvidencijaOVakcinaciji());

        baseRepository.update(fileName, "//*:evidencija-o-vakcinaciji", serializedObj);

        return saglasnost;
    }

    private void fillOutEvidencija(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, AzurirajSaglasnost noviPodaci) {
        SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije privremeneKontraindikacije = new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije();

        privremeneKontraindikacije.setDatumUtvrdjivanja(LocalDate.now());
        privremeneKontraindikacije.setDijagnoza(noviPodaci.getDijagnoza());
        privremeneKontraindikacije.setOdlukaKomisije(noviPodaci.getOdlukaKomisije());

        saglasnost.getEvidencijaOVakcinaciji().setObrazac(new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac());
        saglasnost.getEvidencijaOVakcinaciji().getObrazac().setPrivremeneKontraindikacije(privremeneKontraindikacije);

        ZdravstveniRadnik lekar = (ZdravstveniRadnik) zaposleniService.findById(noviPodaci.getJmbgLekara());
        fillOutLekar(saglasnost, lekar);
    }

    private void fillOutLekar(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, ZdravstveniRadnik lekar) {
        saglasnost.getEvidencijaOVakcinaciji().getLekar().setBrojTelefona(lekar.getBrojTelefona());
        saglasnost.getEvidencijaOVakcinaciji().getLekar().setFaksimil(lekar.getFaksimil());
        saglasnost.getEvidencijaOVakcinaciji().getLekar().setPrezime(lekar.getPrezime());
        saglasnost.getEvidencijaOVakcinaciji().getLekar().setIme(lekar.getIme());
    }

    private void fillOutPacijent(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, Tgradjanin gradjanin) {
        saglasnost.getPacijent().getKontakt().setAdresaElektronskePoste(gradjanin.getEmail());
        saglasnost.getPacijent().getLicneInformacije().setImeRoditelja(gradjanin.getImeRoditelja());
        saglasnost.getPacijent().getLicneInformacije().setIme(gradjanin.getIme());
        saglasnost.getPacijent().getLicneInformacije().setPrezime(gradjanin.getPrezime());
        saglasnost.getPacijent().getLicneInformacije().setPol(gradjanin.getPol());
    }
}
