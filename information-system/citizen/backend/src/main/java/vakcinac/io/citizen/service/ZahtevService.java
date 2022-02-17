package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class ZahtevService {
	
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;
	
	private JwtStore store;
	
	@Autowired
	public ZahtevService(JwtStore store) {
		this.store = store;
	}

	public ZahtevZaIzdavanjeZelenogSertifikata create(ZahtevZaIzdavanjeZelenogSertifikata zahtev) {		
		HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(zahtev, store.getJwt());
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(String.format("%s/zahtevi", sluzbenikUrl), HttpMethod.POST, httpEntity, Object.class);
		
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BadLogicException("Trenutno nije moguće napraviti zahtev za izdavanje zelenog sertifikata.");
		}
	
		return zahtev;

	}
}
