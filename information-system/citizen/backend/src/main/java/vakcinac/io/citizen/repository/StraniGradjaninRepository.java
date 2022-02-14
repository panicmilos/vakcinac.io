package vakcinac.io.citizen.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.core.repository.ExistRepository;

@Repository
@RequestScope
public class StraniGradjaninRepository extends ExistRepository<StraniGradjanin> {
	
	public StraniGradjaninRepository() throws IOException, XMLDBException {
		super(StraniGradjanin.class);
	}

}
