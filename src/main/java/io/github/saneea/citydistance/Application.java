package io.github.saneea.citydistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.saneea.citydistance.beans.DistanceEngine;
import io.github.saneea.citydistance.beans.DistanceEngineImpl;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public DistanceEngine gistanceEngine() {
		return new DistanceEngineImpl();
	}
}
