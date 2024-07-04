package com.ramadan.api;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ramadan.api.config.LoggingInterceptor;

@SpringBootApplication
public class RamadanApplication {

	public static void main(String[] args) {
		SpringApplication.run(RamadanApplication.class, args);
	}

	@Bean
	 ModelMapper modelMapper(){
		return new ModelMapper();
	}

    @Bean
    RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
          TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

          SSLContextBuilder builder = new SSLContextBuilder();
          builder.loadTrustMaterial(acceptingTrustStrategy);

          @SuppressWarnings("deprecation")
     CloseableHttpClient httpClient = HttpClients.custom()
     .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
             .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                     .setSslContext(builder.build())
                     .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                     .build())
             .build())
     .build();
          HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
          requestFactory.setHttpClient(httpClient);
          
          BufferingClientHttpRequestFactory rf = new BufferingClientHttpRequestFactory(requestFactory);

          List<ClientHttpRequestInterceptor> ris = new ArrayList<>();
          ClientHttpRequestInterceptor ri = new LoggingInterceptor();
          ris.add(ri);

          RestTemplate rt = new RestTemplate(rf);
          rt.setInterceptors(ris);
          return rt;
    }
}
