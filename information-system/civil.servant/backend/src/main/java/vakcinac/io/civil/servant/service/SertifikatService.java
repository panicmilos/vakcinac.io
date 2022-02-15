package vakcinac.io.civil.servant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.civil.servant.models.dig.DigitalniSertifikat;

@Service
public class SertifikatService {
	
	@Value("${gradjanin.url}")
	private String gradjaninUrl;
	
	private AuthenticationService authenticationService;
	
	@Autowired
	public SertifikatService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	public DigitalniSertifikat create(DigitalniSertifikat digitalniSertifikat) {
		String sluzbenikId = authenticationService.getCurrentWorkerId();
		digitalniSertifikat.setSluzbenikId(sluzbenikId);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(String.format("%s/sertifikati", gradjaninUrl), digitalniSertifikat, Object.class);
		
		return digitalniSertifikat;
	}

}
