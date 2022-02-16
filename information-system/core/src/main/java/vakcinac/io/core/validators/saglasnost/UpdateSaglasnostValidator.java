package vakcinac.io.core.validators.saglasnost;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.UpdateSaglasnostRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;

public class UpdateSaglasnostValidator extends AbstractValidator<UpdateSaglasnostRequest> {


    @Override
    public void rules() {
        ruleFor(UpdateSaglasnostRequest::getSaglasnostId)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Saglasnost je obavezna.")
                .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
                .withMessage("Saglasnost nije u dobrom formatu.")
                .withFieldName("Saglasnost");

        ruleFor(UpdateSaglasnostRequest::getJmbgLekara)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Jmbg lekara je obavezan.")
                .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
                .withMessage("Jmbg nije u dobrom formatu.")
                .withFieldName("Jmbg");

    }

}
