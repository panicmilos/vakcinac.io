package vakcinac.io.civil.servant.validators.potvrda;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.civil.servant.requests.VaccinateRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = VaccinateRequest.class)
public class VaccinateRequestValidator extends AbstractValidator<VaccinateRequest> {

	@Override
	public void rules() {
		
        ruleFor(VaccinateRequest::getJmbgOsobe)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Jmbg je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
	        .withMessage("Jmbg nije u dobrom formatu.")
	        .withFieldName("Jmbg");
	}

}
