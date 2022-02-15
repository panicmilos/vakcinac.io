package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.red.RedCekanja;
import vakcinac.io.civil.servant.models.term.Termin;
import vakcinac.io.civil.servant.repository.RedCekanjaRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class RedCekanjaService extends BaseService<RedCekanja> {

	private TerminService terminService;
	
	public RedCekanjaService(TerminService terminService, RedCekanjaRepository redCekanjaRepository, CivilServantJenaRepository jenaRepository) throws Exception {
		super(redCekanjaRepository, jenaRepository);
		
		this.terminService = terminService;
		
		create(new RedCekanja());
	}

	@Override
	public RedCekanja create(RedCekanja redCekanja) throws Exception {
		String id = "red-cekanja";
		
		RedCekanja existingRedCekanja = read(id);
		if (existingRedCekanja != null) {
			return existingRedCekanja;
		}
		
		return create(id, redCekanja);
	}
	
	public RedCekanja.GradjaninURedu add(RedCekanja.GradjaninURedu gradjaninURedu) throws XMLDBException {
		JaxBParser parser = JaxBParserFactory.newInstanceFor(RedCekanja.GradjaninURedu.class, Boolean.FALSE);
		String serializedObj = parser.marshall(gradjaninURedu);

		baseRepository.append("red-cekanja", "//*:red-cekanja", serializedObj);
		
		return gradjaninURedu;
	}
	
	public RedCekanja.GradjaninURedu remove(int index) throws XMLDBException {

		RedCekanja existingRedCekanja = read("red-cekanja");

		baseRepository.remove("red-cekanja", String.format("//*:red-cekanja/gradjanin-u-redu[%d]", index));
				
		return existingRedCekanja.getGradjaninURedu().get(index - 1);
	}
	
	public void tryToAssignTermins() throws Exception {
		RedCekanja existingRedCekanja = read("red-cekanja");

		int numberOfDeleted = 0;
		
		for (RedCekanja.GradjaninURedu gradjaninURedu : existingRedCekanja.getGradjaninURedu()) {
			Termin rightTermin = terminService.findAvaiableTermin(gradjaninURedu);
			
			if (rightTermin == null) {
				continue;
			}
			
			terminService.create(rightTermin);
			
			int index = existingRedCekanja.getGradjaninURedu().indexOf(gradjaninURedu);
			remove(index + 1 - numberOfDeleted);
			numberOfDeleted++;		
		}
	}
	
}
