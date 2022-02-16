package vakcinac.io.civil.servant.validators.vakcina;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import vakcinac.io.civil.servant.requests.AddVakcinaStockRequest;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = AddVakcinaStockRequest.class)
public class AddVakcinaStockRequestValidator extends AbstractValidator<AddVakcinaStockRequest> {

	@Override
	public void rules() {
		
        ruleFor(AddVakcinaStockRequest::getAmount)
			.must(ComparablePredicate.greaterThan(0))
			.withMessage("Količina nije validana.")
			.withFieldName("Količina");
		
	}

}
