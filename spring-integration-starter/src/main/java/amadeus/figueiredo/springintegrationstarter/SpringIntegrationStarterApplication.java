package amadeus.figueiredo.springintegrationstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
public class SpringIntegrationStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationStarterApplication.class, args);
	}

}
