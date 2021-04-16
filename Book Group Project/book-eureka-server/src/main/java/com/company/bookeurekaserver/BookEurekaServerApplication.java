package com.company.bookeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BookEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookEurekaServerApplication.class, args);
	}

}
