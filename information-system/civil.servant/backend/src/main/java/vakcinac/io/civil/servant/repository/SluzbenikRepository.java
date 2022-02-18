package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.sluz.Sluzbenik;
import vakcinac.io.core.repository.ExistRepository;

@Repository
public class SluzbenikRepository extends ExistRepository<Sluzbenik> {
	
	public SluzbenikRepository() throws IOException, XMLDBException {
		super(Sluzbenik.class);
	}
}
