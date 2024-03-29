package com.kideveloper_dart.my_spring_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MySpringWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringWebApplication.class, args);
	}
}
