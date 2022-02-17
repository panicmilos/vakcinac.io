package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class SaglasnostService {
    @Value("${sluzbenik.url}")
    private String sluzbenikUrl;
    
	private JwtStore store;
	private RestTemplate restTemplate;
	
	@Autowired
	public SaglasnostService(JwtStore store, RestTemplate restTemplate) {
		this.store = store;
		this.restTemplate = restTemplate;
	}

    public SaglasnostZaSprovodjenjePreporuceneImunizacije create(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(saglasnost, store.getJwt());
        restTemplate.exchange(String.format("%s/saglasnosti", sluzbenikUrl), HttpMethod.POST, httpEntity, Object.class);
        
        return saglasnost;
    }

}
