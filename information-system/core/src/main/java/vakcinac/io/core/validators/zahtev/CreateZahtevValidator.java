package vakcinac.io.core.validators.zahtev;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
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
	        .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
	        .withMessage("Podnosilac nije u dobrom formatu.")
	        .withFieldName("Podnosilac");
		
		ruleFor(CreateZahtevRequest::getRazlog)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Razlog je obavezan.")
	        .withFieldName("Razlog");

		ruleFor(CreateZahtevRequest::getMesto)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Mesto je obavezno.")
	        .withFieldName("Mesto");
	}

}
