package com.marco.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;

import io.swagger.models.ExternalDocs;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	
	public static final Contact DEFAULT_CONTACT = new Contact(
		      "Marco A. Muñoz", "https://github.com/mamunozg", "marcoamgg@gmail.com");
		  		
		  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =  new HashSet<String>(Arrays.asList("application/json","application/xml"));

		  
		  /**
	 * Publish a bean to generate swagger2 endpoints
	 * 
	 * @return a swagger configuration bean
	 */
	@Bean
	public Docket api() {
		
		
		final List<ResponseMessage> responseMessageList = new ArrayList<ResponseMessage>();
	    
		responseMessageList.add(new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build());
	    responseMessageList.add(new ResponseMessageBuilder().code(404).message("Employee Not Found").build());
	    responseMessageList.add(new ResponseMessageBuilder().code(204).message("There aren't information").build());
	    
		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("employee-group")
				.select()
//				.paths(userPaths())
				.apis(RequestHandlerSelectors.basePackage("com.marco.controller"))
				.paths(regex("/employees.*"))
				.build()
				.apiInfo(usersApiInfo())				
				.tags(new Tag("EmployeeRestController", "API Rest to check employess"))
				.useDefaultResponseMessages(false)
		        .globalResponseMessage(RequestMethod.GET, responseMessageList);
//		        .securitySchemes(Arrays.asList(securityScheme()));
//		        .securityContexts(Arrays.asList(securityContext()));		 

 
	}

	
	@Bean
	public UiConfiguration uiConfig() {
	    return UiConfigurationBuilder.builder()//<20>
	            .deepLinking(true)
	            .displayOperationId(false)
	            .docExpansion(DocExpansion.LIST)
	            .defaultModelsExpandDepth(0)
	            .defaultModelExpandDepth(0)
	            .defaultModelRendering(ModelRendering.EXAMPLE)
	            .displayRequestDuration(false)
	            .filter(false)
	            .maxDisplayedTags(null)
	            .operationsSorter(OperationsSorter.ALPHA)
	            .showExtensions(false)
	            .tagsSorter(TagsSorter.ALPHA)	            
	            .validatorUrl(null)
	            .build();
	      }
	
	private static final String API_TITLE = "Marco API [Employee]";
	private static final String API_DESCRIPTION = "This is a sample server Employee server.  You can find out more about  Swagger at [http://swagger.io](http://swagger.io) or on [gitHub](https://github.com/mamunozg).";
	private static final String TERMS_OF_SERVICE = "[Terms of service](#)";
	
	private io.swagger.models.Tag getTagApiEmployee() {
		io.swagger.models.Tag tag = new io.swagger.models.Tag();
		tag.setName("EmployeeRestController");
		tag.setDescription("API Rest to check employess");
		ExternalDocs docs = new ExternalDocs();
        docs.setUrl("http://swagger.io/");
        docs.setDescription("Find out more: ");
        tag.setExternalDocs(docs);
        return tag;
	}
	
	private ApiInfo usersApiInfo() {
		
		return new ApiInfoBuilder()
				.title(API_TITLE)
				.description(API_DESCRIPTION)
				.termsOfServiceUrl(TERMS_OF_SERVICE)
				.contact(DEFAULT_CONTACT)
				.version("1.0")
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")				
				.build();
	}

	/**
	 * Config paths.
	 *
	 * @return the predicate
	 */
	private Predicate<String> userPaths() {
		return regex("/employees.*");
	}

	private static final String CLIENT_ID = "1234567890";
	private static final String CLIENT_SECRET = "ABCDEFGHIJ";
	private static final String AUTH_SERVER = "ABCDEFGHIJ";

	private SecurityScheme securityScheme() {
	    GrantType grantType = new AuthorizationCodeGrantBuilder().tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
	        .tokenRequestEndpoint(
	          new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_ID))
	        .build();
	 
	    SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
	        .grantTypes(Arrays.asList(grantType))
	        .scopes(Arrays.asList(scopes()))
	        .build();
	    return oauth;
	}
	
	private AuthorizationScope[] scopes() {
	    AuthorizationScope[] scopes = { 
	      new AuthorizationScope("read", "for read operations"), 
	      new AuthorizationScope("write", "for write operations"), 
	      new AuthorizationScope("foo", "Access foo API") };
	    return scopes;
	}
}
