package com.marco.get;

import org.springframework.web.client.RestTemplate;

public class SimplestGetExample {

	static final String URL_EMPLOYEES = "http://localhost:8888/employees";

	
	public static void main(String args[]) {
		
		System.out.println(getEmployees());
	}
	
	public static String getEmployees() {
		
       	RestTemplate restTemplate = new RestTemplate();
		//Send request with GET method and default Headers.
		return restTemplate.getForObject(URL_EMPLOYEES, String.class);
	}
}
