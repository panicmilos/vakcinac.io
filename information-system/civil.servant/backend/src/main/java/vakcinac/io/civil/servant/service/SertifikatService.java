package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.civil.servant.models.dig.DeclineZahtev;
import vakcinac.io.civil.servant.models.dig.DigitalniSertifikat;
import vakcinac.io.civil.servant.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.civil.servant.security.JwtStore;
import vakcinac.io.core.Constants;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.mail.MailContent;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.models.os.Tmeta;
import vakcinac.io.core.responses.CountResponse;
import vakcinac.io.core.results.link.DocumentLinksResult;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class SertifikatService {
	
	@Value("${gradjanin.url}")
	private String gradjaninUrl;
	
	private JwtStore store;
	private RestTemplate restTemplate;
	
	private AuthenticationService authenticationService;
	private ZahtevService zahtevService;
	private GradjaninService gradjaninService;
	private MailingService mailingService;
	
	@Autowired
	public SertifikatService(JwtStore store, RestTemplate restTemplate, AuthenticationService authenticationService, ZahtevService zahtevService, GradjaninService gradjaninService, MailingService mailingService) {
		this.store = store;
		this.restTemplate = restTemplate;
		this.authenticationService = authenticationService;
		this.zahtevService = zahtevService;
		this.gradjaninService = gradjaninService;
		this.mailingService = mailingService;
	}
	
	public Object readPlain(String id) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/sertifikati/query/%s", gradjaninUrl, id), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}

	public Object readTransformed(String id, String type) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/sertifikati/query/%s?type=%s", gradjaninUrl, id, type), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}
	
	public DocumentLinksResult readLinks(String id) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DocumentLinksResult> response = restTemplate.exchange(String.format("%s/sertifikati/query/%s/links", gradjaninUrl, id), HttpMethod.GET, httpEntity, DocumentLinksResult.class);

        return response.getBody();
	}
	
	public Object extractRdf(String id, String type) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/sertifikati/query/%s/rdf?type=%s", gradjaninUrl, id, type), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}
	
	public DigitalniSertifikat create(DigitalniSertifikat digitalniSertifikat) throws IOException {	
		validate(digitalniSertifikat);
		
		HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(digitalniSertifikat, store.getJwt());
		restTemplate.exchange(String.format("%s/sertifikati", gradjaninUrl), HttpMethod.POST, httpEntity, Object.class);
		
		return digitalniSertifikat;
	}
	
	public DeclineZahtev decline(DeclineZahtev declineZahtev) throws IOException {
		String zahtevId = declineZahtev.getZahtev();
		ZahtevZaIzdavanjeZelenogSertifikata zahtev = validateZahtev(zahtevId);
				
		String sluzbenikId = authenticationService.getCurrentWorkerId();
		
		updateZahtev(zahtevId, zahtev, false, sluzbenikId);
		
		sendDeclineEmail(zahtev, declineZahtev.getRazlog());
		
		return declineZahtev;
	}
	
	private void sendDeclineEmail(ZahtevZaIzdavanjeZelenogSertifikata zahtev, String razlogOdbijanja) {
		String podnosilacId = zahtev.getPodnosilacZahteva().getJmbg();
		Tgradjanin gradjanin = gradjaninService.read(podnosilacId);
		
		MailContent mailContent = new MailContent();
		mailContent.setTo(gradjanin.getEmail());
		mailContent.setSubject("Odbijen zahtev za digitalni sertifikat");
		
		String body = String.format("Po??tovani,\nZahtev je odbijen iz razloga:\n%s", razlogOdbijanja);
		mailContent.setText(body);
		
		mailingService.Send(mailContent);
	}
	
	public int count(LocalDate startDate, LocalDate endDate) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        ResponseEntity<CountResponse> response = restTemplate.exchange(String.format("%s/sertifikati/count?startDate=%s&endDate=%s", gradjaninUrl, startDate, endDate), HttpMethod.GET, httpEntity, CountResponse.class);

        return response.getBody().getValue();
    }
	
	private void validate(DigitalniSertifikat digitalniSertifikat) throws IOException {
		String zahtevId = digitalniSertifikat.getZahtev();
		ZahtevZaIzdavanjeZelenogSertifikata zahtev = validateZahtev(zahtevId);
		
		if (!zahtev.getPodnosilacZahteva().getJmbg().equals(digitalniSertifikat.getGradjaninId())) {
			throw new MissingEntityException("Zahtev nije podnet za tra??enog gra??anina.");
		}
		
		String sluzbenikId = authenticationService.getCurrentWorkerId();
		
		updateZahtev(zahtevId, zahtev, true, sluzbenikId);
	}
	
	private ZahtevZaIzdavanjeZelenogSertifikata validateZahtev(String zahtevId) {
		String zahtevName = zahtevId.replace("/", "_");
		ZahtevZaIzdavanjeZelenogSertifikata zahtev = zahtevService.read(zahtevName);	
		if (zahtev == null) {
			throw new MissingEntityException("Zahtev ne postoji.");
		}
		
		Optional<Tmeta> obradjenMeta = zahtev.getMeta().stream()
				.filter(meta -> meta.getProperty().equals("rdfzzizs:obradjen"))
				.findFirst();
		if (obradjenMeta.isPresent()) {
			throw new BadLogicException("Zahtev je ve?? obra??en.");
		}
		
		return zahtev;
	}
	
	private void updateZahtev(String id, ZahtevZaIzdavanjeZelenogSertifikata zahtev, boolean prihvacen, String sluzbenikId) throws IOException {
		String zahtevName = id.replace("/", "_");
		updateLink(zahtev, sluzbenikId);
		updateMeta(zahtev, prihvacen);
		
		zahtevService.saveUpdatedZahtev(zahtevName, zahtev);
	}
	
	private void updateLink(ZahtevZaIzdavanjeZelenogSertifikata zahtev, String sluzbenikId) {
		zahtev.getLink().add(TlinkFactory.create("rdfzzizs:obradio", String.format("%s/sluzbenici/%s", Constants.ROOT_URL, sluzbenikId), "rdfos:Sluzbenik"));
	}
	
	private void updateMeta(ZahtevZaIzdavanjeZelenogSertifikata zahtev, boolean prihvacen) {		
		zahtev.getMeta().add(TmetaFactory.create("rdfzzizs:obradjen", "xsd:date", LocalDate.now().toString()));
		zahtev.getMeta().add(TmetaFactory.create("rdfzzizs:prihvacen", "xsd:boolean", Boolean.toString(prihvacen)));
	}

}
