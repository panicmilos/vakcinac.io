package vakcinac.io.core.validators;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.context.ValidationResult;

public class ObjectValidator {
	protected ValidatorsRegistry registry;

	public ObjectValidator() {}
	
	public ValidationResult validate(Object validableObject) {
		Class<?> validatorType = registry.forObjectType(validableObject.getClass());
		
		try {
			@SuppressWarnings({ "deprecation", "unchecked" })
			AbstractValidator<Object> validator = (AbstractValidator<Object>)validatorType.newInstance();
			
			return validator.validate(validableObject);

		} catch (Exception e) {
			System.err.println("Got exception in ObjectValidator: " + e.getMessage());
		}
		
		return ValidationResult.ok();
	}

}
