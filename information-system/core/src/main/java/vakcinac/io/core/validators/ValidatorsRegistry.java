package vakcinac.io.core.validators;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.core.Constants;
import vakcinac.io.core.utils.AnnotatedClassScanner;

public class ValidatorsRegistry {
	
	private HashMap<Class<?>, Class<?>> registry;
	
	public ValidatorsRegistry() {
		registry = new HashMap<>();
		populateRegistry();
	}
	
	private void populateRegistry() {
		List<Class<?>> validatorClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisteredValidator.class);
		System.out.println(validatorClasses);
		for(Class<?> validatorClass : validatorClasses) {
			RegisteredValidator validatorAnnotation = validatorClass.getAnnotation(RegisteredValidator.class);

			registry.put(validatorAnnotation.forType(), validatorClass);
		}
		
	}
	
	public Class<?> forObjectType(Class<?> type) {		
		return registry.get(type);
	}

}
