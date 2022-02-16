package vakcinac.io.civil.servant.repository.jena;

import org.springframework.stereotype.Repository;
import vakcinac.io.core.repository.jena.JenaRepository;

import java.io.IOException;

@Repository
public class CivilServantJenaRepository extends JenaRepository {

	public CivilServantJenaRepository() throws IOException {
		super();
	}
}
