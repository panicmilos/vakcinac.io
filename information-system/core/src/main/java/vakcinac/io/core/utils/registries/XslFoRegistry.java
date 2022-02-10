package vakcinac.io.core.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.core.Constants;
import vakcinac.io.core.annotations.RegisterXslFo;
import vakcinac.io.core.utils.AnnotatedClassScanner;

public class XslFoRegistry {
private HashMap<String, String> registry;
	
	public XslFoRegistry() {
		registry = new HashMap<String, String>();
		findAllXslFo();
	}
	
	private void findAllXslFo() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterXslFo.class);
		for(Class<?> xmlClass : xmlClasses) {
			RegisterXslFo xslFoAnnotation = xmlClass.getAnnotation(RegisterXslFo.class);

			registry.put(xmlClass.getCanonicalName(), xslFoAnnotation.xslPath());
		}
	}
	
	public String getPathFor(Class<?> forClass) {

		return registry.get(forClass.getCanonicalName());
	}
}
