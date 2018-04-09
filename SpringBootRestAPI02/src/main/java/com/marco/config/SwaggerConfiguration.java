package com.marco.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private static final String CLIENT_ID = "1234567890";
	private static final String CLIENT_SECRET = "ABCDEFGHIJ";
	private static final String AUTH_SERVER = "ABCDEFGHIJ";
	
	/**
	 * Publish a bean to generate swagger2 endpoints
	 * 
	 * @return a swagger configuration bean
	 */
	@Bean
	public Docket api() {
		
		final List<ResponseMessage> responseMessageList = new ArrayList<ResponseMessage>();
//	    responseMessageList.add(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error")).build());
	    responseMessageList.add(new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build());
	    
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("user-initial-version")
				.select()
				 .paths(userPaths())
				.apis(RequestHandlerSelectors.basePackage("com.marco.controller"))
				.paths(regex("/bootApp2/api/user.*"))				
				.build()
				.apiInfo(usersApiInfo())
				.tags(new Tag("RestApiController", "API example to users"), new Tag("RestApiControllerVersion", "API with versioned"))
				.useDefaultResponseMessages(false)
		        .globalResponseMessage(RequestMethod.GET, responseMessageList)
		        .securitySchemes(Arrays.asList(securityScheme()))
		        .securityContexts(Arrays.asList(securityContext()));		 
	}

	
	
	private ApiInfo usersApiInfo() {
		
		Contact contact = new Contact("Marco Antonio Muñoz", "https://github.com/mamunozg", "marcoamgg@gmail.com");		
		return new ApiInfoBuilder()
				.title("Service User")
				.description("API to start with Swagger")
				.version("1.0")
				.license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.contact(contact)
				.build();
	}

	/**
	 * Config paths.
	 *
	 * @return the predicate
	 */
	private Predicate<String> userPaths() {
		return or(regex("/api/posts.*"), regex("/api/user.*"));
	}

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

	@Bean
	public SecurityConfiguration security() {
	    return SecurityConfigurationBuilder.builder()
	        .clientId(CLIENT_ID)
	        .clientSecret(CLIENT_SECRET)
	        .scopeSeparator(" ")
	        .useBasicAuthenticationWithAccessCodeGrant(true)
	        .build();
	}
	
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	      .securityReferences(Arrays.asList(new SecurityReference("spring_oauth", scopes())))
	      .forPaths(PathSelectors.regex("/api.*"))
	      .build();
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
}
