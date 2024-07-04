package com.ramadan.api.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ZAROUATI Ayoub
 */

public class TokenFilter implements Filter {
	
	static  private String Authorization;
	static  private String token;

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Extract the token from the Authorization header
    	
        String authorizationHeader = ((HttpServletRequest) request).getHeader("Authorization");
        this.setAuthorization(authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Assuming "Bearer " is followed by the token
            // Do something with the token, for example, store it in a thread-local variable for later use
            this.setToken(token);
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
    
    public static String getAuthorization() {
		return Authorization;
	}

	public void setAuthorization(String authorization) {
		Authorization = authorization;
	}

	public static String getToken() {
		return token;
	}

	public void setToken(String token) {
		TokenFilter.token = token;
	}
}
