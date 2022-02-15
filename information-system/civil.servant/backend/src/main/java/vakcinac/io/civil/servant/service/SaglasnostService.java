package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.sag.AzurirajSaglasnost;
import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.civil.servant.models.sag.Tlekar;
import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.civil.servant.repository.SaglasnostRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.models.os.Tlink;
import vakcinac.io.core.models.os.Tmeta;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class SaglasnostService extends BaseService<SaglasnostZaSprovodjenjePreporuceneImunizacije> {

    private GradjaninService gradjaninService;

    private ZaposleniService zaposleniService;

    public SaglasnostService(GradjaninService gradjaninService, CivilServantJenaRepository jenaRepository, SaglasnostRepository saglasnostRepository, ZaposleniService zaposleniService) {
        super(saglasnostRepository, jenaRepository);

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
        fillOutRdf(gradjaninId, saglasnost);

        JaxBParser parser = JaxBParserFactory.newInstanceFor(SaglasnostZaSprovodjenjePreporuceneImunizacije.class);
        String serializedObj = parser.marshall(saglasnost);

        String id = String.format("%s_%d", gradjaninId, baseRepository.count(gradjaninId) + 1);

        jenaRepository.insert(serializedObj, "/saglasnosti");

        return create(id, serializedObj);
    }

    private void fillOutRdf(String gradjaninId, SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) throws XMLDBException, IOException {

        String id =  String.format("%s/%d", gradjaninId, baseRepository.count(gradjaninId) + 1);

        saglasnost.setAbout(String.format("%s/saglasnost/%s", Constants.ROOT_URL, id));
        saglasnost.setTypeof("rdfos:SaglasnostImunizacijeDokument");

        saglasnost.setDatumIzdavanja(LocalDate.now());

        saglasnost.getLink().add(TlinkFactory.create("rdfsi:naOsnovuInteresovanja", String.format("%s/interesovanje/%s/%d", Constants.ROOT_URL, gradjaninId, 1), "rdfos:IzjavaInteresovanjaZaVakcinisanjeDokument"));
        saglasnost.getLink().add(TlinkFactory.create("rdfsi:za", String.format("%s/gradjani/%s", Constants.ROOT_URL, gradjaninId, 1), "rdfos:Gradjanin"));
        saglasnost.getLink().add(TlinkFactory.create("rdfsi:vakcinisanOd", "", "rdfos:ZdravstveniRadnik"));

        saglasnost.getMeta().add(TmetaFactory.create("rdfos:izdat", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));
        saglasnost.getMeta().add(TmetaFactory.create("rdfos:izmenjen", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));

        saglasnost.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
        saglasnost.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
        saglasnost.getOtherAttributes().put(QName.valueOf("xmlns:rdfsi"), "https://www.vakcinac-io.rs/rdfs/saglasnost/");
    }

    public SaglasnostZaSprovodjenjePreporuceneImunizacije update(AzurirajSaglasnost noviPodaci) throws Exception {

        String fileName = String.format("%s_%d", noviPodaci.getSaglasnostId(), baseRepository.count(noviPodaci.getSaglasnostId()));
        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = read(fileName);

        fillOutEvidencija(saglasnost, noviPodaci);

        JaxBParser parser = JaxBParserFactory.newInstanceFor(SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.class, Boolean.FALSE);
        String serializedObj = parser.marshall(saglasnost.getEvidencijaOVakcinaciji());

        baseRepository.update(fileName, "/*:evidencija-o-vakcinaciji", serializedObj);

        return saglasnost;
    }

    private void updateMetadata(String fileName, SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, String lekarJmbg) {

        Optional<Tmeta> novaMeta = saglasnost.getMeta().stream()
                .filter(meta -> meta.getProperty().equals("rdfos:izmenjen"))
                .findFirst();
        novaMeta.ifPresent(izdatMeta -> izdatMeta.setValue(LocalDateUtils.toXMLDateString(LocalDate.now())));

        Optional<Tlink> noviLink = saglasnost.getLink().stream()
                .filter(link -> link.getRel().equals("rdfsi:vakcinisanOd"))
                .findFirst();
        noviLink.ifPresent(vakcinisanOd -> vakcinisanOd.setHref(String.format("%s/zdravstveniRadnici/%s", Constants.ROOT_URL, lekarJmbg)));

        try {

            JaxBParser metaParser = JaxBParserFactory.newInstanceFor(Tmeta.class, Boolean.FALSE);
            String serializedObj = metaParser.marshall(novaMeta.get());
            baseRepository.update(fileName, "//*:meta[@property='rdfsi:izmenjen']", serializedObj);

            JaxBParser linkParser = JaxBParserFactory.newInstanceFor(Tlink.class, Boolean.FALSE);
            String serializedLink = linkParser.marshall(noviLink.get());
            baseRepository.update(fileName, "//*:link[@rel='rdfsi:vakcinisanOd']", serializedLink);

        } catch (XMLDBException e) {
        }

    }

    private void fillOutEvidencija(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, AzurirajSaglasnost noviPodaci) throws XMLDBException, IOException {
        SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije privremeneKontraindikacije = new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije();

        privremeneKontraindikacije.setDatumUtvrdjivanja(LocalDate.now());
        privremeneKontraindikacije.setDijagnoza(noviPodaci.getDijagnoza());
        privremeneKontraindikacije.setOdlukaKomisije(noviPodaci.getOdlukaKomisije());

        saglasnost.getEvidencijaOVakcinaciji().setObrazac(new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac());
        saglasnost.getEvidencijaOVakcinaciji().getObrazac().setPrivremeneKontraindikacije(privremeneKontraindikacije);

        ZdravstveniRadnik lekar = (ZdravstveniRadnik) zaposleniService.findById(noviPodaci.getJmbgLekara());
        fillOutLekar(saglasnost, lekar);

        String fileName = String.format("%s_%d", noviPodaci.getSaglasnostId(), baseRepository.count(noviPodaci.getSaglasnostId()));

        updateMetadata(fileName, saglasnost, lekar.getJmbg());
    }

    private void fillOutLekar(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, ZdravstveniRadnik lekar) {
        saglasnost.getEvidencijaOVakcinaciji().setLekar(new Tlekar());
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
