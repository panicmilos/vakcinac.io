package vakcinac.io.civil.servant.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.repository.IzjavaRepository;
import vakcinac.io.civil.servant.repository.IzvestajRepository;
import vakcinac.io.civil.servant.repository.SaglasnostRepository;
import vakcinac.io.civil.servant.repository.ZahtevRepository;
import vakcinac.io.civil.servant.security.JwtStore;
import vakcinac.io.core.results.doc.QueryDocumentsResult;
import vakcinac.io.core.utils.HttpUtils;

@Service
@RequestScope
public class ExistSearchService {
	
	@Value("${gradjanin.url}")
	private String gradjaninUrl;
	
	private IzjavaRepository izjavaRepository;
	private IzvestajRepository izvestajRepository;
	private SaglasnostRepository saglasnostRepository;
	private ZahtevRepository zahtevRepository;
	
	private JwtStore jwtStore;
	private RestTemplate restTemplate;

	@Autowired
	public ExistSearchService(
		IzjavaRepository izjavaRepository,
		IzvestajRepository izvestajRepository,
		SaglasnostRepository saglasnostRepository,
		ZahtevRepository zahtevRepository,
		JwtStore jwtStore,
		RestTemplate restTemplate
	) {
		this.izjavaRepository = izjavaRepository;
		this.izvestajRepository = izvestajRepository;
		this.saglasnostRepository = saglasnostRepository;
		this.zahtevRepository = zahtevRepository;
		
		this.jwtStore = jwtStore;
		this.restTemplate = restTemplate;
	}

	public QueryDocumentsResult search(String query) throws XMLDBException, IOException {
		QueryDocumentsResult izjave = izjavaRepository.search(query);
		QueryDocumentsResult izvestaji = izvestajRepository.search(query);
		QueryDocumentsResult saglasnosti = saglasnostRepository.search(query);
		QueryDocumentsResult zahtevi = zahtevRepository.search(query);
		QueryDocumentsResult remote = searchRemote(query);

		QueryDocumentsResult result = new QueryDocumentsResult();
		result.getDocument().addAll(izjave.getDocument());
		result.getDocument().addAll(izvestaji.getDocument());
		result.getDocument().addAll(saglasnosti.getDocument());
		result.getDocument().addAll(zahtevi.getDocument());
		result.getDocument().addAll(remote.getDocument());

		return result;
	}
	
	private QueryDocumentsResult searchRemote(String query)  {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
		ResponseEntity<QueryDocumentsResult> response = restTemplate.exchange(String.format("%s/documents/search?query=%s", gradjaninUrl, query), HttpMethod.GET, httpEntity, QueryDocumentsResult.class);
		
		return response.getBody();
	}
}
