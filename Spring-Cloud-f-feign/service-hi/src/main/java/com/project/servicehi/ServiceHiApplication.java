package com.project.servicehi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@EnableAutoConfiguration //自动加载配置信息
//@PropertySource("classpath:application.yml")
@EnableEurekaClient
@RestController
public class ServiceHiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceHiApplication.class, args);
	}
	
	@Value("${server.port}")
	String port;
	
	@RequestMapping("/hi")
	public String home(@RequestParam(value="name",defaultValue="forezp") String name){
		return "hi " + name + " ,i am from port:" + port;
	}
}
