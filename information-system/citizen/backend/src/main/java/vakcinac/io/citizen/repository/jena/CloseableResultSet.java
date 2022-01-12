package vakcinac.io.citizen.repository.jena;

import java.io.Closeable;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.ResultSetCheckCondition;

public class CloseableResultSet extends ResultSetCheckCondition implements ResultSet, Closeable {
	private QueryExecution query;
	
	public CloseableResultSet(ResultSet resultSet, QueryExecution query) {
		super(resultSet, query);
		this.query = query;
	}

	@Override
	public void close() {
		query.close();
	}

}
