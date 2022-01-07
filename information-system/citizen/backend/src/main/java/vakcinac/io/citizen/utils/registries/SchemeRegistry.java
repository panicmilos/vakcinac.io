package vakcinac.io.citizen.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.annotations.RegisterXmlScheme;
import vakcinac.io.citizen.utils.AnnotatedClassScanner;

public class SchemeRegistry {
	private HashMap<Class<?>, String> registry;
	
	public SchemeRegistry() {
		registry = new HashMap<Class<?>, String>(); 
		findAllSchemes();
	}
	
	private void findAllSchemes() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterXmlScheme.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterXmlScheme xmlSchemeAnnotation = xmlClass.getAnnotation(RegisterXmlScheme.class);

			registry.put(xmlClass, xmlSchemeAnnotation.schemePath());
		}
	}
	
	public String getPathFor(Class<?> forClass) {
		return registry.get(forClass);
	}

}
