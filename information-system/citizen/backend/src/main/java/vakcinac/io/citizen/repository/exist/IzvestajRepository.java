package vakcinac.io.citizen.repository.exist;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.izv.IzvestajOImunizaciji;

@Repository
@RequestScope
public class IzvestajRepository extends ExistRepository<IzvestajOImunizaciji> {

	public IzvestajRepository() throws IOException, XMLDBException {
		super(IzvestajOImunizaciji.class);
	}

}
