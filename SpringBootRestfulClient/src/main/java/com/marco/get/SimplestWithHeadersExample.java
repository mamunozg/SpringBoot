package com.marco.get;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SimplestWithHeadersExample {

	static final String URL_EMPLOYEES = "http://localhost:8888/employees";
	
	public static void main(String args[]) {

		System.out.println(getEmployeeWithHeader(getHeaders()));
			
	}

	public static HttpEntity<String> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
		headers.set("my_other_key", "my_otheR_value");
		
		return new HttpEntity<>(headers);
	}
	
	private static String getEmployeeWithHeader(HttpEntity<String> httpEntity) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES, HttpMethod.GET, httpEntity, String.class);
		return response.getBody();		
	}
}
