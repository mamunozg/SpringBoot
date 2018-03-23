package com.marco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.marco"})
public class SpringBootRestApi01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApi01Application.class, args);
	}
}
