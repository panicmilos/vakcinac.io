package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.core.repository.ExistRepository;

@Repository
public class ZdravstveniRadnikRepository extends ExistRepository<ZdravstveniRadnik> {
	
	public ZdravstveniRadnikRepository() throws IOException, XMLDBException {
		super(ZdravstveniRadnik.class);
	}

}