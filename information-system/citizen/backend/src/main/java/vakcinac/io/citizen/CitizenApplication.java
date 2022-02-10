package vakcinac.io.citizen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;

import vakcinac.io.core.Constants;
import vakcinac.io.core.utils.ExistAuthenticationUtils;
import vakcinac.io.core.utils.ExistAuthenticationUtils.ExistConnectionProperties;

@SpringBootApplication
public class CitizenApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CitizenApplication.class, args);
	}

	@Value("${root.package}")
	private String rootPackage;

	@Value("${root.resource}")
	private String rootResource;

    @Override
    public void run(String... args) {
		try {

			Constants.ROOT_PACKAGE = rootPackage;
			Constants.ROOT_RESOURCE = rootResource;

			//initialize database driver
			ExistConnectionProperties conn = ExistAuthenticationUtils.loadProperties();
			Class<?> cl = Class.forName(conn.driver);

			// encapsulation of the database driver functionality
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			database.setProperty("create-database", "true");

			// entry point for the API which enables you to get the Collection reference
			DatabaseManager.registerDatabase(database);
		
			System.out.println("eXist database driver initialized.");
		} catch (Exception e) {
			System.out.println("[ERROR] An error has occured while trying to initialize eXist database drive.");
		}


    }

}
