package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;

@Service
public class SaglasnostService {

    @Value("${sluzbenik.url}")
    private String sluzbenikUrl;

    public SaglasnostZaSprovodjenjePreporuceneImunizacije create(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(String.format("%s/saglasnosti", sluzbenikUrl), saglasnost, Object.class);

        return saglasnost;
    }

}
