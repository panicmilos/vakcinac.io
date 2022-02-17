package vakcinac.io.civil.servant.validators.sertifikat;

import br.com.fluentvalidator.AbstractValidator;
import vakcinac.io.civil.servant.requests.DeclineZahtevRequest;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = DeclineZahtevRequest.class)
public class DeclineZahtevRequestValidator extends AbstractValidator<DeclineZahtevRequest> {
	
	@Override
    public void rules() {
		ruleFor(DeclineZahtevRequest::getZahtev)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Zahtev je obavezan.")
	        .withFieldName("Zahtev");

		ruleFor(DeclineZahtevRequest::getRazlog)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Razlog je obavezan.")
	        .withFieldName("Razlog");	
	}
}
