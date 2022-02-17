package vakcinac.io.civil.servant.validators.vakcina;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.civil.servant.requests.CreateVakcinaRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = CreateVakcinaRequest.class)
public class CreateVakcinaRequestValidator extends AbstractValidator<CreateVakcinaRequest> {

	@Override
	public void rules() {
		
        ruleFor(CreateVakcinaRequest::getProizvodjac)
	        .must(ComparablePredicate.betweenInclusive(0, 5))
	        .withMessage("Proizvodjac nije validan.")
	        .withFieldName("Proizvodjac");
        
        ruleFor(CreateVakcinaRequest::getSerija)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Serija je obavezana.")
	        .must(StringPredicate.stringMatches(RegexPatterns.SERIJA_PATTERN))
	        .withMessage("Serija nije u dobrom formatu.")
	        .withFieldName("Serija");
	}

}
