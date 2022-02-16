package vakcinac.io.civil.servant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;

import vakcinac.io.civil.servant.models.red.RedCekanja;
import vakcinac.io.civil.servant.models.stanj.StanjeVakcina;
import vakcinac.io.civil.servant.service.RedCekanjaService;
import vakcinac.io.civil.servant.service.StanjeVakcinaService;
import vakcinac.io.core.Constants;
import vakcinac.io.core.utils.ExistAuthenticationUtils;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

	@Value("${root.package}")
	private String rootPackage;

	@Value("${root.resource}")
	private String rootResource;
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private RedCekanjaService redCekanjaService;
	
	@Autowired
	private StanjeVakcinaService stanjeVakcinaService;

	public static void main(String[] args) { SpringApplication.run(Application.class, args); }

	@Override
	public void run(String... args) {
		try {

			Constants.ROOT_PACKAGE = rootPackage;
			Constants.ROOT_RESOURCE = rootResource;

			System.out.println(Constants.ROOT_PACKAGE);
			System.out.println(Constants.ROOT_RESOURCE);

			//initialize database driver
			ExistAuthenticationUtils.ExistConnectionProperties conn = ExistAuthenticationUtils.loadProperties();
			Class<?> cl = Class.forName(conn.driver);

			// encapsulation of the database driver functionality
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			database.setProperty("create-database", "true");

			// entry point for the API which enables you to get the Collection reference
			DatabaseManager.registerDatabase(database);

			System.out.println("eXist database driver initialized.");
			
			redCekanjaService.create(new RedCekanja());
			stanjeVakcinaService.create(new StanjeVakcina());
		} catch (Exception e) {
			System.out.println("[ERROR] An error has occured while trying to initialize eXist database drive.");
		}
	}


}
