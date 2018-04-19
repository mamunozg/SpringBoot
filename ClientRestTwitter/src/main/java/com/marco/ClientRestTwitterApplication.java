package com.marco;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;

@SpringBootApplication
public class ClientRestTwitterApplication {

	@Value("${twitter.consumer-key}")
	private String CONSUMER_KEY;
	@Value("${twitter.consumer-secret}")
	private String CONSUMER_SECRET;
	@Value("${twitter.access-token}")
	private String ACCESS_TOKEN;
	@Value("${twitter.access-token-secret}")
	private String ACCESS_TOKEN_SECRET;
	@Value("${proxy.server}")
	private String PROXY_SERVER;
	@Value("${proxy.port}")
	private String PROXY_PORT;
	@Value("${proxy.user}")
	private String PROXY_USER;
	@Value("${proxy.pass}")
	private String PROXY_PASS;
	
	public static void main(String[] args) {
		SpringApplication.run(ClientRestTwitterApplication.class, args);		
	}
	
	@Bean
	public OAuthRestTemplate restTemplate() {
	
		BaseProtectedResourceDetails protectedResourceDetails = new BaseProtectedResourceDetails();
		protectedResourceDetails.setConsumerKey(CONSUMER_KEY);
		protectedResourceDetails.setSharedSecret(new SharedConsumerSecretImpl(CONSUMER_SECRET));

		OAuthRestTemplate oAuthRestTemplate = new OAuthRestTemplate(protectedResourceDetails);
		oAuthRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(ACCESS_TOKEN, ACCESS_TOKEN_SECRET));
		return setProxy(oAuthRestTemplate, false);
	} 
	
	private OAuthRestTemplate setProxy(OAuthRestTemplate oAuthRestTemplate, boolean withProxy) {
		

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		
        if(withProxy) {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();            
            clientBuilder.useSystemProperties();
            clientBuilder.setProxy(new HttpHost(PROXY_SERVER, 8080));

            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	        credentialsProvider.setCredentials(new AuthScope(PROXY_SERVER, Integer.valueOf(PROXY_PORT)),new UsernamePasswordCredentials(PROXY_USER, PROXY_PASS));
	        clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
	        clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

	        CloseableHttpClient client = clientBuilder.build();
	        factory.setHttpClient(client);
        }		
        

        
        

	    
		oAuthRestTemplate.setRequestFactory(factory);

		return oAuthRestTemplate;
	}
	
}
