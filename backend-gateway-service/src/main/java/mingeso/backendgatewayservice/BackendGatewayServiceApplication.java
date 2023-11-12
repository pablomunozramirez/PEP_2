package mingeso.backendgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConfigServer
public class BackendGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendGatewayServiceApplication.class, args);
	}

}
