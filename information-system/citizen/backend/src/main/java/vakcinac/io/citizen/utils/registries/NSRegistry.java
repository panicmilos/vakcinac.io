package vakcinac.io.citizen.utils.registries;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.annotations.RegisterXmlScheme;
import vakcinac.io.citizen.utils.AnnotatedClassScanner;


@XmlTransient
public class NSRegistry extends NamespacePrefixMapper {
	private HashMap<String, String> registry;
	
	public NSRegistry() { 
		registry = new HashMap<String, String>(); 
		findAllNamespaces();
	}
	
	private void findAllNamespaces() {
		List<Class<?>> xmlClasses = AnnotatedClassScanner.find(Constants.ROOT_PACKAGE, RegisterXmlScheme.class);

		for(Class<?> xmlClass : xmlClasses) {
			RegisterXmlScheme xmlSchemeAnnotation = xmlClass.getAnnotation(RegisterXmlScheme.class);

			registry.put(xmlSchemeAnnotation.targetNamespace(), xmlSchemeAnnotation.shortNamespace());
		}
	}
		
	public String getPreferredPrefix(String namespaceURI, String suggestion, boolean requirePrefix) { 
		return registry.getOrDefault(namespaceURI, suggestion); 
	}

}
