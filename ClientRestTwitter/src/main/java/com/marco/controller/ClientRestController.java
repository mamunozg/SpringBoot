package com.marco.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.model.Trend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trends")
@Api(tags= {"ClientRestController"})
public class ClientRestController {

	@Autowired
	private OAuthRestTemplate restTemplate;
	
	
	@GetMapping(produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ApiOperation(value="Search Trends", notes="Without params")
	public ResponseEntity<Trend[]> getTrends() throws URISyntaxException {


		RequestEntity<String> httpEntity = new RequestEntity<String>(HttpMethod.GET, new URI("https://api.twitter.com/1.1/trends/available.json"));
		
	    ResponseEntity<Trend[]> responseEntity = restTemplate.exchange(httpEntity, Trend[].class);
	    System.out.println(responseEntity.getBody().toString());

//		Gson gson = new GsonBuilder().create();
//		
//		ResponseEntity<String> responseEntity = restTemplate.exchange(httpEntity,String.class);
//		ResponseEntity<Trend[]> response = gson.fromJson(responseEntity, Trend.class)
		
	    return responseEntity;
	    			
	}
}
