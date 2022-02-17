package vakcinac.io.civil.servant.validators.sertifikat;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.civil.servant.requests.DeclineZahtevRequest;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = DeclineZahtevRequest.class)
public class DeclineZahtevRequestValidator extends AbstractValidator<DeclineZahtevRequest> {
	
	@Override
    public void rules() {
		ruleFor(DeclineZahtevRequest::getZahtev)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Zahtev je obavezan.")
	        .withFieldName("Zahtev");

		ruleFor(DeclineZahtevRequest::getRazlog)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Razlog je obavezan.")
	        .withFieldName("Razlog");	
	}
}
