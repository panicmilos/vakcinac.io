package vakcinac.io.citizen.service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat.Vakcinacije;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat.Vakcinacije.Vakcinacija;
import vakcinac.io.citizen.models.dig.TnosilacSertifikata;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza;
import vakcinac.io.citizen.repository.DigitalniSertifikatRepository;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.citizen.security.utils.JwtUtil;
import vakcinac.io.core.Constants;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.mail.MailContent;
import vakcinac.io.core.mail.MailAttachment;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.repository.jena.RdfObject;
import vakcinac.io.core.results.link.Links;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.RandomUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class SertifikatService extends BaseService<DigitalniSertifikat> {
	
	@Value("${frontend.url}")
	private String frontendUrl;
	
	@Value("${backend.url}")
	private String backendUrl;
	
	private GradjaninService gradjaninService;
	private PotvrdaService potvrdaService;
	private MailingService mailingService;
	
	private JwtUtil jwtUtil;
	private JwtStore jwtStore;
	
	@Autowired
	public SertifikatService(MailingService mailingService, JwtUtil jwtUtil, JwtStore jwtStore, DigitalniSertifikatRepository sertifikatRepository, GradjaninService gradjaninService, PotvrdaService potvrdaService, CitizenJenaRepository jenaRepository) {
		super(sertifikatRepository, jenaRepository);
		
		this.gradjaninService = gradjaninService;
		this.potvrdaService = potvrdaService;
		this.mailingService = mailingService;
		this.jwtStore = jwtStore;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public DigitalniSertifikat read(String id) {
		return baseRepository.retrieve(id.replace('/', '-'));
	}
	
	@Override
	protected Links findReferencing(String id) throws Exception {
		return jenaRepository.findReferencing(String.format("%s/digitalni-sertifikat/%s", Constants.ROOT_URL, id.replace('-', '/')), "/sertifikat");
	}

	@Override
	protected Links findReferencedBy(String id) throws Exception {
		return jenaRepository.findReferencedBy(String.format("%s/digitalni-sertifikat/%s", Constants.ROOT_URL, id.replace('-', '/')));
	}
	
	public String extractRdf(String id, Object type) throws Exception {
		 RdfObject rdf = jenaRepository.construct("/sertifikat", Constants.ROOT_RESOURCE + "/data/sparql/construct.sparql", String.format("%s/digitalni-sertifikat/%s", Constants.ROOT_URL, id));
		 
		 if (type.equals("JSON")) {
			 return rdf.toString("RDF/JSON");
		 }
		 
		 return rdf.toString("N-TRIPLE");
	}
	
	@Override
	public DigitalniSertifikat delete(String id) {
		return baseRepository.remove(id.replace('/', '-'));
	}
	
	@Override
	public DigitalniSertifikat create(DigitalniSertifikat sertifikat) throws XMLDBException, IOException {
			
		String id = getValidSertifikatId();
		sertifikat.setBrojSertifikata(id);;
		
		sertifikat.setValidacija(String.format("%s/dokumenti/digitalni-sertifikat/%s", frontendUrl, id));
		
		String gradjaninId;
		TnosilacSertifikata nosilac = sertifikat.getNosilacSertifikata();
		if (nosilac.getJmbg() != null && !nosilac.getJmbg().isEmpty()) {
			gradjaninId = nosilac.getJmbg();
		}
		else if (nosilac.getBrPasosa() != null && !nosilac.getBrPasosa().isEmpty()) {
			gradjaninId = nosilac.getBrPasosa();
		}
		else if (nosilac.getEbs() != null && !nosilac.getEbs().isEmpty()) {
			gradjaninId = nosilac.getEbs();
		}
		else {
			throw new BadLogicException("Id građanina nije validan.");
		}
		
		fillOutNosilacSertifikata(sertifikat, gradjaninId);
		fillOutRdf(sertifikat, gradjaninId);
		
	    JaxBParser parser = JaxBParserFactory.newInstanceFor(DigitalniSertifikat.class);
        String serializedObj = parser.marshall(sertifikat);
        jenaRepository.insert(serializedObj, "/sertifikat");
    
        DigitalniSertifikat created = create(id.replace('/', '-'), sertifikat);
        
        sendApproveEmail(id, gradjaninId);
        
        return created;
	}
	
	private void sendApproveEmail(String sertifikatId, String gradjaninId) {
		Tgradjanin gradjanin = gradjaninService.findById(gradjaninId);
		
		MailContent mailContent = new MailContent();
		mailContent.setTo(gradjanin.getEmail());
		mailContent.setSubject("Odobren zahtev za digitalni sertifikat");
		
		String body = String.format("Poštovani,\nOdobren Vam je zahtev za digitalni sertifikat.\n");
		mailContent.setText(body);
			
		MailAttachment htmlDoc = createAttachmentForType(sertifikatId, "html");
		System.out.println(htmlDoc.getPath());
		
		MailAttachment pdfDoc = createAttachmentForType(sertifikatId, "pdf");
		System.out.println(pdfDoc.getPath());
		
		List<MailAttachment> attach = new ArrayList<MailAttachment>();
		attach.add(htmlDoc);
		attach.add(pdfDoc);	
		mailContent.setAttachments(attach);
		
		mailingService.Send(mailContent);
	}
	
	private MailAttachment createAttachmentForType(String sertifikatId, String type) {
		String downloadLink = createLinkForType(sertifikatId, type);
		
		MailAttachment doc = new MailAttachment();
		doc.setFilename(String.format("sertifikat.%s", type));
		doc.setPath(downloadLink);
		
		return doc;
	}
	
	private String createLinkForType(String id, String type) {
		return String.format("%s/sertifikati/query/%s?type=%s", backendUrl, id, type);
	}
	
	private void fillOutRdf(DigitalniSertifikat sertifikat, String gradjaninId) throws XMLDBException, IOException {
		String id = sertifikat.getBrojSertifikata();
		
		sertifikat.setAbout(String.format("%s/digitalni-sertifikat/%s", Constants.ROOT_URL, id));
		sertifikat.setTypeof("rdfos:DigitalniSertifikatDokument");
		
		String za = String.format("%s/gradjani/%s", Constants.ROOT_URL, gradjaninId);
		sertifikat.getLink().add(TlinkFactory.create("rdfds:za", za, "rdfos:Gradjanin"));
		
		String sluzbenikId = getSluzbenikId();
		String izdao = String.format("%s/sluzbenici/%s", Constants.ROOT_URL, sluzbenikId);
		sertifikat.getLink().add(TlinkFactory.create("rdfds:izdao", izdao, "rdfos:Sluzbenik"));
		
		String potvrda = getRelatedPotvrda(za);
		if(potvrda == null || potvrda.trim().isEmpty()) {
            throw new MissingEntityException(String.format("Ne postoji potvrda za građanina %s", za));
        }
		sertifikat.getLink().add(TlinkFactory.create("rdfds:saPotvrdom", potvrda, "rdfos:PotvrdaOVakcinisanjuDokument"));
		
		fillOutVakcine(sertifikat, potvrda);
		
		sertifikat.getMeta().add(TmetaFactory.create("rdfos:izdat", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));
		
		sertifikat.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		sertifikat.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		sertifikat.getOtherAttributes().put(QName.valueOf("xmlns:rdfds"), "https://www.vakcinac-io.rs/rdfs/digitalni-sertifikat/");
	}
	
	private String getSluzbenikId() {
		String jwt = jwtStore.getJwt();
		
		return jwtUtil.extractCustomClaimFromToken(jwt, "ZaposleniId");
	}
	
	private void fillOutVakcine(DigitalniSertifikat sertifikat, String potvrda) {
		String XQueryExpression = String.format("collection('/db/potvrde')//*:potvrda-o-izvrsenoj-vakcinaciji[@about = '%s']", potvrda);
		
		PotvrdaOIzvrsenojVakcinaciji potvrdaVakc = potvrdaService.findFirstByXQuery(XQueryExpression, PotvrdaOIzvrsenojVakcinaciji.class);
		PodaciOVakcinaciji podaciOVakc = potvrdaVakc.getPodaciOVakcinaciji();
		
		Vakcinacije vakcinacije = new Vakcinacije();
		Integer brojDoza = podaciOVakc.getPodaciODozama().getPrimljenaDoza().size();
		
		vakcinacije.setBrojDoza(new BigInteger(brojDoza.toString()));
		
		for(PrimljenaDoza primljenaDoza : podaciOVakc.getPodaciODozama().getPrimljenaDoza()) {
			Vakcinacija vakcinacija = new Vakcinacija();
			vakcinacija.setDoza(primljenaDoza.getRedniBroj());
			vakcinacija.setZdravstvenaUstanova(podaciOVakc.getZdravstvenaUstanova());
			vakcinacija.setDatum(primljenaDoza.getDatum());
			vakcinacija.setSerija(primljenaDoza.getSerija());
			vakcinacija.setProizvodjac(podaciOVakc.getNazivVakcine());
			vakcinacija.setTip(podaciOVakc.getNazivVakcine());
			
			vakcinacije.getVakcinacija().add(vakcinacija);
		}
		
		sertifikat.setVakcinacije(vakcinacije);
	}
	
	private String getRelatedPotvrda(String za) {
		return jenaRepository.readLatestSubject("/potvrda", String.format("<%s/rdfs/potvrda/za>", Constants.ROOT_URL), String.format("<%s>", za));
	}
	
	private void fillOutNosilacSertifikata(DigitalniSertifikat sertifikat, String gradjaninId) {
		Tgradjanin gradjanin = gradjaninService.findById(gradjaninId);
		if (gradjanin == null) {
			throw new MissingEntityException("Građanin sa unesenim id-em ne postoji.");
		}
		
		TnosilacSertifikata nosilac = sertifikat.getNosilacSertifikata();
		nosilac.setIme(gradjanin.getIme());
		nosilac.setPrezime(gradjanin.getPrezime());
		nosilac.setPol(gradjanin.getPol());
		nosilac.setDatumRodjenja(DateUtils.fromXMLToLocalDate(gradjanin.getDatumRodjenja()));
	}
	
	private String getValidSertifikatId() {
        String id = "";

        do {
            id =  generateSertifikatId();
        } while (baseRepository.retrieve(id) != null);

        return id;
    }
	
	 private String generateSertifikatId() {
		 return String.format("%s/%s", RandomUtils.generateRandomNumericString(7), RandomUtils.generateRandomNumericString(2));
	 }

}
