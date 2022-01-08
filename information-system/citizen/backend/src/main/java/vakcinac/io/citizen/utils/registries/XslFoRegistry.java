package vakcinac.io.citizen.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.annotations.RegisterXslFo;
import vakcinac.io.citizen.utils.AnnotatedClassScanner;

public class XslFoRegistry {
private HashMap<Class<?>, String> registry;
	
	public XslFoRegistry() {
		registry = new HashMap<Class<?>, String>(); 
		findAllXslFo();
	}
	
	private void findAllXslFo() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterXslFo.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterXslFo xslFoAnnotation = xmlClass.getAnnotation(RegisterXslFo.class);

			registry.put(xmlClass, xslFoAnnotation.xslPath());
		}
	}
	
	public String getPathFor(Class<?> forClass) {
		return registry.get(forClass);
	}
}
