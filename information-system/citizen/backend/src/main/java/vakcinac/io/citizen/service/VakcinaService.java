package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class VakcinaService {

    @Value("${sluzbenik.url}")
    private String sluzbenikUrl;

    public Object getVakcina(String serija) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> vakcina = restTemplate.getForEntity(String.format("%s/vakcine?serija=%s", sluzbenikUrl, serija), Object.class);

        return vakcina.getBody();
    }

}
