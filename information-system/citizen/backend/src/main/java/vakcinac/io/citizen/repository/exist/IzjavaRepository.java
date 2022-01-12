package vakcinac.io.citizen.repository.exist;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.izj.IzjavaInteresovanjaZaVakcinisanje;

@Repository
@RequestScope
public class IzjavaRepository extends ExistRepository<IzjavaInteresovanjaZaVakcinisanje> {

	public IzjavaRepository() throws IOException, XMLDBException {
		super(IzjavaInteresovanjaZaVakcinisanje.class);
	}

}
