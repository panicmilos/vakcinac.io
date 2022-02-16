package vakcinac.io.core.repository.jena;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import vakcinac.io.core.Constants;
import vakcinac.io.core.utils.JenaAuthenticationUtils;
import vakcinac.io.core.utils.JenaAuthenticationUtils.JenaConnectionProperties;
import vakcinac.io.core.utils.SparqlUtils;

public class JenaRepository implements Closeable {
	private CloseableHttpClient client;
	private JenaConnectionProperties connectionProperties;

	public JenaRepository() throws IOException {
		client = HttpClientFactory.newClient();
		connectionProperties = JenaAuthenticationUtils.loadProperties();
	}

	public void insert(String xml, String graphUri) throws IOException {
		try (RdfObject rdfObj = RdfObject.from(xml)) {

			String sparqlUpdate = SparqlUtils.insertData(connectionProperties.dataEndpoint + graphUri, rdfObj.toString(SparqlUtils.NTRIPLES));
	
			UpdateRequest update = UpdateFactory.create(sparqlUpdate);
	
			UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, connectionProperties.updateEndpoint, client);
			processor.execute();
		}
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
	
	public CloseableResultSet read(String graphUri, String filePath, Object ...args) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		String notFormattedSparqlCondition = new String(encoded, StandardCharsets.UTF_8);
		
		String formattedSparqlCondition = String.format(notFormattedSparqlCondition, connectionProperties.dataEndpoint + graphUri, args[0], args[1]);

		QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, formattedSparqlCondition);
		ResultSet resultSet = query.execSelect();
		
		return new CloseableResultSet(resultSet, query);
	}
	
	public RdfObject construct(String graphUri, String filePath, Object ...args) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		String notFormattedSparqlCondition = new String(encoded, StandardCharsets.UTF_8);
		
		String formattedSparqlCondition = String.format(notFormattedSparqlCondition, connectionProperties.dataEndpoint + graphUri, args[0]);

		QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, formattedSparqlCondition);
		Model model = query.execConstruct();
				
		return new RdfObject(model);
	}
	

	public String readLatestSubject(String graph, String p, String o) {
		try(CloseableResultSet set = read(graph, String.format("?s %s %s", p, o))) {
			String subject = "";
			while (set.hasNext()) {
				QuerySolution querySolution = set.next();
				subject = querySolution.get("s").toString();
			}
			return subject;
		}
	}
	
	public int count(String graphUri, Object ...args) throws IOException {
		
		try(CloseableResultSet set = read(graphUri, Constants.ROOT_RESOURCE + "/data/sparql/count.sparql", args)) {
			while (set.hasNext()) {
				QuerySolution querySolution = set.next();
				
				return querySolution.get("count").asLiteral().getInt();
			}
		}
		
		return 0;
	}
	
	public void updateData(String about, String xml, String graphUri) {
		deleteData(about, graphUri);
		insert(xml, graphUri);
	}
	
	public void deleteData(String about, String graphUri) {
		String sparqlUpdate = SparqlUtils.deleteData(connectionProperties.dataEndpoint + graphUri, about);
		UpdateRequest dropRequest = UpdateFactory.create(sparqlUpdate);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(dropRequest, connectionProperties.updateEndpoint, client);
		processor.execute();
	}
	
	public void dropGraph(String graphUri) {
		String sparqlUpdate = SparqlUtils.dropGraph(connectionProperties.dataEndpoint + graphUri);
		UpdateRequest dropRequest = UpdateFactory.create(sparqlUpdate);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(dropRequest, connectionProperties.updateEndpoint, client);
		processor.execute();
	}
	
	public void dropAll() {
		UpdateRequest request = UpdateFactory.create(SparqlUtils.dropAll());

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, connectionProperties.updateEndpoint, client);
		processor.execute();
	}

	@Override
	public void close() throws IOException {
		client.close();
	}

}
