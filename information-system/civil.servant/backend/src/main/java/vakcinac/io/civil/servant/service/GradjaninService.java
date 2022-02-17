package vakcinac.io.civil.servant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.civil.servant.security.JwtStore;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class GradjaninService {

	@Value("${gradjanin.url}")
	private String gradjaninUrl;

	private JwtStore jwtStore;
	private RestTemplate restTemplate;

	@Autowired
	public GradjaninService(JwtStore jwtStore, RestTemplate restTemplate) {
		this.jwtStore = jwtStore;
		this.restTemplate = restTemplate;
	}

	public Tgradjanin read(String id) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
		ResponseEntity<Tgradjanin> response = restTemplate.exchange(String.format("%s/gradjani/%s", gradjaninUrl, id), HttpMethod.GET, httpEntity, Tgradjanin.class);
		
		return response.getBody();
	}
}
