package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.civil.servant.models.izj.Tpodnosilac;
import vakcinac.io.civil.servant.models.red.RedCekanja.GradjaninURedu;
import vakcinac.io.civil.servant.repository.IzjavaRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.mail.MailContent;
import vakcinac.io.core.models.os.InformacijeOPrimljenimDozamaIzPotvrde;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.models.os.Tkontakt;
import vakcinac.io.core.repository.jena.RdfObject;
import vakcinac.io.core.results.link.Links;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class IzjavaService extends BaseService<IzjavaInteresovanjaZaVakcinisanje> {

	private GradjaninService gradjaninService;
	private TerminService terminService;
	private RedCekanjaService redCekanjaService;
	private PotvrdaService potvrdaService;
	private MailingService mailingService;
	private AuthenticationService authenticationService;
	
	public IzjavaService(
			GradjaninService gradjaninService,
			TerminService terminService,
			RedCekanjaService redCekanjaService,
			PotvrdaService potvrdaService,
			MailingService mailingService,
			AuthenticationService authenticationService,
			IzjavaRepository izjavaRepository,
			CivilServantJenaRepository jenaRepository) {
		super(izjavaRepository, jenaRepository);
		
		this.gradjaninService = gradjaninService;
		this.terminService = terminService;
		this.redCekanjaService = redCekanjaService;
		this.potvrdaService = potvrdaService;
		this.mailingService = mailingService;
		this.authenticationService = authenticationService;
	}
	
	public String getIzjavaZa(String za) {
		return jenaRepository.readLatestSubject("/izjava", "<https://www.vakcinac-io.rs/rdfs/interesovanje/za>", String.format("<%s>", za));
	}

	@Override
	protected Links findReferencing(String id) throws Exception {
		return jenaRepository.findReferencing(String.format("%s/izjava/%s", Constants.ROOT_URL, id.replace('_', '/')), "/izjava");
	}

	@Override
	protected Links findReferencedBy(String id) throws Exception {
		return jenaRepository.findReferencedBy(String.format("%s/izjava/%s", Constants.ROOT_URL, id.replace('_', '/')));
	}
	
	public Object extractRdf(String id, String type) throws IOException {
		 RdfObject rdf = jenaRepository.construct("/izjava", Constants.ROOT_RESOURCE + "/data/sparql/construct.sparql", String.format("%s/izjava/%s", Constants.ROOT_URL, id));
		 
		 if (type.equals("JSON")) {
			 return rdf.toString("RDF/JSON");
		 }
		 
		 return rdf.toString("N-TRIPLE");
	}
	
	@Override
	public IzjavaInteresovanjaZaVakcinisanje create(IzjavaInteresovanjaZaVakcinisanje izjava) throws XMLDBException, IOException {
		
		String jmbg = izjava.getPodnosilacIzjave().getPodnosilac().getJmbg();
		Tgradjanin gradjanin = gradjaninService.read(jmbg);
		String currentUserUsername = authenticationService.getCurrentUserUsername();

		if (!gradjanin.getKorisnickoIme().equals(currentUserUsername)) {
			throw new BadLogicException("Nije moguće iskazati interesovanje za drugu osobu.");
		}
		
		if (terminService.hasActiveTermin(jmbg) || redCekanjaService.isInRow(jmbg) || potvrdaService.getNumberOfVakcine(jmbg) == 3) {
			throw new BadLogicException("Trenutno nije moguće iskazati interesovanje za vakcinacijom.");
		}
		
		if (potvrdaService.getNumberOfVakcine(jmbg) != 0) {
			InformacijeOPrimljenimDozamaIzPotvrde info = potvrdaService.getVakcine(jmbg);
			Integer rightProizvodjac = getVakcinaProizvodjac(info.getNazivVakcine());
			
			if (rightProizvodjac != izjava.getInformacijeOPrimanjuVakcine().getProizvodjaci().getProizvodjac().get(0)) {
				throw new BadLogicException("Sve vakcine moraju biti od istog proizvodjaca.");
			}
		}
		
		fillRestOfIzjava(izjava, gradjanin);
		
		String id = String.format("%s_%d", jmbg, baseRepository.count(jmbg) + 1);
		fillOutRdf(jmbg, izjava);
		
		sendEmail(izjava);

		JaxBParser parser = JaxBParserFactory.newInstanceFor(IzjavaInteresovanjaZaVakcinisanje.class);
		String serializedObj = parser.marshall(izjava);
		jenaRepository.insert(serializedObj, "/izjava");
		
		GradjaninURedu gradjaninURedu = GradjaninUReduFactory.create(jmbg, izjava, 1);
		redCekanjaService.add(gradjaninURedu);

		return create(id, serializedObj);
	}
	
	private void sendEmail(IzjavaInteresovanjaZaVakcinisanje izjava) {
		MailContent mailContent = new MailContent();
		mailContent.setTo(izjava.getPodnosilacIzjave().getKontakt().getAdresaElektronskePoste());
		mailContent.setSubject("Uspešno iskazano interesovanje za prijem vakcine");
		
		Tpodnosilac podnosilac = izjava.getPodnosilacIzjave().getPodnosilac();
		Tkontakt kontakt = izjava.getPodnosilacIzjave().getKontakt();
		
		String body = String.format("Poštovani,\nDobijate prvi slobodan termin za vakcinu.\nPodaci koje ste uneil:"
				+ "\nJMBG: %s\nIme: %s\nPrezime: %s\nAdresa elektronske pošte: %s\nBroj mobilnog telefona: %s\n"
				+ "Broj fiksnog telefona: %s\nLokacija gde primate vakcinu: %s\nProizvođači vakcina: ", podnosilac.getJmbg(), podnosilac.getIme(), podnosilac.getPrezime(), 
				kontakt.getAdresaElektronskePoste(), kontakt.getBrojMobilnogTelefona(), kontakt.getBrojFiksnogTelefona(), izjava.getInformacijeOPrimanjuVakcine().getOpstina());
		List<Integer> proizvodjaci = izjava.getInformacijeOPrimanjuVakcine().getProizvodjaci().getProizvodjac();
		for(int i = 0 ; i < proizvodjaci.size(); i++) {
			body += getVakcinaName(proizvodjaci.get(i));
			if (i < proizvodjaci.size() - 1) {
				body +=  ", ";
			}
		}
		String davalacKrvi = izjava.getPodnosilacIzjave().isDobrovoljanDavalacKrvi() ? "Da" : "Ne";
		body += String.format("\nDobrovoljan davalac krvi: %s", davalacKrvi);
		
		mailContent.setText(body);
				
		mailingService.Send(mailContent);
	}
	
	private String getVakcinaName(int proizvodjac) {
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	map.put(0, "Pfizer-BioNTech");
    	map.put(1, "Sputnik V");
    	map.put(2, "Sinopharm");
    	map.put(3, "AstraZeneca");
    	map.put(4, "Moderna");
    	map.put(5, "Bilo koja");
    	
    	return map.get(proizvodjac);
    }
	
    private Integer getVakcinaProizvodjac(String nazivVakcine) {
    	Map<String, Integer> map = new HashMap<>();
    	map.put("Pfizer-BioNTech", 0);
    	map.put("Sputnik V", 1);
    	map.put("Sinopharm", 2);
    	map.put("AstraZeneca", 3);
    	map.put("Moderna", 4);
    	
    	return map.get(nazivVakcine);
    }

	private void fillOutRdf(String gradjaninId, IzjavaInteresovanjaZaVakcinisanje izjava) throws XMLDBException, IOException {

		String id =  String.format("%s/%d", gradjaninId, baseRepository.count(gradjaninId) + 1);

		izjava.setAbout(String.format("%s/izjava/%s", Constants.ROOT_URL, id));
		izjava.setTypeof("rdfos:IzjavaInteresovanjaZaVakcinisanjeDokument");
		izjava.setDan(LocalDate.now());

		izjava.getLink().add(TlinkFactory.create("rdfiizv:za", String.format("%s/gradjani/%s", Constants.ROOT_URL, gradjaninId), "rdfos:Gradjanin"));
		for (int proizvodjac : izjava.getInformacijeOPrimanjuVakcine().getProizvodjaci().getProizvodjac()) {
			izjava.getLink().add(TlinkFactory.create("rdfiizv:zeljeneVakcine", String.format("%s/vakcine/%d", Constants.ROOT_URL, proizvodjac), "rdfos:Vakcina"));
		}

		izjava.getMeta().add(TmetaFactory.create("rdfiizv:uOpstini", "xsd:string", izjava.getInformacijeOPrimanjuVakcine().getOpstina()));
		izjava.getMeta().add(TmetaFactory.create("rdfos:izdat", "xsd:date", LocalDateUtils.toXMLDateString(LocalDate.now())));

		izjava.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		izjava.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		izjava.getOtherAttributes().put(QName.valueOf("xmlns:rdfiizv"), "https://www.vakcinac-io.rs/rdfs/interesovanje/");
	}
	
	private void fillRestOfIzjava(IzjavaInteresovanjaZaVakcinisanje izjava, Tgradjanin gradjanin) {
		izjava.getPodnosilacIzjave().getPodnosilac().setIme(gradjanin.getIme());
		izjava.getPodnosilacIzjave().getPodnosilac().setPrezime(gradjanin.getPrezime());
		izjava.getPodnosilacIzjave().getKontakt().setAdresaElektronskePoste(gradjanin.getEmail());
	}

}
