package vakcinac.io.core.validators.potvrda;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.AddDozaRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;

public class AddDozaValidator extends AbstractValidator<AddDozaRequest> {

    @Override
    public void rules() {
        ruleFor(AddDozaRequest::getJmbg)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Id je obavezan.")
                .must(AddDozaValidator::isIdValid)
                .withMessage("Id nije u dobrom formatu.")
                .withFieldName("Id");


        ruleFor(AddDozaRequest::getSerija)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Serija je obavezana.")
                .must(StringPredicate.stringMatches(RegexPatterns.SERIJA_PATTERN))
                .withMessage("Serija nije u dobrom formatu.")
                .withFieldName("Serija");
    }
    
	private static boolean isIdValid(String podnosilac) {
		if (podnosilac.matches(RegexPatterns.JMBG_PATTERN) || podnosilac.matches(RegexPatterns.EBS_PATTERN) || 
				podnosilac.matches(RegexPatterns.PASOS_PATTERN)) {
			return true;
		}
		
		return false;
	}

}
