package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.core.repository.ExistRepository;


@Repository
public class VakcinaRepository extends ExistRepository<Vakcina> {

	public VakcinaRepository() throws IOException, XMLDBException {
		super(Vakcina.class);
	}

}
