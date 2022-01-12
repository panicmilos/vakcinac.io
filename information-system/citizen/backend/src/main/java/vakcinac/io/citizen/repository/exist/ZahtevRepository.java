package vakcinac.io.citizen.repository.exist;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;

@Repository
@RequestScope
public class ZahtevRepository extends ExistRepository<ZahtevZaIzdavanjeZelenogSertifikata> {

	public ZahtevRepository() throws IOException, XMLDBException {
		super(ZahtevZaIzdavanjeZelenogSertifikata.class);
	}

}