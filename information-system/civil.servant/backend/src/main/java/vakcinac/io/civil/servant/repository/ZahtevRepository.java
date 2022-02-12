package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.core.repository.ExistRepository;

@Repository
@RequestScope
public class ZahtevRepository extends ExistRepository<ZahtevZaIzdavanjeZelenogSertifikata> {

	public ZahtevRepository() throws IOException, XMLDBException {
		super(ZahtevZaIzdavanjeZelenogSertifikata.class);
	}

}