package com.github.gabrielbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringJWTStarter {

	public static void main(String[] args) {
		SpringApplication.run(SpringJWTStarter.class, args);
	}
}
