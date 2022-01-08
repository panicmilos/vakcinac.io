package vakcinac.io.citizen.repository.jena;

import java.io.IOException;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientFactory {

	public static CloseableHttpClient newClient() throws IOException {
		CredentialsProvider provider = CredentialsProviderFactory.newProvider();

		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		return client;
	}

}
