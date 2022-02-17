package vakcinac.io.citizen.service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.core.Constants;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.models.os.Tlink;
import vakcinac.io.core.models.os.Tmeta;
import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.repository.exist.CloseableResource;
import vakcinac.io.core.repository.jena.CloseableResultSet;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.results.agres.AggregateResult;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.RandomUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;


@Service
@RequestScope
public class PotvrdaService extends BaseService<PotvrdaOIzvrsenojVakcinaciji> {

    @Autowired
    private GradjaninService gradjaninService;

    @Autowired
    private VakcinaService vakcinaService;

    protected PotvrdaService(ExistRepository<PotvrdaOIzvrsenojVakcinaciji> baseRepository, JenaRepository jenaRepository) {
        super(baseRepository, jenaRepository);
    }
    
    public AggregateResult aggregateByDose(LocalDate startDate, LocalDate endDate) throws XMLDBException, IOException {
    	return aggregate("aggregate-doses", startDate, endDate);
    }
    
    public AggregateResult aggregateByTypes(LocalDate startDate, LocalDate endDate) throws XMLDBException, IOException {
    	return aggregate("aggregate-types", startDate, endDate);
    }
    
    private AggregateResult aggregate(String fileName, LocalDate startDate, LocalDate endDate) throws XMLDBException, IOException {
    	ResourceIterator iterator = baseRepository.retrieveUsingXQuery(String.format("%s/data/xquery/%s.xqy", Constants.ROOT_RESOURCE, fileName), startDate, endDate);
    		
        try (CloseableResource resource = new CloseableResource()) {
    		resource.setRealResource(iterator.nextResource());
            
            JaxBParser parser = JaxBParserFactory.newInstanceFor(AggregateResult.class);
	        return parser.unmarshall(resource.getContent().toString());
        }
    }

    public PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji readVakcinePotvrdePoGradjanu(String gradjaninId) {

        String potvrdaId = getPotvrdaIdByGradjaninId(gradjaninId);

        PotvrdaOIzvrsenojVakcinaciji potvrda = read(potvrdaId);

        if (potvrda == null) {
            return null;
        }

        return potvrda.getPodaciOVakcinaciji();
    }

    private String getPotvrdaIdByGradjaninId(String gradjaninId) {
        String gradjaninUrl = String.format("<%s/gradjani/%s>", Constants.ROOT_URL, gradjaninId);

        String potvrdaAbout = "";
        try(CloseableResultSet set = jenaRepository.read("/potvrda", String.format("?s %s %s", "<https://www.vakcinac-io.rs/rdfs/potvrda/za>", gradjaninUrl))) {
            if(!set.hasNext()) {
                return null;
            }

            potvrdaAbout = set.next().get("?s").toString();
        }

        String[] potvrdaSplit = potvrdaAbout.split("/");

        return potvrdaSplit[potvrdaSplit.length-1];
    }
    
    @Override
     public PotvrdaOIzvrsenojVakcinaciji create(PotvrdaOIzvrsenojVakcinaciji potvrda) throws Exception {
        validateVakcina(potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().get(0).getSerija());

        String id = getValidPotvrdaId();

        potvrda.setQrKod(String.format("%s/potvrda/%s", Constants.ROOT_URL, id));

        Tgradjanin gradjanin = gradjaninService.findById(potvrda.getPodaciOVakcinisanom().getJmbg());

        fillOutPodaciOVakcinisanom(potvrda, gradjanin);
        fillOutRdf(id, potvrda);

        JaxBParser parser = JaxBParserFactory.newInstanceFor(PotvrdaOIzvrsenojVakcinaciji.class);
        String serializedObj = parser.marshall(potvrda);
        jenaRepository.insert(serializedObj, "/potvrda");

        return create(id, serializedObj);
    }

    private void validateVakcina(String serija) {
        if (vakcinaService.getVakcina(serija) == null) {
            throw new MissingEntityException("Vakcina ne postoji.");
        }
    }

    private void fillOutRdf(String potvrdaId, PotvrdaOIzvrsenojVakcinaciji potvrda) throws XMLDBException, IOException {

        int brDoza = potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().size();
        String gradjaninId = potvrda.getPodaciOVakcinisanom().getJmbg();

        potvrda.setAbout(String.format("%s/potvrda/%s", Constants.ROOT_URL, potvrdaId));
        potvrda.setTypeof("rdfos:PotvrdaOVakcinisanjuDokument");

        potvrda.setDatumIzdavanja(LocalDate.now());
        potvrda.setBrojPrimljenihDoza(new BigInteger(String.valueOf(brDoza)));
        potvrda.setSifraPotvrde(potvrdaId);

        String za = String.format("%s/gradjani/%s", Constants.ROOT_URL, gradjaninId);

        String interesovanje = getRelatedInteresovanje(za);
        if(interesovanje == null || interesovanje.trim().isEmpty()) {
            throw new MissingEntityException(String.format("No interesovanje for gradjanin %s", za));
        }

        String saglasnost = getRelatedSaglasnost(za);
        potvrda.getLink().add(TlinkFactory.create("rdfpoiv:saSaglasnoscu",  saglasnost, "rdfos:SaglasnostImunizacijeDokument"));

        potvrda.getLink().add(TlinkFactory.create("rdfpoiv:naOsnovuInteresovanja", interesovanje, "rdfos:IzjavaInteresovanjaZaVakcinisanjeDokument"));

        potvrda.getLink().add(TlinkFactory.create("rdfpoiv:za", za, "rdfos:Gradjanin"));
        potvrda.getLink().add(TlinkFactory.create("rdfpoiv:izdao",  String.format("%s/sluzbenici/%s", Constants.ROOT_URL, gradjaninId), "rdfos:Sluzbenik"));

        potvrda.getMeta().add(TmetaFactory.create("rdfpoiv:izmenjen", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));
        potvrda.getMeta().add(TmetaFactory.create("rdfpoiv:brojDoza", "xsd:integer", potvrda.getBrojPrimljenihDoza().toString()));
        potvrda.getMeta().add(TmetaFactory.create("rdfos:izdat", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));

        addGeneralAttributes(potvrda);
    }

