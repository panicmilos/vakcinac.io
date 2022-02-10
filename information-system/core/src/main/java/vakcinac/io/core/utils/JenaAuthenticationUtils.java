package vakcinac.io.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JenaAuthenticationUtils {
	static public class JenaConnectionProperties {

		public String endpoint;
		public String dataset;

		public String queryEndpoint;
		public String updateEndpoint;
		public String dataEndpoint;

		public String username;
		public String password;

		public JenaConnectionProperties(Properties props) {
			super();
			dataset = props.getProperty("conn.dataset").trim();
			endpoint = props.getProperty("conn.endpoint").trim();

			queryEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.query").trim());
			updateEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.update").trim());
			dataEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.data").trim());

			username = props.getProperty("conn.username").trim();
			password = props.getProperty("conn.password").trim();
		}
	}

	public static JenaConnectionProperties loadProperties() throws IOException {
		String propsName = "jena.properties";

		InputStream propsStream = openStream(propsName);
		if (propsStream == null)
			throw new IOException("Could not read properties " + propsName);

		Properties props = new Properties();
		props.load(propsStream);

		return new JenaConnectionProperties(props);
	}

	public static InputStream openStream(String fileName) throws IOException {
		return JenaAuthenticationUtils.class.getClassLoader().getResourceAsStream(fileName);
	}
}
