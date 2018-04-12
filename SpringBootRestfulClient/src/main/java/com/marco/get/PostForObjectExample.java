package com.marco.get;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.marco.model.Employee;

public class PostForObjectExample {

	static final String URL_EMPLOYEES = "http://localhost:8888/employees";

	public static void main(String args[]) {

		String empNo = "E11";
		Employee newEmployee = new Employee(empNo, "Tom", "Cleck");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		RestTemplate restClient = new RestTemplate();
		
		HttpEntity<Employee> requestBody = new HttpEntity<>(newEmployee, headers);
		
		
		Employee emp = restClient.postForObject(URL_EMPLOYEES, requestBody, Employee.class);
		
		if(emp != null && emp.getEmpNo() != null) {			
			System.out.println("Se ha creado el empleado: " + emp);
		}
		else {
			System.out.println("No se ha creado el empleado");
		}
		
		
		

		
		

	}
}
