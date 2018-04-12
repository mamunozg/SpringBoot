package com.marco.get;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.marco.model.Employee;

public class PutSimpleExample {
	static final String URL_EMPLOYEES = "http://localhost:8888/employees";

	public static void main(String args[]) {

		String empNo = "E01";
		Employee employee = new Employee(empNo, "Tommmmmmm", "Cleck");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		HttpEntity<Employee> requestBody = new HttpEntity<Employee>(employee, headers);

		
		
		RestTemplate restClient = new RestTemplate();
		System.out.println("11111111111");
		restClient.put(URL_EMPLOYEES + "/" + empNo, requestBody);
		System.out.println("22222222222");
		
		Employee emp = restClient.getForObject(URL_EMPLOYEES + "/" + empNo, Employee.class);
		System.out.println("3333333333");
		if(emp!= null) {			
			System.out.println("Se ha actualizado el empleado: " + emp);
		}

	}

}
