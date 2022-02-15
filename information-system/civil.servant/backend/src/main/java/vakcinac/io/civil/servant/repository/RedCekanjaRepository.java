package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.red.RedCekanja;
import vakcinac.io.core.repository.ExistRepository;

@Repository
@RequestScope
public class RedCekanjaRepository extends ExistRepository<RedCekanja> {

	public RedCekanjaRepository() throws IOException, XMLDBException {
		super(RedCekanja.class);
	}

}
