package vakcinac.io.civil.servant.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.term.Termin;
import vakcinac.io.core.Constants;
import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.repository.exist.CloseableResource;

@Repository
public class TerminRepository extends ExistRepository<Termin> {

	public TerminRepository() throws IOException, XMLDBException {
		super(Termin.class);
	}
	
	public List<String> findNotHeldTerminiForDate(String maxDateTime, String currentDate) throws XMLDBException, IOException {
		List<String> termini = new ArrayList<>();
		ResourceIterator iterator = retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/find_non_held_termini_for_date.xqy", maxDateTime, currentDate);
		
		while(iterator.hasMoreResources()) {
			Resource resource = iterator.nextResource();
			
			String terminDocument = resource.getContent().toString();
			termini.add(terminDocument.split("\\.")[0]);
		}
		
		return termini;
	}
	
	public int findTermin(int numOfZR, String date) throws XMLDBException, IOException {
		ResourceIterator iterator = retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/find_termin.xqy", numOfZR, date);
		
		try (CloseableResource resource = new CloseableResource(iterator.nextResource())) {
			String content = resource.getContent().toString();

			return Integer.parseInt(content);
		}
	}


}
