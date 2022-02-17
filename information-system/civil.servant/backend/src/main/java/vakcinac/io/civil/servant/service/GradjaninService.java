package vakcinac.io.civil.servant.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.civil.servant.security.JwtStore;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.results.doc.CitizenDocumentsResult;
import vakcinac.io.core.utils.HttpUtils;

@Service
public class GradjaninService {

	@Value("${gradjanin.url}")
	private String gradjaninUrl;

	private JenaRepository jenaRepository;
	
	private JwtStore jwtStore;
	private RestTemplate restTemplate;

	@Autowired
	public GradjaninService(JenaRepository jenaRepository, JwtStore jwtStore, RestTemplate restTemplate) {
		this.jenaRepository = jenaRepository;
		this.jwtStore = jwtStore;
		this.restTemplate = restTemplate;
	}

	public Tgradjanin read(String id) {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
		ResponseEntity<Tgradjanin> response = restTemplate.exchange(String.format("%s/gradjani/%s", gradjaninUrl, id), HttpMethod.GET, httpEntity, Tgradjanin.class);
		
		return response.getBody();
	}
	
	public CitizenDocumentsResult getDocumentsFor(String jmbg) throws IOException {
		return jenaRepository.findDocumentsFor(jmbg);
	}
	
	public CitizenDocumentsResult getAllDocumentsFor(String jmbg) throws IOException {
		CitizenDocumentsResult remoteDocuments = getAllRemoteDocumentsFor(jmbg);
		CitizenDocumentsResult localDocuments = getDocumentsFor(jmbg);
		
		localDocuments.getCitizenDocument().addAll(remoteDocuments.getCitizenDocument());
		
		return localDocuments;
	}
	
	private CitizenDocumentsResult getAllRemoteDocumentsFor(String jmbg) throws IOException {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
		ResponseEntity<CitizenDocumentsResult> response = restTemplate.exchange(String.format("%s/gradjani/%s/documents", gradjaninUrl, jmbg), HttpMethod.GET, httpEntity, CitizenDocumentsResult.class);
		
		return response.getBody();
	}
}
