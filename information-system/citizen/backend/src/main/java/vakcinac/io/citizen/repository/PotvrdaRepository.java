package vakcinac.io.citizen.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.core.Constants;
import vakcinac.io.core.repository.ExistRepository;

@Repository
@RequestScope
public class PotvrdaRepository extends ExistRepository<PotvrdaOIzvrsenojVakcinaciji> {

	public PotvrdaRepository() throws IOException, XMLDBException {
		super(PotvrdaOIzvrsenojVakcinaciji.class);
	}

	public ResourceIterator search(String query) throws XMLDBException, IOException {
		return retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/pot-ser.xqy", query);
	}

}
