package com.company.motoinventoryconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MotoInventoryConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoInventoryConfigServerApplication.class, args);
	}

}
