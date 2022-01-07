package vakcinac.io.citizen.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.annotations.RegisterXslt;
import vakcinac.io.citizen.utils.AnnotatedClassScanner;

public class XsltRegistry {
	private HashMap<Class<?>, String> registry;
	
	public XsltRegistry() {
		registry = new HashMap<Class<?>, String>(); 
		findAllXslt();
	}
	
	private void findAllXslt() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterXslt.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterXslt xsltAnnotation = xmlClass.getAnnotation(RegisterXslt.class);

			registry.put(xmlClass, xsltAnnotation.xslPath());
		}
	}
	
	public String getPathFor(Class<?> forClass) {
		return registry.get(forClass);
	}
}
