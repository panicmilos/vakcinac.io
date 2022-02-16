package vakcinac.io.core.validators.potvrda;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.AddDozaRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;

public class AddDozaValidator extends AbstractValidator<AddDozaRequest> {

    @Override
    public void rules() {
        ruleFor(AddDozaRequest::getPotvrdaId)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Potvrda je obavezna.")
                .must(StringPredicate.stringMatches(RegexPatterns.POTVRDA_ID_PATTERN))
                .withMessage("Potvrda nije u dobrom formatu.")
                .withFieldName("Potvrda");


        ruleFor(AddDozaRequest::getSerija)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Serija je obavezana.")
                .must(StringPredicate.stringMatches(RegexPatterns.SERIJA_PATTERN))
                .withMessage("Serija nije u dobrom formatu.")
                .withFieldName("Serija");
    }

}
