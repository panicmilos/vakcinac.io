package vakcinac.io.civil.servant.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.results.vak.VakcineResult;
import vakcinac.io.core.Constants;
import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;


@Repository
public class VakcinaRepository extends ExistRepository<Vakcina> {

	public VakcinaRepository() throws IOException, XMLDBException {
		super(Vakcina.class);
	}
	
	public VakcineResult findAll() throws XMLDBException, IOException {
		ResourceIterator iterator = retrieveUsingXQuery(Constants.ROOT_RESOURCE + String.format("/data/xquery/vak-ser.xqy"), "");
		
		VakcineResult result = new VakcineResult();
		JaxBParser parser = JaxBParserFactory.newInstanceFor(Vakcina.class);
		while (iterator.hasMoreResources()) {
			Vakcina vakcina = parser.unmarshall(iterator.nextResource().getContent().toString());
			result.getVakcina().add(vakcina);
		}
		
		return result;
	}
	
}
