package com.example.mobile_banking_apprest;

import org.omg.CORBA.TIMEOUT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MobileBankingAppRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileBankingAppRestApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(5000);
		requestFactory.setReadTimeout(5000);

		restTemplate.setRequestFactory(requestFactory);

		return restTemplate;

//		return new RestTemplate();
	}
}
