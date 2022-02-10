package vakcinac.io.core.repository.jena;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import vakcinac.io.core.utils.JenaAuthenticationUtils;
import vakcinac.io.core.utils.JenaAuthenticationUtils.JenaConnectionProperties;

public class CredentialsProviderFactory {

	public static CredentialsProvider newProvider() throws IOException {
		JenaConnectionProperties connectionProperties = JenaAuthenticationUtils.loadProperties();

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(connectionProperties.username,
				connectionProperties.password);
		provider.setCredentials(AuthScope.ANY, credentials);

		return provider;

	}

}
