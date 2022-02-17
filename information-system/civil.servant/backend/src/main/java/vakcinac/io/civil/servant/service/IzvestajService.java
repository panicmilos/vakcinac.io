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
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.results.agres.AggregateResult;
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
	
	public IzvestajService(
			ZahtevService zahtevService,
			IzjavaService izjavaService,
			SertifikatService sertifikatService,
			PotvrdaService potvrdaService,
			IzvestajRepository izvestajRepository,
			CivilServantJenaRepository jenaRepository
	) {
		super(izvestajRepository, jenaRepository);
		
		this.zahtevService = zahtevService;
		this.izjavaService = izjavaService;
		this.sertifikatService = sertifikatService;
		this.potvrdaService = potvrdaService;
	}
	
	@Override
	protected Links findReferencing(String id) throws Exception {
		return null;
	}

	@Override
	protected Links findReferencedBy(String id) throws Exception {
		return null;
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
		
		//TODO: NEkako ucitati sluzbenika?
		
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
