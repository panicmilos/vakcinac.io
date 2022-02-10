package vakcinac.io.core.repository.exist;

import java.io.Closeable;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

public class CloseableResource implements Resource, Closeable {
	private Resource resource;

	public CloseableResource(Resource resource) {
		this.resource = resource;
	}

	public Resource getRealResource() {
		return resource;
	}

	@Override
	public int hashCode() {
		return resource.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return resource.equals(obj);
	}

	@Override
	public Collection getParentCollection() throws XMLDBException {
		return resource.getParentCollection();
	}

	@Override
	public String getId() throws XMLDBException {
		return resource.getId();
	}

	@Override
	public String getResourceType() throws XMLDBException {
		return resource.getResourceType();
	}

	@Override
	public Object getContent() throws XMLDBException {
		return resource.getContent();
	}

	@Override
	public void setContent(Object value) throws XMLDBException {
		resource.setContent(value);
	}

	@Override
	public void close() {
		if (resource != null) {
			try {
				((EXistResource) resource).freeResources();
			} catch (XMLDBException xe) {
				xe.printStackTrace();
			}
		}
	}

}
