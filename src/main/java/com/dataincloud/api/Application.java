package com.dataincloud.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "data-in-cloud",
				contact = @Contact(
						name = "Maksym Mishchuk",
						email = "maxim.mishchuk@gmail.com"
				),
				description = "Simple social network web-service",
				version = "feature/Phase1"
		),
		servers = {

				@Server(url = "http://localhost:8080", description = "labs server"),
		}

)

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
