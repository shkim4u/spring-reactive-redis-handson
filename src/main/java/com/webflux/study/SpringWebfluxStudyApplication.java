package com.webflux.study;

import com.webflux.study.web_client.HelloWebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebfluxStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxStudyApplication.class, args);


		HelloWebClient hwc = new HelloWebClient();
		System.out.println(hwc.getResult());
	}
}
