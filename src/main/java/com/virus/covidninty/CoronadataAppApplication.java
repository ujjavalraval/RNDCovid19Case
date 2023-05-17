package com.virus.covidninty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronadataAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronadataAppApplication.class, args);
	}

}
