package vakcinac.io.core.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.core.Constants;
import vakcinac.io.core.annotations.RegisterExistEntity;
import vakcinac.io.core.utils.AnnotatedClassScanner;

public class ExistEntitiesRegistry {
	private HashMap<String, String> registry;
	
	public ExistEntitiesRegistry() {
		registry = new HashMap<String, String>();
		findAllExistEntities();
	}
	
	private void findAllExistEntities() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterExistEntity.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterExistEntity registerEntityAnnotation = xmlClass.getAnnotation(RegisterExistEntity.class);

			registry.put(xmlClass.getCanonicalName(), registerEntityAnnotation.collectionUri());
		}
	}
	
	public String getCollectionUriFor(Class<?> forClass) {
		return registry.get(forClass.getCanonicalName());
	}

}
