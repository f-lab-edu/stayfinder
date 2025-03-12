package com.vacation.platform.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.vacation.platform.api", "com.vacation.platform.batch", "com.vacation.platform.corp"})
public class StayFinderApi {

	public static void main(String[] args) {
		SpringApplication.run(StayFinderApi.class, args);
	}

}