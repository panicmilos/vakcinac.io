package vakcinac.io.civil.servant.service;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.red.RedCekanja;
import vakcinac.io.civil.servant.models.term.Termin;
import vakcinac.io.civil.servant.repository.RedCekanjaRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
public class RedCekanjaService extends BaseService<RedCekanja> {

	@Value("${gradjanin.url}")
	private String gradjaninUrl;
	
	private TerminService terminService;
	private RestTemplate restTemplate;
	
	public RedCekanjaService(TerminService terminService, RestTemplate restTemplate, RedCekanjaRepository redCekanjaRepository, CivilServantJenaRepository jenaRepository) throws Exception {
		super(redCekanjaRepository, jenaRepository);
		
		this.terminService = terminService;
		this.restTemplate = restTemplate;
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
	
	public boolean isInRow(String citizenId) throws XMLDBException {
		
		boolean isNationalCitizenInRow = baseRepository.contains("red-cekanja", String.format("//*:red-cekanja//*:jmbg[text() = '%s']", citizenId));
		boolean isForeignCitizenInRow = baseRepository.contains("red-cekanja", String.format("//*:red-cekanja//*:broj-pasosa-ebs[text() = '%s']", citizenId));

		return isNationalCitizenInRow || isForeignCitizenInRow;
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
			
			sendEmail(rightTermin);
			
			int index = existingRedCekanja.getGradjaninURedu().indexOf(gradjaninURedu);
			remove(index + 1 - numberOfDeleted);
			numberOfDeleted++;		
		}
	}
	
	private void sendEmail(Termin termin) {
		String id;
		if (StringUtils.notNullOrEmpty(termin.getJmbg())) {
			id = termin.getJmbg();
		}
		else {
			id = termin.getBrojPasosaEbs();
		}
			
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
		String terminDate = timeFormatter.format(termin.getVreme());
		
		restTemplate.postForEntity(String.format("%s/gradjani/dodeljen-termin/%s?termin=%s", gradjaninUrl, id, terminDate),  HttpEntity.EMPTY, String.class);
	}
	
}
