package com.marco.get;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.marco.model.Employee;

	

public class DeleteSimpleExample {

	
	static final String RESOURCE_URL = "http://localhost:8888/employees/E01";
	
	public static void main(String args[]) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(RESOURCE_URL, Employee.class);
		
		System.out.println("Primera consulta:" + responseEntity.getStatusCode());
		restTemplate.delete(RESOURCE_URL);
				
		System.out.println("000000000000000000000");
		responseEntity = restTemplate.getForEntity(RESOURCE_URL, Employee.class);
		System.out.println("AAAAAAAAAAAAAAAAAAAAA");
		System.out.println("Segunda consulta:" + responseEntity.getStatusCode());
		System.out.println("BBBBBBBBBBBBBBBBBBBBB");
		
	}
}
