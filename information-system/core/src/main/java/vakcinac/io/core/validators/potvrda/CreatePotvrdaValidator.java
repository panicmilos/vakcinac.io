package vakcinac.io.core.validators.potvrda;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.CreatePotvrdaRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;

public class CreatePotvrdaValidator extends AbstractValidator<CreatePotvrdaRequest> {

    @Override
    public void rules() {

        ruleFor(CreatePotvrdaRequest::getJmbgOsobe)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Jmbg je obavezan.")
                .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
                .withMessage("Jmbg nije u dobrom formatu.")
                .withFieldName("Jmbg");

        ruleFor(CreatePotvrdaRequest::getNazivVakcine)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Naziv vakcine je obavezan.")
                .withFieldName("NazivVakcine");

        ruleFor(CreatePotvrdaRequest::getSerija)
                .must(StringUtils::notNullOrEmpty)
                .withMessage("Serija je obavezana.")
                .must(StringPredicate.stringMatches(RegexPatterns.SERIJA_PATTERN))
                .withMessage("Serija nije u dobrom formatu.")
                .withFieldName("Serija");
    }

}
