package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class ZahtevService {
	
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;
	
	private JwtStore store;
	private RestTemplate restTemplate;
	
	@Autowired
	public ZahtevService(JwtStore store, RestTemplate restTemplate) {
		this.store = store;
		this.restTemplate = restTemplate;
	}

	public ZahtevZaIzdavanjeZelenogSertifikata create(ZahtevZaIzdavanjeZelenogSertifikata zahtev) {		
		HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(zahtev, store.getJwt());
		restTemplate.exchange(String.format("%s/zahtevi", sluzbenikUrl), HttpMethod.POST, httpEntity, Object.class);
		
		return zahtev;
	}
}
