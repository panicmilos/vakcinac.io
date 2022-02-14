package vakcinac.io.citizen.service;

import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;


public abstract class BaseService<T> {
	
	protected ExistRepository<T> baseRepository;
	
	protected BaseService(ExistRepository<T> baseRepository) {
		this.baseRepository = baseRepository;
	}
	
	public T read(String id) {
		return baseRepository.retrieve(id);
	}
	
	public T create(String id, T obj) {
		return baseRepository.store(id, obj);
	}
	
	public T findFirstByXQuery(String XQueryExpression, Class<?> targetType) {
		try {
			Resource resource = baseRepository.retrieveFirstUsingXQuery(XQueryExpression);
			if (resource == null) {
				return null;
			}
			
			JaxBParser parser = JaxBParserFactory.newInstanceFor(targetType);
			Object deserializedObj = parser.unmarshall(resource.getContent().toString());
			
			return (T) deserializedObj;
			
		} catch (XMLDBException e) {
			return null;
		}
	}

}
