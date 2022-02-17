package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class IzjavaService {
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;
	
	private JwtStore store;
	private RestTemplate restTemplate;
	
	@Autowired
	public IzjavaService(JwtStore store, RestTemplate restTemplate) {
		this.store = store;
		this.restTemplate = restTemplate;
	}

	public IzjavaInteresovanjaZaVakcinisanje create(IzjavaInteresovanjaZaVakcinisanje izjava) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeaderWithBody(izjava, store.getJwt());
		restTemplate.exchange(String.format("%s/izjave", sluzbenikUrl), HttpMethod.POST, httpEntity, Object.class);
	
		return izjava;
	}

}
