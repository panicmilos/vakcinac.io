package vakcinac.io.citizen.validators;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.core.validators.ObjectValidator;
import vakcinac.io.core.validators.ValidatorsRegistry;

@Component
@RequestScope
public class CitizenValidator extends ObjectValidator {
	
	public CitizenValidator() {
		super();
		this.registry = new ValidatorsRegistry();
	}

}
