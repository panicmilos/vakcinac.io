package vakcinac.io.core.repository.jena;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import vakcinac.io.core.utils.JenaAuthenticationUtils;
import vakcinac.io.core.utils.JenaAuthenticationUtils.JenaConnectionProperties;
import vakcinac.io.core.utils.SparqlUtils;

import java.io.Closeable;
import java.io.IOException;

public class JenaRepository implements Closeable {
	private BasicHttpContext context;
	private CloseableHttpClient client;
	private JenaConnectionProperties connectionProperties;

	public JenaRepository() throws IOException {
		context = HttpContextFactory.newContext();
		client = HttpClientFactory.newClient();
		connectionProperties = JenaAuthenticationUtils.loadProperties();
	}

	public void insert(String xml, String graphUri) {
		RdfObject rdfObj = RdfObject.from(xml);

		String sparqlUpdate = SparqlUtils.insertData(connectionProperties.dataEndpoint + graphUri, rdfObj.toString(SparqlUtils.NTRIPLES));

		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, connectionProperties.updateEndpoint, client, context);
		processor.execute();
	}
	
	public CloseableResultSet read(String graphUri) {
		return read(graphUri, "?s ?p ?o");
	}
	
	public CloseableResultSet read(String graphUri, String sparqlCondition) {
		String sparqlQuery = SparqlUtils.selectData(connectionProperties.dataEndpoint + graphUri, sparqlCondition);
		QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, sparqlQuery);
		ResultSet resultSet = query.execSelect();
		
		return new CloseableResultSet(resultSet, query);
	}
	
	public void updateData(String about, String xml, String graphUri) {
		deleteData(about, graphUri);
		insert(xml, graphUri);
	}
	
	public void deleteData(String about, String graphUri) {
		String sparqlUpdate = SparqlUtils.deleteData(connectionProperties.dataEndpoint + graphUri, about);
		UpdateRequest dropRequest = UpdateFactory.create(sparqlUpdate);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(dropRequest, connectionProperties.updateEndpoint, client, context);
		processor.execute();
	}
	
	public void dropGraph(String graphUri) {
		String sparqlUpdate = SparqlUtils.dropGraph(connectionProperties.dataEndpoint + graphUri);
		UpdateRequest dropRequest = UpdateFactory.create(sparqlUpdate);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(dropRequest, connectionProperties.updateEndpoint, client, context);
		processor.execute();
	}
	
	public void dropAll() {
		UpdateRequest request = UpdateFactory.create(SparqlUtils.dropAll());

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, connectionProperties.updateEndpoint, client, context);
		processor.execute();
	}

	@Override
	public void close() throws IOException {
		client.close();
	}

}
