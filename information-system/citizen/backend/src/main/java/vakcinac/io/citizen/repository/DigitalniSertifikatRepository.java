package vakcinac.io.citizen.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.core.Constants;
import vakcinac.io.core.repository.ExistRepository;

import java.io.IOException;

@Repository
@RequestScope
public class DigitalniSertifikatRepository extends ExistRepository<DigitalniSertifikat> {

	public DigitalniSertifikatRepository() throws IOException, XMLDBException {
		super(DigitalniSertifikat.class);
	}

	public ResourceIterator search(String query) throws XMLDBException, IOException {
		return retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/dig-ser.xqy", query);
	}

}
