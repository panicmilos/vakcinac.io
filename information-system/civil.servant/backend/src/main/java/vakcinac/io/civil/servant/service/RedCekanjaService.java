package vakcinac.io.civil.servant.service;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.red.RedCekanja;
import vakcinac.io.civil.servant.models.term.Termin;
import vakcinac.io.civil.servant.repository.RedCekanjaRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.mail.MailContent;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
public class RedCekanjaService extends BaseService<RedCekanja> {

	private TerminService terminService;
	private GradjaninService gradjaninService;
	private MailingService mailingService;
	
	public RedCekanjaService(TerminService terminService, GradjaninService gradjaninService, MailingService mailingService, RedCekanjaRepository redCekanjaRepository, CivilServantJenaRepository jenaRepository) throws Exception {
		super(redCekanjaRepository, jenaRepository);
		
		this.terminService = terminService;
		this.gradjaninService = gradjaninService;
		this.mailingService = mailingService;
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
		
		Tgradjanin gradjanin = gradjaninService.read(id);
		if (gradjanin == null) {
			throw new MissingEntityException("Ne postoji građanin.");
		}
		
		MailContent mailContent = new MailContent();
		mailContent.setTo(gradjanin.getEmail());
		mailContent.setSubject("Termin za vakcinaciju");
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");  
		
		String body = String.format("Poštovani,\n Dodeljen Vam je termin za vakcinaciju datuma: %s", timeFormatter.format(termin.getVreme()));
		mailContent.setText(body);
		
		mailingService.Send(mailContent);
	}
	
}
