package com.marco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.marco"})
public class SpringBootRestApi02Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApi02Application.class, args);
	}
}
