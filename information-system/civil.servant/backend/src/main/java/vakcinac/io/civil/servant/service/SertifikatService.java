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
import vakcinac.io.core.models.os.Tmeta;
import vakcinac.io.core.responses.CountResponse;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class SertifikatService {
	
	@Value("${gradjanin.url}")
	private String gradjaninUrl;
	
	private JwtStore store;
	
	private AuthenticationService authenticationService;
	private ZahtevService zahtevService;
	
	@Autowired
	public SertifikatService(JwtStore store, AuthenticationService authenticationService, ZahtevService zahtevService) {
		this.store = store;
		this.authenticationService = authenticationService;
		this.zahtevService = zahtevService;
	}
	
	public DigitalniSertifikat create(DigitalniSertifikat digitalniSertifikat) throws IOException {	
		validate(digitalniSertifikat);
		
		HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(digitalniSertifikat, store.getJwt());
				
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(String.format("%s/sertifikati", gradjaninUrl), HttpMethod.POST, httpEntity, Object.class);
		
		return digitalniSertifikat;
	}
	
	public DeclineZahtev decline(DeclineZahtev declineZahtev) throws IOException {
		String zahtevId = declineZahtev.getZahtev();
		ZahtevZaIzdavanjeZelenogSertifikata zahtev = validateZahtev(zahtevId);
				
		String sluzbenikId = authenticationService.getCurrentWorkerId();
		
		updateZahtev(zahtevId, zahtev, false, sluzbenikId);
		
		return declineZahtev;
	}
	
	public int count(LocalDate startDate, LocalDate endDate) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CountResponse> response = restTemplate.getForEntity(String.format("%s/sertifikati/count?startDate=%s&endDate=%s", gradjaninUrl, startDate, endDate), CountResponse.class);

        return response.getBody().getValue();
    }
	
	private void validate(DigitalniSertifikat digitalniSertifikat) throws IOException {
		String zahtevId = digitalniSertifikat.getZahtev();
		ZahtevZaIzdavanjeZelenogSertifikata zahtev = validateZahtev(zahtevId);
		
		if (!zahtev.getPodnosilacZahteva().getJmbg().equals(digitalniSertifikat.getGradjaninId())) {
			throw new MissingEntityException("Zahtev nije podnet za traženog građanina.");
		}
		
		String sluzbenikId = authenticationService.getCurrentWorkerId();
		digitalniSertifikat.setSluzbenikId(sluzbenikId);
		
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
			throw new BadLogicException("Zahtev je već obrađen.");
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
