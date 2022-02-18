package vakcinac.io.core.services;

import java.io.IOException;

import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.factories.DocumentLinksResultFactory;
import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.results.link.DocumentLinksResult;
import vakcinac.io.core.results.link.Links;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;
import vakcinac.io.core.utils.transformers.PDFTransformer;
import vakcinac.io.core.utils.transformers.XHTMLTransformer;

public abstract class BaseService<T> {
	
	protected ExistRepository<T> baseRepository;
	protected JenaRepository jenaRepository;
	
	protected BaseService(ExistRepository<T> baseRepository, JenaRepository jenaRepository) {
		this.baseRepository = baseRepository;
		this.jenaRepository = jenaRepository;
	}
	
	public T read(String id) {
		return baseRepository.retrieve(id);
	}
	
	public T read(String additionalCollectionUri, String id) throws IOException, XMLDBException {
		return baseRepository.retrieve(additionalCollectionUri, id);
	}
	
	public T readPlain(String id) {
		T obj = read(id);
		
		if (obj == null) {
			throw new MissingEntityException("Zadati dokument ne postoji.");
		}
		
		return obj;
	}
	
	public byte[] readTransformed(String id, String type) {
		PDFTransformer pdfTransformer = new PDFTransformer();
		XHTMLTransformer xhtmlTransformer = new XHTMLTransformer();
		
		Object obj = readPlain(id);
		if (type.equals("pdf")) {
			return pdfTransformer.generate(obj);
		} else {
			return xhtmlTransformer.generate(obj);
		}
	}
	
	public DocumentLinksResult readLinks(String id) throws Exception {
		Links referencing = findReferencing(id);
		Links referencedBy = findReferencedBy(id);
		
		return DocumentLinksResultFactory.create(referencing, referencedBy);
	}
	
	protected Links findReferencing(String id) throws Exception { return null; }
	protected Links findReferencedBy(String id) throws Exception { return null; }

	public int count(String graphUri, Object ...args) throws IOException {
		return jenaRepository.count(graphUri, args);
	}
		
	public abstract T create(T obj) throws Exception;
	
	protected T create(String id, T obj) {
		return baseRepository.store(id, obj);
	}
	
	protected T create(String additionalCollectionUri, String id, T obj) throws IOException, XMLDBException {
		return baseRepository.store(additionalCollectionUri, id, obj);
	}
	
	protected T create(String id, String serializedObj) {
		return baseRepository.store(id, serializedObj);
	}
	
	public T delete(String id) {
		return baseRepository.remove(id);
	}
	
	@SuppressWarnings("unchecked")
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
