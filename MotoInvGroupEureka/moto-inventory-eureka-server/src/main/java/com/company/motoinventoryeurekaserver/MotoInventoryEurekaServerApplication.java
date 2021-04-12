package com.company.motoinventoryeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MotoInventoryEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoInventoryEurekaServerApplication.class, args);
	}

}
