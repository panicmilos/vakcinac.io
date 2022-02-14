package vakcinac.io.core.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.core.Constants;
import vakcinac.io.core.annotations.RegisterXmlScheme;
import vakcinac.io.core.utils.AnnotatedClassScanner;


public class TargetNamespaceRegistry {
	private HashMap<String, String> registry;
	
	public TargetNamespaceRegistry() {
		registry = new HashMap<String, String>();
		findAllSchemes();
	}
	
	private void findAllSchemes() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterXmlScheme.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterXmlScheme xmlSchemeAnnotation = xmlClass.getAnnotation(RegisterXmlScheme.class);

			registry.put(xmlClass.getCanonicalName(), xmlSchemeAnnotation.targetNamespace());
		}
	}
	
	public String getTargetNamespaceFor(Class<?> forClass) {
		return registry.get(forClass.getCanonicalName());
	}

}
