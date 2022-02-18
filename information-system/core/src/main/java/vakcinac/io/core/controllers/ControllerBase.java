package vakcinac.io.core.controllers;

import org.modelmapper.ModelMapper;

import br.com.fluentvalidator.context.ValidationResult;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.validators.ObjectValidator;

public abstract class ControllerBase {
	
	protected ModelMapper mapper;
	protected ObjectValidator validator;
	
	protected ControllerBase(ModelMapper mapper, ObjectValidator validator) {
		this.mapper = mapper;
		this.validator = validator;
	}
	
	protected void validate(Object validatableObject) {
		ValidationResult validateResult = validator.validate(validatableObject);
		for (br.com.fluentvalidator.context.Error a : validateResult.getErrors()) {
			System.out.println(a.getMessage());
		}
		if (!validateResult.isValid()) {
			throw new BadLogicException(
					"Jedan ili više uslova nije ispunjeno za: " + validatableObject.getClass().getSimpleName());
		}
	}
	
	protected Object map(Object source, Class<?> targetType) {
		return mapper.map(source, targetType);
	}
}
