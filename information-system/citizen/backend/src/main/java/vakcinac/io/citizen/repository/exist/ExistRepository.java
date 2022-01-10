package vakcinac.io.citizen.repository.exist;

import java.io.Closeable;
import java.io.IOException;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import vakcinac.io.citizen.utils.parsers.JaxBParser;
import vakcinac.io.citizen.utils.parsers.JaxBParserFactory;
import vakcinac.io.citizen.utils.registries.ExistEntitiesRegistry;

@SuppressWarnings("unused")
public abstract class ExistRepository<T> implements Closeable {
	private CloseableCollection collection;
	private Class<?> forClass;
	
	public ExistRepository(Class<?> forClass) throws IOException, XMLDBException {
		this.forClass = forClass;

		ExistEntitiesRegistry collectionUriRegistry = new ExistEntitiesRegistry();
		String collectionUri = collectionUriRegistry.getCollectionUriFor(forClass);
		
		collection = new CloseableCollection(collectionUri);
		collection.setProperty(OutputKeys.INDENT, "yes");
	}

	public void store(String id, T obj) {
		try (CloseableResource resource = new CloseableResource(collection.createResource(id, XMLResource.RESOURCE_TYPE))) {

			JaxBParser parser = JaxBParserFactory.newInstanceFor(forClass);
			String serializedObj = parser.marshall(obj);

			resource.setContent(serializedObj);
			collection.storeResource(resource.getRealResource());
		} catch (XMLDBException e) {
		}
	}

	public T retrieve(String id) {
		try (CloseableResource resource = new CloseableResource(collection.getResource(id))) {

			if (resource == null) {
				System.out.format("[ERROR] Document with id: %s can not be found!\n", id);
				return null;
			}

			JaxBParser parser = JaxBParserFactory.newInstanceFor(forClass);
			return parser.unmarshall(resource.getContent().toString());

		} catch (XMLDBException e) {
		}

		return null;

	}

	public T remove(String id) {
		try (CloseableResource resource = new CloseableResource(collection.getResource(id))) {

			if (resource == null) {
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

	@Override
	public void close() throws IOException {
		collection.close();
	}

}
