package com.ramadan.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

/**
 * @author ZAROUATI Ayoub
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
   public static final String RESPONSABLE = "responsable";

	@Autowired
    private final JwtAuthConverter jwtAuthConverter ;

   

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http.csrf(csrf -> csrf.disable())
       		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
               .authorizeHttpRequests(auth ->
               {
                   auth.requestMatchers("/api/v1/nitro-sales/user/auth/**","/",
                   		"/swagger-ui/**", "/v3/api-docs/**").permitAll();
                   auth.requestMatchers("/api/v1/nitro-sales/**").hasRole(RESPONSABLE);
                   auth.anyRequest().authenticated();
               });

       http.
		        oauth2ResourceServer(oauth2 -> oauth2.jwt(
		                jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
		        ));

       http.
               sessionManagement((session) ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       return http.build();

       
   }
	
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	          CorsConfiguration configuration = new CorsConfiguration();
	          //configuration.setAllowedOriginPatterns(Arrays.asList(https://*.DOMAINE));
	          configuration.setAllowedOriginPatterns(Arrays.asList("*"));
	          configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
	          configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token","client_uuid"));
	          configuration.setExposedHeaders(Arrays.asList("x-auth-token", "X-File-Name"));
	          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	          source.registerCorsConfiguration("/**", configuration);
	          return source;
	    }
	
	
	
}