    private void addGeneralAttributes(PotvrdaOIzvrsenojVakcinaciji potvrda) {
        potvrda.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
        potvrda.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
        potvrda.getOtherAttributes().put(QName.valueOf("xmlns:rdfpoiv"), "https://www.vakcinac-io.rs/rdfs/potvrda/");
    }

    private String getRelatedInteresovanje(String za) {
        return jenaRepository.readLatestSubject("/izjava", "<https://www.vakcinac-io.rs/rdfs/interesovanje/za>", String.format("<%s>", za));
    }

    public PotvrdaOIzvrsenojVakcinaciji addDoza(String gradjaninId, String serija) throws XMLDBException, IOException {
        validateVakcina(serija);

        String potvrdaId = getPotvrdaIdByGradjaninId(gradjaninId);

        PotvrdaOIzvrsenojVakcinaciji potvrda = read(potvrdaId);

        if (potvrda == null) {
            throw new MissingEntityException("Potvrda nije pronadjena.");
        }

        PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza primljenaDoza = new PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza();
        primljenaDoza.setDatum(LocalDate.now());
        primljenaDoza.setSerija(serija);

        int brDoza = potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().size();
        primljenaDoza.setRedniBroj(new BigInteger(String.valueOf(brDoza + 1)));

        potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().add(primljenaDoza);

        updateMetadata(potvrdaId, potvrda);
        addGeneralAttributes(potvrda);

        JaxBParser saglasnostParser = JaxBParserFactory.newInstanceFor(PotvrdaOIzvrsenojVakcinaciji.class, Boolean.FALSE);
        String serializedObj = saglasnostParser.marshall(potvrda);

        baseRepository.store(potvrdaId, serializedObj);
        jenaRepository.updateData(potvrda.getAbout(), serializedObj, "/potvrda");

        return potvrda;
    }

    private void updateMetadata(String fileName, PotvrdaOIzvrsenojVakcinaciji potvrda) {

        int brDoza = potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().size();
        String za = String.format("%s/gradjani/%s", Constants.ROOT_URL, potvrda.getPodaciOVakcinisanom().getJmbg());
        System.out.println(za);

        String saglasnost = getRelatedSaglasnost(za);
        Tlink saglasnostLink = TlinkFactory.create("rdfpoiv:saSaglasnoscu",  saglasnost, "rdfos:SaglasnostImunizacijeDokument");
        potvrda.getLink().add(saglasnostLink);

        Optional<Tmeta> brojDozaMeta = potvrda.getMeta().stream()
                .filter(meta -> meta.getProperty().equals("rdfpoiv:brojDoza"))
                .findFirst();
        brojDozaMeta.ifPresent(meta -> meta.setValue(String.valueOf(brDoza)));

        Optional<Tmeta> izmenjenMeta = potvrda.getMeta().stream()
                .filter(meta -> meta.getProperty().equals("rdfpoiv:izmenjen"))
                .findFirst();
        izmenjenMeta.ifPresent(meta -> meta.setValue(String.valueOf(LocalDate.now())));

    }

    private String getRelatedSaglasnost(String za) {
        String saglasnost =  jenaRepository.readLatestSubject("/saglasnosti", "<https://www.vakcinac-io.rs/rdfs/saglasnost/za>", String.format("<%s>", za));

        if(saglasnost == null || saglasnost.trim().isEmpty()) {
            throw new MissingEntityException(String.format("No saglasnost for gradjanin %s", za));
        }

        return saglasnost;
    }

    private void fillOutPodaciOVakcinisanom(PotvrdaOIzvrsenojVakcinaciji potvrda, Tgradjanin tgradjanin) {
        potvrda.getPodaciOVakcinisanom().setPol(tgradjanin.getPol());
        potvrda.getPodaciOVakcinisanom().setIme(tgradjanin.getIme());
        potvrda.getPodaciOVakcinisanom().setPrezime(tgradjanin.getPrezime());
        potvrda.getPodaciOVakcinisanom().setDatumRodjenja(LocalDateUtils.from(tgradjanin.getDatumRodjenja()));
    }

    private String getValidPotvrdaId() {
        String id = "";

        do {
            id =  generatePotvrdaId();
        } while (baseRepository.retrieve(id) != null);

        return id;
    }

    private String generatePotvrdaId() {
        return String.format("%s-%s", RandomUtils.generateRandomNumericString(6), RandomUtils.generateRandomNumericString(6));
    }
}
