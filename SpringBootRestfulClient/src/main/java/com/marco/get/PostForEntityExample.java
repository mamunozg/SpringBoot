package com.marco.get;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.marco.model.Employee;

public class PostForEntityExample {

	static final String URL_EMPLOYEES = "http://localhost:8888/employees";

	public static void main(String args[]) {

		String empNo = "E01";
		Employee newEmployee = new Employee(empNo, "Tom", "Cleck");
		RestTemplate restClient = new RestTemplate();
	
		HttpEntity<Employee> requestBody = new HttpEntity<>(newEmployee);
		
		
		ResponseEntity<Employee> emp = restClient.postForEntity(URL_EMPLOYEES, requestBody, Employee.class);
		
		System.out.println(emp.getStatusCodeValue());
		
		if(emp.getStatusCode() == HttpStatus.OK) {			
			System.out.println("Se ha creado el empleado: " + emp.getBody());
		}
		else {
			System.out.println("No se ha creado el empleado");
		}

	}
}
