package vakcinac.io.civil.servant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.civil.servant.security.JwtStore;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class GradjaninService {

	@Value("${gradjanin.url}")
	private String gradjaninUrl;

	private JwtStore jwtStore;

	@Autowired
	public GradjaninService(JwtStore jwtStore) {
		this.jwtStore = jwtStore;
	}

	public Tgradjanin read(String id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Tgradjanin> response = restTemplate.getForEntity(String.format("%s/gradjani/%s", gradjaninUrl, id), Tgradjanin.class);
		
		if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new MissingEntityException("Građanin sa željenim id ne postoji.");
		}
		
		return response.getBody();
	}
}
