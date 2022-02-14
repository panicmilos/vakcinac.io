package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.izv.IzvestajOImunizaciji;
import vakcinac.io.core.Constants;
import vakcinac.io.core.repository.ExistRepository;

@Repository
@RequestScope
public class IzvestajRepository extends ExistRepository<IzvestajOImunizaciji> {

	public IzvestajRepository() throws IOException, XMLDBException {
		super(IzvestajOImunizaciji.class);
	}

	public ResourceIterator search(String query) throws XMLDBException, IOException {
		return retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/izv-ser.xqy", query);
	}

}
