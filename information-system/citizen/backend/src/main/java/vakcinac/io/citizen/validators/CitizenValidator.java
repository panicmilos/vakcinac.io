package vakcinac.io.citizen.validators;

import org.springframework.stereotype.Service;

import vakcinac.io.core.validators.ObjectValidator;
import vakcinac.io.core.validators.ValidatorsRegistry;

@Service
public class CitizenValidator extends ObjectValidator {
	
	public CitizenValidator() {
		super();
		this.registry = new ValidatorsRegistry();
	}

}
