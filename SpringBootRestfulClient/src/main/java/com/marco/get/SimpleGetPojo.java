package com.marco.get;

import org.springframework.web.client.RestTemplate;

import com.marco.model.Employee;

public class SimpleGetPojo {

	static final String URL_EMPLOYEES = "http://localhost:8888/employees";
	
	public static void main(String args[]) {
		getEmployees();
	}

	public static void getEmployees() {

		RestTemplate restTemplate = new RestTemplate();
		// Send request with GET method and default Headers.
		Employee[] listEmployee = restTemplate.getForObject(URL_EMPLOYEES, Employee[].class);
		
		
		if(listEmployee != null) {
			
			System.out.println(listEmployee);
			for(Employee e : listEmployee) {
				System.out.println(e);
			}
		}
	}
}
