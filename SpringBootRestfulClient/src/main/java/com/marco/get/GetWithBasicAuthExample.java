package com.marco.get;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GetWithBasicAuthExample {

	static final String URL_EMPLOYEES = "http://localhost:8888/employees";
	
	public static final String USER_NAME = "abc";
    public static final String PASSWORD = "123";
    
	public static void main(String[] args) {
		
			
		String auth = USER_NAME + ":" + PASSWORD;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String autHeader = "Basic " + new String(encodedAuth);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", autHeader);
		headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("my_other_key", "my_other_value");
		
		 HttpEntity<String> entity = new HttpEntity<String>(headers);
		 
		 RestTemplate restTemplate = new RestTemplate();
		 
		 ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES, HttpMethod.GET, entity, String.class);
		 
		 System.out.println(response.getStatusCodeValue());
		 System.out.println(response.getBody());
	}
	
	

}
