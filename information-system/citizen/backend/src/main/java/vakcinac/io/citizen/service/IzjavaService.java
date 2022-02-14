package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.izj.IzjavaInteresovanjaZaVakcinisanje;

@Service
public class IzjavaService {
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;

	public IzjavaInteresovanjaZaVakcinisanje create(IzjavaInteresovanjaZaVakcinisanje izjava) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(String.format("%s/izjave", sluzbenikUrl), izjava, Object.class);
	
		return izjava;
	}

}
