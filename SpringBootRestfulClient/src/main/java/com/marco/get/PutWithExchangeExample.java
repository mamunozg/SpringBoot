package com.marco.get;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.marco.model.Employee;

public class PutWithExchangeExample {
	static final String URL_EMPLOYEES = "http://localhost:8888/employees";

	public static void main(String args[]) {

		String empNo = "E02";
		Employee employee = new Employee(empNo, "Marco Antonio2", "Develop");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		HttpEntity<Employee> requestBody = new HttpEntity<Employee>(employee, headers);
		
		RestTemplate restClient = new RestTemplate();
//		restClient.exchange(URL_EMPLOYEES + "/" + empNo, HttpMethod.PUT, requestBody, Void.class);
		
		ResponseEntity<Void> responseEntity = restClient.exchange(URL_EMPLOYEES + "/" + empNo, HttpMethod.PUT, requestBody, Void.class);
		System.out.println(responseEntity.getStatusCode());
		
		
		
		Employee emp = restClient.getForObject(URL_EMPLOYEES + "/" + empNo, Employee.class);
		if(emp!= null) {			
			System.out.println("Se ha actualizado el empleado: " + emp);
		}

	}

}
