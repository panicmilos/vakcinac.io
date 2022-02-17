package vakcinac.io.core.validators.saglasnost;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.CreateSaglasnostRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;

public class CreateSaglasnostValidator extends AbstractValidator<CreateSaglasnostRequest> {

    @Override
    public void rules() {

        ruleFor(CreateSaglasnostRequest::getBrojMobilnog)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Broj mobilnog telefona je obavezan.")
                .must(StringPredicate.stringMatches(RegexPatterns.BR_MOB_PATTERN))
                .withMessage("Broj mobilnog telefona nije u dobrom formatu.")
                .withFieldName("BrojMobilnog");

        ruleFor(CreateSaglasnostRequest::getRadniStatus)
                .must(ComparablePredicate.betweenInclusive(0, 5))
                .withMessage("Radni status je obavezan.")
                .withFieldName("RadniStatus");

        ruleFor(CreateSaglasnostRequest::getZanimanje)
                .must(ComparablePredicate.betweenInclusive(0, 5))
                .withMessage("Zanimanje je obavezno.")
                .withFieldName("Zanimanje");

        ruleFor(CreateSaglasnostRequest::getNazivImunoloskogLeka)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Naziv imunoloskog leka.")
                .withFieldName("Naziv imunoloskog leka je obavezan");

        ruleFor(CreateSaglasnostRequest::getNazivOpstinaSedista)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Naziv opstine sedista je obavezan.")
                .withFieldName("Naziv opstine sedista.");
    }

}
