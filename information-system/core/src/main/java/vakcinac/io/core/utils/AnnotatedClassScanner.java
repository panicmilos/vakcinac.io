package vakcinac.io.core.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class AnnotatedClassScanner {
	public static List<Class<?>> find(String scanPackage, Class<? extends Annotation> annotationClass) {
		ClassPathScanningCandidateComponentProvider provider = createComponentScanner(annotationClass);
        
    	List<Class<?>> classes = new ArrayList<Class<?>>();
    	
   	 	for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
	   	 	try {
	           	classes.add(Class.forName(beanDef.getBeanClassName()));
	        } catch (Exception e) {
	            System.err.println("Got exception: " + e.getMessage());
	        }
        }
       
        return classes;
    }
 
    private static ClassPathScanningCandidateComponentProvider createComponentScanner(Class<? extends Annotation> annotationClass) {
    	ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
        
        return provider;
    }

}
