package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.izv.IzvestajOImunizaciji;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.QueryDocumentFactory;
import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.results.doc.QueryDocumentsResult;
import vakcinac.io.core.results.doc.QueryDocumentsResult.Document;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Repository
@RequestScope
public class IzvestajRepository extends ExistRepository<IzvestajOImunizaciji> {

	public IzvestajRepository() throws IOException, XMLDBException {
		super(IzvestajOImunizaciji.class);
	}

	public QueryDocumentsResult search(String query) throws XMLDBException, IOException {
		ResourceIterator iterator = retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/izv-ser.xqy", query);
		
		if (!iterator.hasMoreResources()) {
			return new QueryDocumentsResult();
		}
		
		String serializedDocuments = iterator.nextResource().getContent().toString();
		
		JaxBParser parser = JaxBParserFactory.newInstanceFor(QueryDocumentsResult.class);
		QueryDocumentsResult result = parser.unmarshall(serializedDocuments);
		for (Document document : result.getDocument()) {
			QueryDocumentFactory.create(document);
		}
		
		return result;
	}

}
