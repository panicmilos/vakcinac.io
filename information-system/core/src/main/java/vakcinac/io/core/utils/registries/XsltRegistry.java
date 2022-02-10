package vakcinac.io.core.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.core.Constants;
import vakcinac.io.core.annotations.RegisterXslt;
import vakcinac.io.core.utils.AnnotatedClassScanner;

public class XsltRegistry {
	private HashMap<String, String> registry;
	
	public XsltRegistry() {
		registry = new HashMap<String, String>();
		findAllXslt();
	}
	
	private void findAllXslt() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterXslt.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterXslt xsltAnnotation = xmlClass.getAnnotation(RegisterXslt.class);

			registry.put(xmlClass.getCanonicalName(), xsltAnnotation.xslPath());
		}
	}
	
	public String getPathFor(Class<?> forClass) {
		return registry.get(forClass.getCanonicalName());
	}
}
