package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.core.results.link.DocumentLinksResult;
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

	public Object readPlain(String id) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/zahtevi/query/%s", sluzbenikUrl, id), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}

	public Object readTransformed(String id, String type) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/zahtevi/query/%s?type=%s", sluzbenikUrl, id, type), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}
	
	public DocumentLinksResult readLinks(String id) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DocumentLinksResult> response = restTemplate.exchange(String.format("%s/zahtevi/query/%s/links", sluzbenikUrl, id), HttpMethod.GET, httpEntity, DocumentLinksResult.class);

        return response.getBody();
	}

	public Object extractRdf(String id, String type) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(store.getJwt());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s/zahtevi/query/%s/rdf?type=%s", sluzbenikUrl, id, type), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
	}
}
