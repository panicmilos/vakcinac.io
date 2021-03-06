package vakcinac.io.core.repository;

import java.io.Closeable;
import java.io.IOException;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import vakcinac.io.core.utils.ExistAuthenticationUtils;
import vakcinac.io.core.utils.ExistAuthenticationUtils.ExistConnectionProperties;

public class CloseableCollection implements Collection, Closeable {
	private ExistConnectionProperties connectionProperties;
	private Collection connection;

	public CloseableCollection(String collectionUrl) throws IOException, XMLDBException {
		connectionProperties = ExistAuthenticationUtils.loadProperties();

		this.connection = getOrCreateCollection(collectionUrl);
	}

	@Override
	public String getProperty(String name) throws XMLDBException {
		return connection.getProperty(name);
	}

	@Override
	public void setProperty(String name, String value) throws XMLDBException {
		connection.setProperty(name, value);
	}

	@Override
	public String getName() throws XMLDBException {
		return connection.getName();
	}

	@Override
	public Service[] getServices() throws XMLDBException {
		return connection.getServices();
	}

	@Override
	public Service getService(String name, String version) throws XMLDBException {
		return connection.getService(name, version);
	}

	@Override
	public Collection getParentCollection() throws XMLDBException {
		return connection.getParentCollection();
	}

	@Override
	public int getChildCollectionCount() throws XMLDBException {
		return connection.getChildCollectionCount();
	}

	@Override
	public String[] listChildCollections() throws XMLDBException {
		return connection.listChildCollections();
	}

	@Override
	public Collection getChildCollection(String name) throws XMLDBException {
		return connection.getChildCollection(name);
	}

	@Override
	public int getResourceCount() throws XMLDBException {
		return connection.getResourceCount();
	}

	@Override
	public String[] listResources() throws XMLDBException {
		return connection.listResources();
	}

	@Override
	public Resource createResource(String id, String type) throws XMLDBException {
		return connection.createResource(id, type);
	}

	@Override
	public void removeResource(Resource res) throws XMLDBException {
		connection.removeResource(res);
	}

	@Override
	public void storeResource(Resource res) throws XMLDBException {
		connection.storeResource(res);
	}

	@Override
	public Resource getResource(String id) throws XMLDBException {
		return connection.getResource(id);
	}

	@Override
	public String createId() throws XMLDBException {
		return connection.createId();
	}

	@Override
	public boolean isOpen() throws XMLDBException {
		return connection.isOpen();
	}

	@Override
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (XMLDBException xe) {
				xe.printStackTrace();
			}
		}

	}
	
	public XQueryService getXQueryService(String targetNamespace) throws XMLDBException {
        XQueryService xqueryService = (XQueryService) getService("XQueryService", "1.0");
        xqueryService.setProperty("indent", "yes");
        xqueryService.setNamespace("b", targetNamespace);
        
        return xqueryService;
	}
	
	public XUpdateQueryService getXUpdateQueryService() throws XMLDBException {
        XUpdateQueryService xupdateService = (XUpdateQueryService) getService("XUpdateQueryService", "1.0");
        xupdateService.setProperty("indent", "yes");
        
        return xupdateService;
	}


	private Collection getOrCreateCollection(String collectionUri) throws XMLDBException, IOException {
		return getOrCreateCollection(collectionUri, 0);
	}

	private Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset)
			throws XMLDBException, IOException {
		Collection col = DatabaseManager.getCollection(connectionProperties.uri + collectionUri,
				connectionProperties.user, connectionProperties.password);

		// create the collection if it does not exist
		if (col == null) {

			if (collectionUri.startsWith("/")) {
				collectionUri = collectionUri.substring(1);
			}

			String pathSegments[] = collectionUri.split("/");

			if (pathSegments.length > 0) {
				StringBuilder path = new StringBuilder();

				for (int i = 0; i <= pathSegmentOffset; i++) {
					path.append("/" + pathSegments[i]);
				}

				Collection startCol = DatabaseManager.getCollection(connectionProperties.uri + path,
						connectionProperties.user, connectionProperties.password);

				if (startCol == null) {

					// child collection does not exist

					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parentCol = DatabaseManager.getCollection(connectionProperties.uri + parentPath,
							connectionProperties.user, connectionProperties.password);

					CollectionManagementService mgt = (CollectionManagementService) parentCol
							.getService("CollectionManagementService", "1.0");

					System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
					col = mgt.createCollection(pathSegments[pathSegmentOffset]);

					col.close();
					parentCol.close();

				} else {
					startCol.close();
				}
			}
			return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
		} else {
			return col;
		}
	}

}
