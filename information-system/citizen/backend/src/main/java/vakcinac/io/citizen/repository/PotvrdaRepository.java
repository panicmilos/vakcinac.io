package vakcinac.io.citizen.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.core.repository.ExistRepository;

@Repository
@RequestScope
public class PotvrdaRepository extends ExistRepository<PotvrdaOIzvrsenojVakcinaciji> {

	public PotvrdaRepository() throws IOException, XMLDBException {
		super(PotvrdaOIzvrsenojVakcinaciji.class);
	}

}