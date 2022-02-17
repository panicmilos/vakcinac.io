package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.core.Constants;
import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.results.doc.QueryDocumentsResult;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Repository
@RequestScope
public class SaglasnostRepository extends ExistRepository<SaglasnostZaSprovodjenjePreporuceneImunizacije> {

	public SaglasnostRepository() throws IOException, XMLDBException {
		super(SaglasnostZaSprovodjenjePreporuceneImunizacije.class);
	}

	public QueryDocumentsResult search(String query) throws XMLDBException, IOException {
		ResourceIterator iterator = retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/sag-ser.xqy", query);
		
		if (!iterator.hasMoreResources()) {
			return new QueryDocumentsResult();
		}
		
		String serializedDocuments = iterator.nextResource().getContent().toString();
		
		JaxBParser parser = JaxBParserFactory.newInstanceFor(QueryDocumentsResult.class);
		return parser.unmarshall(serializedDocuments);
	}

}
