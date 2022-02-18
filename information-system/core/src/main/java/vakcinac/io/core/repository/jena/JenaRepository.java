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
import vakcinac.io.core.factories.CitizenDocumentFactory;
import vakcinac.io.core.factories.LinkFactory;
import vakcinac.io.core.results.doc.CitizenDocumentsResult;
import vakcinac.io.core.results.link.Links;
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

	public CloseableResultSet execQuery(String sparqlQuery) {
		QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, sparqlQuery);
		ResultSet resultSet = query.execSelect();

		return new CloseableResultSet(resultSet, query);
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
		
		String formattedSparqlCondition = "";
		if (args.length == 2) {
			formattedSparqlCondition = String.format(notFormattedSparqlCondition, connectionProperties.dataEndpoint + graphUri, args[0], args[1]);
		} else {
			formattedSparqlCondition = String.format(notFormattedSparqlCondition, connectionProperties.dataEndpoint + graphUri, args[0], args[1], args[2]);
		}

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
	

	public String readLatestSubject(String graphUri, String p, String o) {

		String sparqlCondition = String.format("?s %s %s", p, o);
		String sparqlQuery = SparqlUtils.selectOrderedData(connectionProperties.dataEndpoint + graphUri, sparqlCondition, "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>", "?date");

		QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, sparqlQuery);
		ResultSet resultSet = query.execSelect();

		try(CloseableResultSet set = new CloseableResultSet(resultSet, query)) {
			if(!set.hasNext()) {
				return null;
			}

			return set.next().get("?s").toString();
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
	
	public int countFor(String graphUri, Object ...args) throws IOException {
		
		try(CloseableResultSet set = read(graphUri, Constants.ROOT_RESOURCE + "/data/sparql/count_for.sparql", args)) {
			while (set.hasNext()) {
				QuerySolution querySolution = set.next();
				
				return querySolution.get("count").asLiteral().getInt();
			}
		}
		
		return 0;
	}
	
	public CitizenDocumentsResult findDocumentsFor(String jmbg) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(Constants.ROOT_RESOURCE + "/data/sparql/documents_for.sparql"));
		String notFormattedSparqlCondition = new String(encoded, StandardCharsets.UTF_8);
		
		String formattedSparqlCondition = String.format(notFormattedSparqlCondition, jmbg);

		QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, formattedSparqlCondition);
		ResultSet resultSet = query.execSelect();

		CitizenDocumentsResult result = new CitizenDocumentsResult();
		
		try(CloseableResultSet set = new CloseableResultSet(resultSet, query)) {
			while (set.hasNext()) {
				QuerySolution querySolution = set.next();
				
				String document = querySolution.get("document").asResource().toString();
				String createdAt = querySolution.get("createdAt").asLiteral().toString();

				result.getCitizenDocument().add(CitizenDocumentFactory.create(document, createdAt));
			}
		}
		
		return result;
	}
	
	
	public Links findReferencing(String about, String graphUri) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(Constants.ROOT_RESOURCE + "/data/sparql/referencing.sparql"));
		String notFormattedSparqlCondition = new String(encoded, StandardCharsets.UTF_8);
		
		String formattedSparqlCondition = String.format(notFormattedSparqlCondition, connectionProperties.dataEndpoint + graphUri, about);

		return findLinks(formattedSparqlCondition);
	}
	
	public Links findReferencedBy(String about) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(Constants.ROOT_RESOURCE + "/data/sparql/referenced_by.sparql"));
		String notFormattedSparqlCondition = new String(encoded, StandardCharsets.UTF_8);
		
		String formattedSparqlCondition = String.format(notFormattedSparqlCondition, about);

		return findLinks(formattedSparqlCondition);
	}
	
	private Links findLinks(String sparqlQuery) {
		QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, sparqlQuery);
		ResultSet resultSet = query.execSelect();
		
		Links links = new Links();
		try (CloseableResultSet set = new CloseableResultSet(resultSet, query)) {
			while (set.hasNext()) {
				QuerySolution querySolution = set.next();
				links.getLink().add(LinkFactory.create(querySolution.get("link").toString(), querySolution.get("createdAt").toString()));
			}
		}
		
		return links;
	}
	
	public void updateData(String about, String xml, String graphUri) throws IOException {
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
