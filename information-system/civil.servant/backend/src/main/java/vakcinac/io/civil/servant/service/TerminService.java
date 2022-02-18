package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.factories.TerminFactory;
import vakcinac.io.civil.servant.models.red.RedCekanja;
import vakcinac.io.civil.servant.models.stanj.StanjeVakcina;
import vakcinac.io.civil.servant.models.term.Termin;
import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.repository.TerminRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
public class TerminService extends BaseService<Termin> {
	
	private TerminRepository terminRepository;
	private VakcinaService vakcinaService;
	private StanjeVakcinaService stanjeVakcinaService;
	private ZaposleniService zaposleniService;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
	
	public TerminService(StanjeVakcinaService stanjeVakcinaService, VakcinaService vakcinaService, ZaposleniService zaposleniService, TerminRepository terminRepository, CivilServantJenaRepository jenaRepository) {
		super(terminRepository, jenaRepository);
		
		this.terminRepository = terminRepository;
		this.stanjeVakcinaService = stanjeVakcinaService;
		this.vakcinaService = vakcinaService;
		this.zaposleniService = zaposleniService;
	}

	@Override
	public Termin create(Termin termin) throws Exception {
		String id = createTerminId(termin);
		
        stanjeVakcinaService.decStockFor(termin.getVakcina());
             
		return create(formatter.format(termin.getVreme()), id, termin);
	}
	
	private String createTerminId(Termin termin) throws XMLDBException, IOException {
		LocalDateTime terminTime = termin.getVreme();
		int period = ((terminTime.getHour() * 60 + terminTime.getMinute()) - 540) / 10;
        
        int numberOfTerminsInPeriod = baseRepository.count(formatter.format(terminTime), String.format("termin_%d", period));
        
        return String.format("termin_%d_%d", period, numberOfTerminsInPeriod);
	}
	
	public Termin findLastRealizedTermin(String citizenId) throws XMLDBException, IOException {
		return executeFindLastTermin(citizenId, "find_last_realized_termin_for_citizen");
	}
	
	public Termin findLastTermin(String citizenId) throws XMLDBException, IOException {
		return executeFindLastTermin(citizenId, "find_last_termin_for_citizen");
	}
	
	private Termin executeFindLastTermin(String citizenId, String fileName) throws XMLDBException, IOException {
		LocalDateTime dateTime = LocalDateTime.now();
		
		ResourceIterator iterator = baseRepository.retrieveUsingXQuery(String.format("%s/data/xquery/%s.xqy", Constants.ROOT_RESOURCE, fileName), dateTime.toString(), citizenId);
		
		if (!iterator.hasMoreResources()) {
			return null;
		}
		
		String serializedTermin = iterator.nextResource().getContent().toString();
		
		JaxBParser parser = JaxBParserFactory.newInstanceFor(Termin.class);
		return parser.unmarshall(serializedTermin);
	}
	
	public String findLastTerminName(String citizenId) throws XMLDBException, IOException {
		LocalDateTime dateTime = LocalDateTime.now();
		
		ResourceIterator iterator = baseRepository.retrieveUsingXQuery(String.format("%s/data/xquery/%s.xqy", Constants.ROOT_RESOURCE, "find_last_termin_name_for_citizen"), dateTime.toString(), citizenId);
		
		if (!iterator.hasMoreResources()) {
			return null;
		}
		
		return iterator.nextResource().getContent().toString();
	}
	
	public boolean hasActiveTermin(String citizenId) throws XMLDBException, IOException {
		LocalDateTime dateTime = LocalDateTime.now();
		
		ResourceIterator iterator = baseRepository.retrieveUsingXQuery(String.format("%s/data/xquery/has_active_termin.xqy", Constants.ROOT_RESOURCE), dateTime.toString(), citizenId);
		
		return iterator.hasMoreResources();
	}
	
	public Termin findAvaiableTermin(RedCekanja.GradjaninURedu gradjaninURedu) throws XMLDBException, IOException {

		if (!isRightTime(gradjaninURedu)) {
			return null;
		}

		Vakcina rightVakcina = findRightVaccine(gradjaninURedu);
		if (rightVakcina == null) {
			return null;
		}

		return TerminFactory.create(gradjaninURedu.getJmbg(), gradjaninURedu.getBrojPasosaEbs(), rightVakcina.getSerija(), findRightTerminPeriod(gradjaninURedu));
	}
	
	
	public void updateNonHeldTerminiStatus() throws XMLDBException, IOException {
		LocalDateTime maxDateTime = LocalDateTime.now();
		LocalDate currentDate = LocalDate.now();
		
		List<String> termini = terminRepository.findNotHeldTerminiForDate(maxDateTime.toString(), formatter.format(currentDate));
		for(String terminId: termini) {
			Termin termin = read(currentDate.toString(), terminId);
			
			updateTerminStatus(currentDate.toString(), terminId, false);
			
			stanjeVakcinaService.incStockFor(termin.getVakcina());
		}
	}
	
	public void updateTerminStatus(String additionalCollectionUri, String terminId, boolean realizovan) throws XMLDBException, IOException {		
		baseRepository.update(additionalCollectionUri, terminId, "//*:realizovan", realizovan);
		baseRepository.update(additionalCollectionUri, terminId, "//*:realizovan/@xsi:nil", false);
	}
	
	private boolean isRightTime(RedCekanja.GradjaninURedu gradjaninURedu) {
		LocalDate currentTime = LocalDate.now();
		LocalDate minTime = gradjaninURedu.getMinimalnoVreme();
		
		return minTime.isBefore(currentTime);
	}
	
	private Vakcina findRightVaccine(RedCekanja.GradjaninURedu gradjaninURedu) {
		StanjeVakcina stanjeVakcina = stanjeVakcinaService.read("stanje-vakcina");

		for (Integer zeljenaVakcina : gradjaninURedu.getVakcine().getVakcina()) {

			for (StanjeVakcina.StanjeVakcine stanjeVakcine : stanjeVakcina.getStanjeVakcine()) {
				
				if (stanjeVakcine.getDostupno().intValue() == 0) {
					continue;
				}
			
				Vakcina vakcina = vakcinaService.read(stanjeVakcine.getVakcina());
				
				if (vakcina.getProizvodjac() == zeljenaVakcina || vakcina.getProizvodjac() == 5) {
					return vakcina;
				}
			}
		}
		
		return null;
	}
	
	

	private LocalDateTime findRightTerminPeriod(RedCekanja.GradjaninURedu gradjaninURedu) throws XMLDBException, IOException {
		LocalDate iterator = LocalDateUtils.max(gradjaninURedu.getMinimalnoVreme(), LocalDate.now());

		while (true) {
			int numOfZdravstveniRadnici = zaposleniService.totalNumberOfZdravstveniRadnici();
			int terminPeriod = terminRepository.findTermin(numOfZdravstveniRadnici, formatter.format(iterator));
			
			if (terminPeriod != -1) {
				LocalDateTime dateTime = iterator.atStartOfDay();
				
				return dateTime.plusMinutes(540 + terminPeriod * 10);
			}
			
			iterator = iterator.plusDays(1);			
		}
	}
}
