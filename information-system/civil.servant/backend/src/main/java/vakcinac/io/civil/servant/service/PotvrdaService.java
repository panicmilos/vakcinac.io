package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.factories.DodajDozuFactory;
import vakcinac.io.civil.servant.factories.KreiranjePotvrdeFactory;
import vakcinac.io.civil.servant.models.pot.DodajDozu;
import vakcinac.io.civil.servant.models.pot.KreiranjePotvrde;
import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.civil.servant.models.term.Termin;
import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.security.JwtStore;
import vakcinac.io.core.models.os.InformacijeOPrimljenimDozamaIzPotvrde;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.results.agres.AggregateResult;
import vakcinac.io.core.utils.HttpUtils;

@Service
@RequestScope
public class PotvrdaService {

	@Value("${gradjanin.url}")
	private String gradjaninUrl;

	private TerminService terminService;
	private SaglasnostService saglasnostService;
	private VakcinaService vakcinaService;
	private RedCekanjaService redCekanjaService;
	private JenaRepository jenaRepository;
	
    private JwtStore jwtStore;
	private RestTemplate restTemplate;

    @Autowired
    public PotvrdaService(
    		TerminService terminService,
    		SaglasnostService saglasnostService,
    		VakcinaService vakcinaService,
    		RedCekanjaService redCekanjaService,
    		JenaRepository jenaRepository,
    		JwtStore jwtStore,
    		RestTemplate restTemplate) {
        this.terminService = terminService;
        this.saglasnostService = saglasnostService;
        this.vakcinaService = vakcinaService;
        this.redCekanjaService = redCekanjaService;
        this.jenaRepository = jenaRepository;
        this.jwtStore = jwtStore;
		this.restTemplate = restTemplate;
    }

	public AggregateResult aggregateByDoses(LocalDate startDate, LocalDate endDate) {
        return aggregate("doses", startDate, endDate);
    }
	
	public AggregateResult aggregateByTypes(LocalDate startDate, LocalDate endDate) {
        return aggregate("types", startDate, endDate);
    }
	
	private AggregateResult aggregate(String by, LocalDate startDate, LocalDate endDate) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
		ResponseEntity<AggregateResult> response = restTemplate.exchange(String.format("%s/potvrde/aggregate/%s?startDate=%s&endDate=%s", gradjaninUrl, by, startDate, endDate), HttpMethod.GET, httpEntity, AggregateResult.class);
        
        return response.getBody();
    }
	
	public Object readPlain(String id) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/potvrde/%s/preview", gradjaninUrl, id), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}

	public Object readPreview(String id, String type) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/potvrde/%s/preview?type=%s", gradjaninUrl, id, type), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}

    public InformacijeOPrimljenimDozamaIzPotvrde getVakcine(String gradjaninId) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
        ResponseEntity<InformacijeOPrimljenimDozamaIzPotvrde> response = restTemplate.exchange(String.format("%s/potvrde/gradjanin/%s/doze", gradjaninUrl, gradjaninId), HttpMethod.GET, httpEntity, InformacijeOPrimljenimDozamaIzPotvrde.class);

        return response.getBody();
    }

    public int getNumberOfVakcine(String gradjaninId) {
    	InformacijeOPrimljenimDozamaIzPotvrde info = getVakcine(gradjaninId);
    	if (info == null) {
    		return 0;
    	}
    	
    	return info.getPrimljenaDozaIzPotvrde().size();
    }

    private KreiranjePotvrde create(KreiranjePotvrde request) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(request, jwtStore.getJwt());
        restTemplate.exchange(String.format("%s/potvrde", gradjaninUrl), HttpMethod.POST, httpEntity, Object.class);

        return request;
    }

    private DodajDozu dodajDozu(DodajDozu request) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(request, jwtStore.getJwt());
        restTemplate.exchange(String.format("%s/potvrde/dodaj-dozu", gradjaninUrl), HttpMethod.POST, httpEntity, Object.class);

        return request;
    }
    
    public void create(String osobaJMBG) throws XMLDBException, IOException {
    	Termin lastTermin = terminService.findLastTermin(osobaJMBG);
    	System.out.println(lastTermin != null);
    	if (lastTermin == null || lastTermin.isRealizovan() != null) {
    		throw new BadLogicException("Korisnik nema adekvatan termin.");
    	}
    	
    	if (!hasRightSaglasnost(osobaJMBG)) {
    		throw new BadLogicException("Korisnik nema adekvatanu saglasnost.");
    	}

    	Vakcina vakcina = vakcinaService.read(lastTermin.getVakcina());
    	int proizvodjacVakcine = vakcina.getProizvodjac();
    	String nazivVakcine = getVakcinaName(proizvodjacVakcine);

    	if (getNumberOfVakcine(osobaJMBG) == 0) {
    		create(KreiranjePotvrdeFactory.create(osobaJMBG, nazivVakcine, vakcina.getSerija()));
    	} else {
    		dodajDozu(DodajDozuFactory.create(osobaJMBG, vakcina.getSerija()));
    	}

    	String lastTerminName = terminService.findLastTerminName(osobaJMBG);
    	redCekanjaService.add(GradjaninUReduFactory.create(osobaJMBG, proizvodjacVakcine, 60));
    	terminService.updateTerminStatus(lastTermin.getVreme().toLocalDate().toString(), lastTerminName.split("\\.")[0], true);
    }
    
    private boolean hasRightSaglasnost(String osobaJMBG) throws XMLDBException, IOException {
        String lastSaglasnostId = jenaRepository.readLatestSubject("/saglasnosti", "<https://www.vakcinac-io.rs/rdfs/saglasnost/za>", String.format("<https://www.vakcinac-io.rs/gradjani/%s>", osobaJMBG));
        
        SaglasnostZaSprovodjenjePreporuceneImunizacije lastSaglasnost = null;
        if (lastSaglasnostId != null) {
            String[] tokens = lastSaglasnostId.split("/");
            String id = String.join("_", tokens[tokens.length - 2], tokens[tokens.length - 1]);
            lastSaglasnost = saglasnostService.read(id);
        }
        
    	Termin lastRealisedTermin = terminService.findLastRealizedTermin(osobaJMBG);
    	if (lastRealisedTermin == null) {
    		return lastSaglasnost != null;
    	}
    	
    	return lastRealisedTermin.getVreme().toLocalDate().isBefore(lastSaglasnost.getDatumIzdavanja());

    }
    
    
    private String getVakcinaName(int proizvodjac) {
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	map.put(0, "Pfizer-BioNTech");
    	map.put(1, "Sputnik V");
    	map.put(2, "Sinopharm");
    	map.put(3, "AstraZeneca");
    	map.put(4, "Moderna");
    	
    	return map.get(proizvodjac);
    }

	
}
