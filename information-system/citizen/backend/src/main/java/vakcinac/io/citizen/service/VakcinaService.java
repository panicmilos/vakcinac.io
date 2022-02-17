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
	private RestTemplate restTemplate;

    public VakcinaService(JwtStore jwtStore, RestTemplate restTemplate) {
        this.jwtStore = jwtStore;
		this.restTemplate = restTemplate;
    }

    public Object getVakcina(String serija) {
        HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
        ResponseEntity<Object> response = restTemplate.exchange(String.format("%s/vakcine?serija=%s", sluzbenikUrl, serija), HttpMethod.GET, httpEntity, Object.class);
        
        return response.getBody();
    }

}
