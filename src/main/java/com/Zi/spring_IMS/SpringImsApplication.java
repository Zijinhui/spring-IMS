package com.Zi.spring_IMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringImsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringImsApplication.class, args);
	}

}
