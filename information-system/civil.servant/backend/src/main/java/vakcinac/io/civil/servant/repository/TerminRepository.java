package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
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
	
	public int findTermin(int numOfZR, String date) throws XMLDBException, IOException {
		ResourceIterator iterator = retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/find_termin.xqy", numOfZR, date);
		
		try (CloseableResource resource = new CloseableResource(iterator.nextResource())) {
			String content = resource.getContent().toString();

			return Integer.parseInt(content);
		}
	}


}
