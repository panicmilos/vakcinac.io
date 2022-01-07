package vakcinac.io.citizen.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.dig.DigitalniSertifikat;

@Repository
@RequestScope
public class DigitalniSertifikatiRepository extends ExistRepository<DigitalniSertifikat> {

	public DigitalniSertifikatiRepository() throws IOException, XMLDBException {
		super(DigitalniSertifikat.class);
	}

}
