package com.claro.moose.config;

import com.claro.moose.util.authentication.PAWClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PAWClientConfiguration {

    // This package must match the package in the <generatePackage> specified in pom.xml
    @Value("${paw.soap.package}")
    private String contextPackage;

    @Value("${paw.soap.endpoint}")
    private String endpoint;

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(contextPackage);
		return marshaller;
	}

	@Bean
	public PAWClient pawClient(Jaxb2Marshaller marshaller) {
		PAWClient client = new PAWClient();
		client.setDefaultUri(endpoint);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}