package vakcinac.io.citizen.repository.jena;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.core.repository.jena.JenaRepository;

import java.io.IOException;

@RequestScope
@Repository
public class CitizenJenaRepository extends JenaRepository {

	public CitizenJenaRepository() throws IOException {
		super();
	}
}
