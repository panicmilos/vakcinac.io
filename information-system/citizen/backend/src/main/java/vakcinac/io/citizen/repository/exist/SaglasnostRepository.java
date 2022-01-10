package vakcinac.io.citizen.repository.exist;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;

@Repository
@RequestScope
public class SaglasnostRepository extends ExistRepository<SaglasnostZaSprovodjenjePreporuceneImunizacije> {

	public SaglasnostRepository() throws IOException, XMLDBException {
		super(SaglasnostZaSprovodjenjePreporuceneImunizacije.class);
	}

}
