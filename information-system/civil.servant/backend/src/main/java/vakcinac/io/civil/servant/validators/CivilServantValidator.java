package vakcinac.io.civil.servant.validators;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.core.validators.ObjectValidator;
import vakcinac.io.core.validators.ValidatorsRegistry;

@Component
@RequestScope
public class CivilServantValidator extends ObjectValidator {
	
	public CivilServantValidator() {
		super();
		this.registry = new ValidatorsRegistry();
	}

}
