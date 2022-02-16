package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
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
@RequestScope
public class TerminService extends BaseService<Termin> {
	
	private TerminRepository terminRepository;
	private VakcinaService vakcinaService;
	private StanjeVakcinaService stanjeVakcinaService;
	
	public TerminService(StanjeVakcinaService stanjeVakcinaService, VakcinaService vakcinaService, TerminRepository terminRepository, CivilServantJenaRepository jenaRepository) {
		super(terminRepository, jenaRepository);
		
		this.terminRepository = terminRepository;
		this.stanjeVakcinaService = stanjeVakcinaService;
		this.vakcinaService = vakcinaService;
	}

	@Override
	public Termin create(Termin termin) throws Exception {
		LocalDateTime terminTime = termin.getVreme();
		int period = ((terminTime.getHour() * 60 + terminTime.getMinute()) - 540) / 10;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

        stanjeVakcinaService.decStockFor(termin.getVakcina());
        
        int numberOfTerminsInPeriod = baseRepository.count(formatter.format(terminTime), String.format("termin_%d", period));
		return create(formatter.format(terminTime), String.format("termin_%d_%d", period, numberOfTerminsInPeriod), termin);
	}
	
	public Termin findLastTermin(String citizenId) throws XMLDBException, IOException {
		LocalDateTime dateTime = LocalDateTime.now();
		
		ResourceIterator iterator = baseRepository.retrieveUsingXQuery(String.format("%s/data/xquery/find_last_termin_for_citizen.xqy", Constants.ROOT_RESOURCE), dateTime.toString(), citizenId);
		
		if (!iterator.hasMoreResources()) {
			return null;
		}
		
		String serializedTermin = iterator.nextResource().getContent().toString();
		
		JaxBParser parser = JaxBParserFactory.newInstanceFor(Termin.class);
		return parser.unmarshall(serializedTermin);
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
	
	private boolean isRightTime(RedCekanja.GradjaninURedu gradjaninURedu) {
		LocalDate currentTime = LocalDate.now();
		LocalDate minTime = gradjaninURedu.getMinimalnoVreme();
		
		return minTime.isAfter(currentTime);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

		while (true) {
			int terminPeriod = terminRepository.findTermin(5, formatter.format(iterator));
			
			if (terminPeriod != -1) {
				LocalDateTime dateTime = iterator.atStartOfDay();
				
				return dateTime.plusMinutes(540 + terminPeriod * 10);
			}
			
			iterator = iterator.plusDays(1);			
		}
	}
}
