package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.time.LocalDate;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.factories.IzvestajOImunizacijiFactory;
import vakcinac.io.civil.servant.models.izv.IzvestajOImunizaciji;
import vakcinac.io.civil.servant.repository.IzvestajRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.civil.servant.security.JwtStore;
import vakcinac.io.civil.servant.security.utils.JwtUtil;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.repository.jena.RdfObject;
import vakcinac.io.core.results.agres.AggregateResult;
import vakcinac.io.core.results.doc.QueryDocumentsResult;
import vakcinac.io.core.results.link.Links;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class IzvestajService extends BaseService<IzvestajOImunizaciji> {
	
	private ZahtevService zahtevService;
	private IzjavaService izjavaService;
	private SertifikatService sertifikatService;
	private PotvrdaService potvrdaService;
    private JwtUtil jwtUtil;
    private JwtStore jwtStore;
    
    private IzvestajRepository izvestajRepository;
	
	public IzvestajService(
			ZahtevService zahtevService,
			IzjavaService izjavaService,
			SertifikatService sertifikatService,
			PotvrdaService potvrdaService,
			JwtUtil jwtUtil,
			JwtStore jwtStore,
			IzvestajRepository izvestajRepository,
			CivilServantJenaRepository jenaRepository
	) {
		super(izvestajRepository, jenaRepository);
		
		this.zahtevService = zahtevService;
		this.izjavaService = izjavaService;
		this.sertifikatService = sertifikatService;
		this.potvrdaService = potvrdaService;
		this.jwtUtil = jwtUtil;
		this.jwtStore = jwtStore;
		
		this.izvestajRepository = izvestajRepository;
	}
	
	public QueryDocumentsResult findAll() throws XMLDBException, IOException {
		return izvestajRepository.search("");
	}
	
	
	@Override
	protected Links findReferencing(String id) throws Exception {
		return new Links();
	}

	@Override
	protected Links findReferencedBy(String id) throws Exception {
		return new Links();
	}

	public Object extractRdf(String id, String type) throws IOException {
		 RdfObject rdf = jenaRepository.construct("/izvestaji", Constants.ROOT_RESOURCE + "/data/sparql/construct.sparql", String.format("%s/izvestaj/%s", Constants.ROOT_URL, id));
		 
		 if (type.equals("JSON")) {
			 return rdf.toString("RDF/JSON");
		 }
		 
		 return rdf.toString("N-TRIPLE");
	}

	@Override
	public IzvestajOImunizaciji create(IzvestajOImunizaciji izvestaj) throws Exception {
		fillRdfOfIzvestaj(izvestaj);
		
		JaxBParser parser = JaxBParserFactory.newInstanceFor(IzvestajOImunizaciji.class);
		String serializedObj = parser.marshall(izvestaj);

		jenaRepository.insert(serializedObj, "/izvestaji");

		String id = String.format("%d", baseRepository.count(".xml") + 1);
		
		return create(id, serializedObj);
	}
	
	private void fillRdfOfIzvestaj(IzvestajOImunizaciji izvestaj) throws XMLDBException, IOException {

		izvestaj.setAbout(String.format("%s/izvestaj/%d", Constants.ROOT_URL, baseRepository.count(".xml") + 1));
		izvestaj.setTypeof("IzvestajOImunizacijiDokument");
		
		izvestaj.getMeta().add(TmetaFactory.create("rdfioi:periodOd", "xsd:date", izvestaj.getOd().toString()));
		izvestaj.getMeta().add(TmetaFactory.create("rdfioi:periodDo", "xsd:date", izvestaj.getDo().toString()));
		izvestaj.getMeta().add(TmetaFactory.create("rdfioi:ukupnoDoza", "xsd:integer", izvestaj.getStatistikaDoza().getUkupnoIzdatihDoza().toString()));
		izvestaj.getMeta().add(TmetaFactory.create("rdfos:izdat", "xsd:date", izvestaj.getIzdato().toString()));
		
		izvestaj.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		izvestaj.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		izvestaj.getOtherAttributes().put(QName.valueOf("xmlns:rdfioi"), "https://www.vakcinac-io.rs/rdfs/izvestaj/");
		
		String sluzbenikId = getSluzbenikId();
		izvestaj.getLink().add(TlinkFactory.create("rdfioi:izdao",  String.format("%s/sluzbenici/%s", Constants.ROOT_URL, sluzbenikId), "rdfos:Sluzbenik"));		
	}
	
    private String getSluzbenikId() {
    	String jwt = jwtStore.getJwt();
    	String sluzbenikId = jwtUtil.extractCustomClaimFromToken(jwt, "ZaposleniId");
    	
    	return sluzbenikId;
    }

	public IzvestajOImunizaciji generate(LocalDate startDate, LocalDate endDate) throws IOException {
		int numOfZahtev = zahtevService.count("/zahtevi", startDate, endDate);
		int numOfIzjava = izjavaService.count("/izjave", startDate, endDate);
		int numOfSertifikat = sertifikatService.count(startDate, endDate);
		
		AggregateResult aggregatedByDoses = potvrdaService.aggregateByDoses(startDate, endDate);
		AggregateResult aggregatedByTypes = potvrdaService.aggregateByTypes(startDate, endDate);
		

		return IzvestajOImunizacijiFactory.create(startDate, endDate, numOfZahtev, numOfIzjava, numOfSertifikat, aggregatedByDoses, aggregatedByTypes);
		
	}

}
