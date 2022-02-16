package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.core.exceptions.BadLogicException;

@Service
public class IzjavaService {
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;

	public IzjavaInteresovanjaZaVakcinisanje create(IzjavaInteresovanjaZaVakcinisanje izjava) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.postForEntity(String.format("%s/izjave", sluzbenikUrl), izjava, Object.class);
	
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BadLogicException("Trenutno nije moguće iskazati interesovanje za vakcinacijom.");
		}
		
		return izjava;
	}

}
