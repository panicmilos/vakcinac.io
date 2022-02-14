package vakcinac.io.citizen.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.core.repository.ExistRepository;

@Repository
@RequestScope
public class DomaciGradjaninRepository extends ExistRepository<DomaciGradjanin> {
	
	public DomaciGradjaninRepository() throws IOException, XMLDBException {
		super(DomaciGradjanin.class);
	}

}
