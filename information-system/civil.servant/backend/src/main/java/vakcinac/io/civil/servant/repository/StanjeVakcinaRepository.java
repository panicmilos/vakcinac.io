package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.stanj.StanjeVakcina;
import vakcinac.io.core.repository.ExistRepository;

@Repository
public class StanjeVakcinaRepository extends ExistRepository<StanjeVakcina> {

	public StanjeVakcinaRepository() throws IOException, XMLDBException {
		super(StanjeVakcina.class);
	}

}
