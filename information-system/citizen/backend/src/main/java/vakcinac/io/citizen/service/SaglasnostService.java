package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.core.exceptions.BadLogicException;

@Service
public class SaglasnostService {

    @Value("${sluzbenik.url}")
    private String sluzbenikUrl;

    public SaglasnostZaSprovodjenjePreporuceneImunizacije create(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.postForEntity(String.format("%s/saglasnosti", sluzbenikUrl), saglasnost, Object.class);

		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BadLogicException("Trenutno nije moguće poslati saglasnost za vakcinisanje.");
		}

        return saglasnost;
    }

}
