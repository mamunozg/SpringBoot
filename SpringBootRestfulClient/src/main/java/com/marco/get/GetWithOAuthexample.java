package com.marco.get;

import java.net.ProxySelector;
import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;

public class GetWithOAuthexample {

	static final String URL_EMPLOYEES = "https://api.twitter.com/1.1/trends/available.json";
	
	public static final String USER_NAME = "abc";
    public static final String PASSWORD = "123";
    
	public static void main(String[] args) {
		
			
		BaseProtectedResourceDetails protectedResourceDetails = new BaseProtectedResourceDetails();
		protectedResourceDetails.setConsumerKey("XXXXXX");
		protectedResourceDetails.setSharedSecret(new SharedConsumerSecretImpl("XXXXXX"));

		OAuthRestTemplate oAuthRestTemplate = new OAuthRestTemplate(protectedResourceDetails);
		oAuthRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("yyyy-yyyyyyyy", "yyyyy"));
		RequestEntity<String> httpEntity;
		try {

			
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope("proxy.indra.es", 8080),new UsernamePasswordCredentials("mamunozg", "Mamun0zg3"));
			
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            
            clientBuilder.useSystemProperties();
            clientBuilder.setProxy(new HttpHost("proxy.indra.es", 8080));
            clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
 
            CloseableHttpClient client = clientBuilder.build();
 
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(client);

		    
			oAuthRestTemplate.setRequestFactory(factory);
			
			
			
			httpEntity = new RequestEntity<String>(HttpMethod.GET, new URI("https://api.twitter.com/1.1/trends/available.json"));
		    ResponseEntity<String> responseEntity = oAuthRestTemplate.exchange(httpEntity, String.class);
		    System.out.println(responseEntity.getBody().toString());
		} catch (Exception e) {
		    e.printStackTrace();
		}

		
	}
	
	

}
