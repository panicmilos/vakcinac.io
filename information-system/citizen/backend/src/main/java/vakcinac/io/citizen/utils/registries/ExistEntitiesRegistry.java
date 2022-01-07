package vakcinac.io.citizen.utils.registries;

import java.util.HashMap;
import java.util.List;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.annotations.RegisterExistEntity;
import vakcinac.io.citizen.utils.AnnotatedClassScanner;

public class ExistEntitiesRegistry {
	private HashMap<Class<?>, String> registry;
	
	public ExistEntitiesRegistry() {
		registry = new HashMap<Class<?>, String>(); 
		findAllExistEntities();
	}
	
	private void findAllExistEntities() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterExistEntity.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterExistEntity registerEntityAnnotation = xmlClass.getAnnotation(RegisterExistEntity.class);

			registry.put(xmlClass, registerEntityAnnotation.collectionUri());
		}
	}
	
	public String getCollectionUriFor(Class<?> forClass) {
		return registry.get(forClass);
	}

}
