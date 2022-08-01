package sg.edu.iss.restfulend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestfulEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulEndApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> { 

		};
	}

}
