package vakcinac.io.citizen.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.repository.DigitalniSertifikatRepository;
import vakcinac.io.citizen.repository.PotvrdaRepository;
import vakcinac.io.core.results.doc.QueryDocumentsResult;

@Service
@RequestScope
public class ExistSearchService {
	
	private PotvrdaRepository potvrdaRepository;
	private DigitalniSertifikatRepository sertifikatiRepository;
	
	@Autowired
	public ExistSearchService(PotvrdaRepository potvrdaRepository, DigitalniSertifikatRepository sertifikatiRepository) {
		this.potvrdaRepository = potvrdaRepository;
		this.sertifikatiRepository = sertifikatiRepository;
	}

	public QueryDocumentsResult search(String query) throws XMLDBException, IOException {
		QueryDocumentsResult potvrde = potvrdaRepository.search(query);
		QueryDocumentsResult sertifikati = sertifikatiRepository.search(query);

		QueryDocumentsResult result = new QueryDocumentsResult();
		result.getDocument().addAll(potvrde.getDocument());
		result.getDocument().addAll(sertifikati.getDocument());
		
		return result;
	}

}
