package vakcinac.io.core.repository.jena;

import java.io.IOException;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.BasicHttpContext;

public class HttpContextFactory {
	
	public static BasicHttpContext newContext() throws IOException {
		CredentialsProvider provider = CredentialsProviderFactory.newProvider();
		
        BasicHttpContext context = new BasicHttpContext();
        context.setAttribute(ClientContext.CREDS_PROVIDER, provider);

        return context;

	}
}
	