package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;
import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.core.Constants;
import vakcinac.io.core.repository.ExistRepository;


@Repository
@RequestScope
public class IzjavaRepository extends ExistRepository<IzjavaInteresovanjaZaVakcinisanje> {

	public IzjavaRepository() throws IOException, XMLDBException {
		super(IzjavaInteresovanjaZaVakcinisanje.class);
	}

	public ResourceIterator search(String query) throws XMLDBException, IOException {
		return retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/izj-ser.xqy", query);
	}
}
