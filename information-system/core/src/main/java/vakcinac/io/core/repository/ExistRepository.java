package vakcinac.io.core.repository;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import vakcinac.io.core.repository.exist.CloseableResource;
import vakcinac.io.core.repository.exist.XUpdateTemplate;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;
import vakcinac.io.core.utils.registries.ExistEntitiesRegistry;
import vakcinac.io.core.utils.registries.TargetNamespaceRegistry;
import vakcinac.io.core.Constants;

@SuppressWarnings("unused")
public abstract class ExistRepository<T> implements Closeable {
	protected Class<?> forClass;
	protected String collectionUri;
	protected CloseableCollection collection;

	public ExistRepository(Class<?> forClass) throws IOException, XMLDBException {
		this.forClass = forClass;

		ExistEntitiesRegistry collectionUriRegistry = new ExistEntitiesRegistry();
		collectionUri = collectionUriRegistry.getCollectionUriFor(forClass);
		
		collection = new CloseableCollection(collectionUri);
		collection.setProperty(OutputKeys.INDENT, "yes");
	}

	public T store(String id, T obj) {
		try (CloseableResource resource = new CloseableResource(collection.createResource(id + ".xml", XMLResource.RESOURCE_TYPE))) {

			JaxBParser parser = JaxBParserFactory.newInstanceFor(forClass);
			String serializedObj = parser.marshall(obj);

			resource.setContent(serializedObj);
			collection.storeResource(resource.getRealResource());
			
			return obj;
		} catch (XMLDBException e) {
			return null;
		}
	}
	
	public T store(String id, String serializedObj) {
		try (CloseableResource resource = new CloseableResource(collection.createResource(id + ".xml", XMLResource.RESOURCE_TYPE))) {

			resource.setContent(serializedObj);
			collection.storeResource(resource.getRealResource());
			
			return retrieve(id);
		} catch (XMLDBException e) {
			return null;
		}
	}

	public T retrieve(String id) {
		try (CloseableResource resource = new CloseableResource(collection.getResource(id + ".xml"))) {

			if (resource.getRealResource() == null) {
				System.out.format("[ERROR] Document with id: %s can not be found!\n", id);
				return null;
			}

			JaxBParser parser = JaxBParserFactory.newInstanceFor(forClass);
			return parser.unmarshall(resource.getContent().toString());

		} catch (XMLDBException e) {
		}

		return null;

	}
	
	public boolean contains(String id, String xPathExpression) throws XMLDBException {
		String xQueryExpression = String.format("doc(\"/%s/%s\")%s", collectionUri, id + ".xml", xPathExpression);

		ResourceIterator iterator = executeRetrieveUsingXQuery(xQueryExpression);
        while(iterator.hasMoreResources()) {
    		return true;
		}
        
        return false;
	}
	
	public int count(String id) throws XMLDBException, IOException {
		ResourceIterator iterator = retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/count.xqy", collectionUri, id);
		
		try (CloseableResource resource = new CloseableResource(iterator.nextResource())) {
			String content = resource.getContent().toString();

			return Integer.parseInt(content);
		}
	}
	
	public ResourceIterator retrieveUsingXQuery(String xQueryExpression) throws XMLDBException {
		return executeRetrieveUsingXQuery(xQueryExpression);
	}
	
	public Resource retrieveFirstUsingXQuery(String xQueryExpression) throws XMLDBException {
		ResourceIterator iterator = executeRetrieveUsingXQuery(xQueryExpression);
		
		return findFirst(iterator);
	}
	
	public ResourceIterator retrieveUsingXQuery(String filePath, Object ...args) throws XMLDBException, IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		String notFormattedXQuery = new String(encoded, StandardCharsets.UTF_8);
		
		String formattedXQuery = String.format(notFormattedXQuery, args);

