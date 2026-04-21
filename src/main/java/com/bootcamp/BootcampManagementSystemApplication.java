package com.bootcamp;

import com.bootcamp.infrastructure.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootcampManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootcampManagementSystemApplication.class);
		app.addInitializers(new DotenvInitializer());
		app.run(args);
	}

}
