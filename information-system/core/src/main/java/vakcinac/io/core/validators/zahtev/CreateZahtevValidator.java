package vakcinac.io.core.validators.zahtev;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.CreateZahtevRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = CreateZahtevRequest.class)
public class CreateZahtevValidator extends AbstractValidator<CreateZahtevRequest> {
	
	@Override
	public void rules() {
		
		ruleFor(CreateZahtevRequest::getPodnosilac)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Podnosilac je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
	        .withMessage("Podnosilac nije u dobrom formatu.")
	        .withFieldName("Podnosilac");
		
		ruleFor(CreateZahtevRequest::getRazlog)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Razlog je obavezan.")
	        .withFieldName("Razlog");

		ruleFor(CreateZahtevRequest::getMesto)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Mesto je obavezno.")
	        .withFieldName("Mesto");
	}

}