		return executeRetrieveUsingXQuery(formattedXQuery);
	}
	
	private Resource findFirst(ResourceIterator iterator) throws XMLDBException {
		if (!iterator.hasMoreResources()) {
			return null;
		}
		
		return iterator.nextResource();
	}
	
	private ResourceIterator executeRetrieveUsingXQuery(String xQueryExpression) throws XMLDBException {
		TargetNamespaceRegistry registry = new TargetNamespaceRegistry();
		
		XQueryService xqueryService = collection.getXQueryService(registry.getTargetNamespaceFor(forClass));
        CompiledExpression compiledXquery = xqueryService.compile(xQueryExpression);
        ResourceSet result = xqueryService.execute(compiledXquery);

        return result.getIterator();		
	}
	
	public void insertBefore(String id, String contextPath, String serializedObj) throws XMLDBException {
		TargetNamespaceRegistry registry = new TargetNamespaceRegistry();
		
		XUpdateQueryService xqueryService = collection.getXUpdateQueryService();
		String nonFormattedInsert = XUpdateTemplate.getInsertBefore(registry.getTargetNamespaceFor(forClass));
		String formattedInsert = String.format(nonFormattedInsert, contextPath, serializedObj);
		
		xqueryService.updateResource(id + ".xml", formattedInsert);
	}
	
	public void insertAfter(String id, String contextPath, String serializedObj) throws XMLDBException {
		TargetNamespaceRegistry registry = new TargetNamespaceRegistry();
		
		XUpdateQueryService xqueryService = collection.getXUpdateQueryService();
		String nonFormattedInsert = XUpdateTemplate.getInsertAfter(registry.getTargetNamespaceFor(forClass));
		String formattedInsert = String.format(nonFormattedInsert, contextPath, serializedObj);
		
		xqueryService.updateResource(id + ".xml", formattedInsert);
	}
	
	public void append(String id, String contextPath, String serializedObj) throws XMLDBException {
		TargetNamespaceRegistry registry = new TargetNamespaceRegistry();
		
		XUpdateQueryService xqueryService = collection.getXUpdateQueryService();
		String nonFormattedAppend = XUpdateTemplate.getAppend(registry.getTargetNamespaceFor(forClass));
		String formattedAppend = String.format(nonFormattedAppend, contextPath, serializedObj);

		xqueryService.updateResource(id + ".xml", formattedAppend);
	}
	
	public void update(String id, String contextPath, Object obj) throws XMLDBException {
		TargetNamespaceRegistry registry = new TargetNamespaceRegistry();
		
		XUpdateQueryService xqueryService = collection.getXUpdateQueryService();
		String nonFormattetUpdate = XUpdateTemplate.getUpdate(registry.getTargetNamespaceFor(forClass));
		String formattedUpdate = String.format(nonFormattetUpdate, contextPath, obj.toString());
		
		xqueryService.updateResource(id + ".xml", formattedUpdate);
	}
	
	public T remove(String id) {
		try (CloseableResource resource = new CloseableResource(collection.getResource(id + ".xml"))) {

			if (resource.getRealResource() == null) {
				System.out.format("[ERROR] Document with id: %s can not be found!\n", id);
				return null;
			}

			JaxBParser parser = JaxBParserFactory.newInstanceFor(forClass);
			T obj = parser.unmarshall(resource.getContent().toString());

			collection.removeResource(resource.getRealResource());
			return obj;

		} catch (XMLDBException e) {
		}

		return null;
	}
	
	public void remove(String id, String contextPath) throws XMLDBException {
		TargetNamespaceRegistry registry = new TargetNamespaceRegistry();
		
		XUpdateQueryService xqueryService = collection.getXUpdateQueryService();
		String nonFormattetRemove = XUpdateTemplate.getRemove(registry.getTargetNamespaceFor(forClass));
		String formattedRemove = String.format(nonFormattetRemove, contextPath);
		
		xqueryService.updateResource(id + ".xml", formattedRemove);
	}

	@Override
	public void close() throws IOException {
		collection.close();
	}

}
