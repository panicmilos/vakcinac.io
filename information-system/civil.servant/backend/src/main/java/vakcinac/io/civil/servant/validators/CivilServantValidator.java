package vakcinac.io.civil.servant.validators;

import org.springframework.stereotype.Service;

import vakcinac.io.core.validators.ObjectValidator;
import vakcinac.io.core.validators.ValidatorsRegistry;

@Service
public class CivilServantValidator extends ObjectValidator {
	
	public CivilServantValidator() {
		super();
		this.registry = new ValidatorsRegistry();
	}

}
