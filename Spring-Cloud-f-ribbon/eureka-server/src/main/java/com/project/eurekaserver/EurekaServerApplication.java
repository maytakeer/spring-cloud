package com.project.eurekaserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
//@EnableAutoConfiguration
@EnableEurekaServer

public class EurekaServerApplication {
	
//	@Value("${server.port}")
//	String port;
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
