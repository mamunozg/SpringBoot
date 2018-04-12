package com.marco.get;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.marco.model.Employee;

public class SimplestPojoWithHeadersExample {

	static final String URL_EMPLOYEES = "http://localhost:8888/employees";

	public static void main(String args[]) {

		getEmployeeWithHeader(getHeaders());

	}

	public static HttpEntity<String> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
		headers.set("my_other_key", "my_otheR_value");

		return new HttpEntity<>(headers);
	}

	private static void getEmployeeWithHeader(HttpEntity<String> httpEntity) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Employee[]> response = restTemplate.exchange(URL_EMPLOYEES, HttpMethod.GET, httpEntity,
				Employee[].class);
		
		System.out.println(response.getStatusCode());

		if(response.getStatusCode() == HttpStatus.OK) {
			Employee[] listEmployee = response.getBody();
	
			if (listEmployee != null) {
	
				System.out.println(listEmployee);
				for (Employee e : listEmployee) {
					System.out.println(e);
				}
			}
		}
	}
}
