package vakcinac.io.core.utils.registries;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import vakcinac.io.core.Constants;
import vakcinac.io.core.annotations.RegisterXmlScheme;
import vakcinac.io.core.utils.AnnotatedClassScanner;


@XmlTransient
public class NSRegistry extends NamespacePrefixMapper {
	private HashMap<String, String> registry;
	
	@SuppressWarnings("serial")
	public NSRegistry() { 
		registry = new HashMap<String, String>() {{
			put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
			put("http://java.sun.com/xml/ns/jaxb", "jaxb");
			put("https://www.vakcinac-io.rs/osnovna-sema", "os");
			put("https://www.vakcinac-io.rs/aggregate-result", "agres");
		}};
		
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
