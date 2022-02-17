package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.core.utils.HttpUtils;

@Service
@RequestScope
public class VakcinaService {

    @Value("${sluzbenik.url}")
    private String sluzbenikUrl;

    private JwtStore jwtStore;

    public VakcinaService(JwtStore jwtStore) {
        this.jwtStore = jwtStore;
    }

    public Object getVakcina(String serija) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(String.format("%s/vakcine?serija=%s", sluzbenikUrl, serija), HttpMethod.GET, httpEntity, Object.class);
        
        return response.getBody();
    }

}
