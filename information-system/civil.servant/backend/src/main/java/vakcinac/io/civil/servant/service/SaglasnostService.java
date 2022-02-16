package vakcinac.io.civil.servant.service;

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
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.models.os.InformacijeOPrimljenimDozamaIzPotvrde;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.models.os.Tlink;
import vakcinac.io.core.models.os.Tmeta;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequestScope
public class SaglasnostService extends BaseService<SaglasnostZaSprovodjenjePreporuceneImunizacije> {

    private GradjaninService gradjaninService;

    private ZaposleniService zaposleniService;

    private PotvrdaService potvrdaService;

    public SaglasnostService(GradjaninService gradjaninService, CivilServantJenaRepository jenaRepository, SaglasnostRepository saglasnostRepository, ZaposleniService zaposleniService) {
        super(saglasnostRepository, jenaRepository);

        this.gradjaninService = gradjaninService;
        this.zaposleniService = zaposleniService;
    }

    @Override
    public SaglasnostZaSprovodjenjePreporuceneImunizacije create(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) throws Exception {

        String gradjaninId = getGradjaninId(saglasnost);

        Tgradjanin gradjanin = gradjaninService.read(gradjaninId);

        if(gradjanin == null) {
            throw new MissingEntityException("Gradjan sa datim identifikatorm ne postoji.");
        }

        fillOutPacijent(saglasnost, gradjanin);
//        fillOutVakcine(saglasnost, )
        fillOutRdf(gradjaninId, saglasnost);

        JaxBParser parser = JaxBParserFactory.newInstanceFor(SaglasnostZaSprovodjenjePreporuceneImunizacije.class);
        String serializedObj = parser.marshall(saglasnost);

        String id = String.format("%s_%d", gradjaninId, baseRepository.count(gradjaninId) + 1);

        jenaRepository.insert(serializedObj, "/saglasnosti");

        return create(id, serializedObj);
    }

    private void fillOutVakcine(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, String gradjaninId) {
        InformacijeOPrimljenimDozamaIzPotvrde info = potvrdaService.getVakcine(gradjaninId);
        System.out.println(info);
    }

    private String getGradjaninId(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) {
        String gradjaninId = "";

        SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo.Domace domace = saglasnost.getPacijent().getDrzavljanstvo().getDomace();
        SaglasnostZaSprovodjenjePreporuceneImunizacije.Pacijent.Drzavljanstvo.Strano strano = saglasnost.getPacijent().getDrzavljanstvo().getStrano();

        if (domace != null && !domace.getJmbg().trim().isEmpty()) {
            gradjaninId = domace.getJmbg();
        } else if(strano != null && !strano.getBrojPasosaEbs().trim().isEmpty()){
            gradjaninId = strano.getBrojPasosaEbs();
        } else {
            throw new BadLogicException("Gradjanin mora imati jmbg, ebs ili broj pasosa.");
        }

        return gradjaninId;
    }

    private void fillOutRdf(String gradjaninId, SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) throws XMLDBException, IOException {

        String id =  String.format("%s/%d", gradjaninId, baseRepository.count(gradjaninId) + 1);

        saglasnost.setAbout(String.format("%s/saglasnost/%s", Constants.ROOT_URL, id));
        saglasnost.setTypeof("rdfos:SaglasnostImunizacijeDokument");

        saglasnost.setDatumIzdavanja(LocalDate.now());

        String za = String.format("%s/gradjani/%s", Constants.ROOT_URL, gradjaninId);

        String interesovanje = getRelatedInteresovanje(za);
        if(interesovanje == null || interesovanje.trim().isEmpty()) {
            throw new MissingEntityException(String.format("No interesovanje for gradjanin %s", za));
        }

        saglasnost.getLink().add(TlinkFactory.create("rdfsi:naOsnovuInteresovanja", interesovanje, "rdfos:IzjavaInteresovanjaZaVakcinisanjeDokument"));
        saglasnost.getLink().add(TlinkFactory.create("rdfsi:za", za, "rdfos:Gradjanin"));
        saglasnost.getLink().add(TlinkFactory.create("rdfsi:vakcinisanOd", "https://www.vakcinac-io.rs/none", "rdfos:ZdravstveniRadnik"));

        saglasnost.getMeta().add(TmetaFactory.create("rdfos:izdat", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));
        saglasnost.getMeta().add(TmetaFactory.create("rdfos:izmenjen", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));

        addGeneralAttributes(saglasnost);
    }

    private void addGeneralAttributes(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) {
        saglasnost.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
        saglasnost.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
        saglasnost.getOtherAttributes().put(QName.valueOf("xmlns:rdfsi"), "https://www.vakcinac-io.rs/rdfs/saglasnost/");
    }

    private String getRelatedInteresovanje(String za) {
        return jenaRepository.readLatestSubject("/izjava", "<https://www.vakcinac-io.rs/rdfs/interesovanje/za>", String.format("<%s>", za));
    }

    public SaglasnostZaSprovodjenjePreporuceneImunizacije update(AzurirajSaglasnost noviPodaci) throws Exception {

        String id = String.format("%s_%d", noviPodaci.getSaglasnostId(), baseRepository.count(noviPodaci.getSaglasnostId()));
        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = read(id);

        fillOutEvidencija(saglasnost, noviPodaci);
        addGeneralAttributes(saglasnost);

        JaxBParser saglasnostParser = JaxBParserFactory.newInstanceFor(SaglasnostZaSprovodjenjePreporuceneImunizacije.class, Boolean.FALSE);
        String serializedObj = saglasnostParser.marshall(saglasnost);

        baseRepository.store(id, serializedObj);
        jenaRepository.updateData(saglasnost.getAbout(), serializedObj, "/saglasnosti");

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

    }

    private void fillOutEvidencija(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, AzurirajSaglasnost noviPodaci) throws XMLDBException, IOException {

        SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije privremeneKontraindikacije = new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac.PrivremeneKontraindikacije();
        privremeneKontraindikacije.setDatumUtvrdjivanja(LocalDate.now());
        privremeneKontraindikacije.setDijagnoza(noviPodaci.getDijagnoza());
        privremeneKontraindikacije.setOdlukaKomisije(noviPodaci.getOdlukaKomisije());

        saglasnost.getEvidencijaOVakcinaciji().setObrazac(new SaglasnostZaSprovodjenjePreporuceneImunizacije.EvidencijaOVakcinaciji.Obrazac());
        saglasnost.getEvidencijaOVakcinaciji().getObrazac().setPrivremeneKontraindikacije(privremeneKontraindikacije);
        saglasnost.getEvidencijaOVakcinaciji().getObrazac().getPrimljeneVakcine();

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
