package com.subsplatform.subs_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; // ADD THIS

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // ADD THE EXCLUDE HERE
public class SubsPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubsPlatformApplication.class, args);
	}

}