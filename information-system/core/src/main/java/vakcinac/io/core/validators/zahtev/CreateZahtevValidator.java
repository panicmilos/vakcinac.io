package vakcinac.io.core.validators.zahtev;

import br.com.fluentvalidator.AbstractValidator;
import vakcinac.io.core.requests.CreateZahtevRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = CreateZahtevRequest.class)
public class CreateZahtevValidator extends AbstractValidator<CreateZahtevRequest> {
	
	@Override
	public void rules() {
		
		ruleFor(CreateZahtevRequest::getPodnosilac)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Podnosilac je obavezan.")
	        .must(CreateZahtevValidator::isPodnosilacValid)
	        .withMessage("Podnosilac nije u dobrom formatu.")
	        .withFieldName("Podnosilac");
		
		ruleFor(CreateZahtevRequest::getPasos)
	        .must(CreateZahtevValidator::isPasosValid)
	        .withMessage("Pasoš nije u dobrom formatu.")
	        .withFieldName("Pasos");
		
		ruleFor(CreateZahtevRequest::getRazlog)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Razlog je obavezan.")
	        .withFieldName("Razlog");

		ruleFor(CreateZahtevRequest::getMesto)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Mesto je obavezno.")
	        .withFieldName("Mesto");
	}
	
	private static boolean isPodnosilacValid(String podnosilac) {
		if (podnosilac.matches(RegexPatterns.JMBG_PATTERN) || podnosilac.matches(RegexPatterns.EBS_PATTERN) || 
				podnosilac.matches(RegexPatterns.PASOS_PATTERN)) {
			return true;
		}

		return false;
	}
	
	private static boolean isPasosValid(String pasos) {
		if (StringUtils.nullOrEmpty(pasos)) {
			return true;
		}
		
		if (!pasos.matches(RegexPatterns.PASOS_PATTERN)) {
			return false;
		}
		
		return true;
	}

}
