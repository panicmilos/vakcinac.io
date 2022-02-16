package vakcinac.io.civil.servant.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.factories.StanjeVakcineFactory;
import vakcinac.io.civil.servant.models.stanj.StanjeVakcina;
import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.repository.StanjeVakcinaRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
public class StanjeVakcinaService extends BaseService<StanjeVakcina> {

	private VakcinaService vakcinaService;
	
	public StanjeVakcinaService(VakcinaService vakcinaService, StanjeVakcinaRepository stanjeVakcinaRepository, CivilServantJenaRepository jenaRepository) {
		super(stanjeVakcinaRepository, jenaRepository);
		this.vakcinaService = vakcinaService;
	}

	@Override
	public StanjeVakcina create(StanjeVakcina stanjeVakcina) {
		String id = "stanje-vakcina";
		
		StanjeVakcina existingStanjeVakcina = read(id);
		if (existingStanjeVakcina != null) {
			return existingStanjeVakcina;
		}
		
		return create(id, stanjeVakcina);
	}
	
	public StanjeVakcina.StanjeVakcine addToStockFor(String vaccineId, int amount) throws XMLDBException {
		Vakcina vakcina = vakcinaService.read(vaccineId);
		if (vakcina == null) {
			throw new MissingEntityException("Vakcina ne postoji.");
		}
		
		boolean vaccineHasStock = baseRepository.contains("stanje-vakcina", String.format("//*:vakcina[text()='%s']", vaccineId));
		if (!vaccineHasStock) {
			StanjeVakcina.StanjeVakcine stanjeVakcine = StanjeVakcineFactory.create(vaccineId, amount, 0);
			JaxBParser parser = JaxBParserFactory.newInstanceFor(StanjeVakcina.StanjeVakcine.class, Boolean.FALSE);
			String serializedObj = parser.marshall(stanjeVakcine);
			baseRepository.append("stanje-vakcina", "//stanje_vakcina", serializedObj);
			
			return stanjeVakcine;
		} else {
			return changeStockFor(vaccineId, amount);
		}
	}
	
	public StanjeVakcina.StanjeVakcine incStockFor(String vaccineId) throws XMLDBException {
		return changeStockFor(vaccineId, 1);
	}
	
	public StanjeVakcina.StanjeVakcine decStockFor(String vaccineId) throws XMLDBException {
		return changeStockFor(vaccineId, -1);
	}
	
	public StanjeVakcina.StanjeVakcine changeStockFor(String vaccineId, int amount) throws XMLDBException {
		StanjeVakcina stanjeVakcina = read("stanje-vakcina");
		StanjeVakcina.StanjeVakcine stanjeVakcine = stanjeVakcina.getStanjeVakcine().stream().filter(stanje -> stanje.getVakcina().equals(vaccineId)).findFirst().get();
		stanjeVakcine.setDostupno(stanjeVakcine.getDostupno().add(BigInteger.valueOf(amount)));
		baseRepository.update("stanje-vakcina", String.format("//vakcina[text()='%s']/../*:dostupno", vaccineId), stanjeVakcine.getDostupno());
		
		return stanjeVakcine;

	}


}
