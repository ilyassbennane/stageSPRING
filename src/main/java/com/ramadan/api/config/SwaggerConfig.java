 package com.ramadan.api.config;



import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @author ZAROUATI Ayoub
 */

@Configuration
public class  SwaggerConfig {
	
	/**
	 * Documentation Swagger 3 with security token 
	 * @author ZAROUATI Ayoub
	 */


	private SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer");
	}
	

    @Bean
    OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("ayoub.zerouati@gmail.com");
        contact.setName("Ayoub");
        contact.setUrl("https://www.swagger.ma");

        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Facade API NETRO SALES")
                .version("2.0")
                .contact(contact)
                .description("Explore and interact with our API using this Swagger documentation. "
                		+ "This API provides a comprehensive set of endpoints for managing various aspects of our system. "
                		+ "From user authentication to data retrieval and updates, this documentation outlines the details of each API operation, "
                		+ "including request and response formats. Feel free to test API calls directly from this Swagger UI to better understand how our services work. "
                		+ "If you have any questions or need further assistance, please refer to our support documentation or contact our technical support team.")
                .termsOfService("")
            	.license(mitLicense);
        		
        

        return new OpenAPI()	
    						.addSecurityItem(new SecurityRequirement()
								.addList("Bearer Authentication"))
								.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
							.info(info);
							
    }


    @Bean
    GroupedOpenApi presentation() { // group all APIs with `operation` in the path
      return GroupedOpenApi.builder().group("1- PRESENTATION").pathsToMatch("").build();
    }
    @Bean
    GroupedOpenApi login() { // group all APIs with `operation` in the path
      return GroupedOpenApi.builder().group("2- UTILISATEUR").pathsToMatch("/**/user/**").build();
    }
    @Bean
    GroupedOpenApi referentiel() { // group all APIs with `operation` in the path
      return GroupedOpenApi.builder().group("5- REFERENTIEL").pathsToMatch("/**/referentiel/**").build();
    }

    @Bean
    GroupedOpenApi All() { // group all APIs with `operation` in the path
        return GroupedOpenApi.builder().group(" ALL").pathsToMatch("/**").build();
    }
    

}
