package com.vacation.platform.corp;

import com.vacation.platform.api.config.QuerydslConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.vacation.platform.api",
		"com.vacation.platform.corp"
})
@EntityScan(basePackages = {"com.vacation.platform.api", "com.vacation.platform.batch", "com.vacation.platform.corp"})
@Import(QuerydslConfiguration.class)
public class StayFinderCorp {

	public static void main(String[] args) {
		SpringApplication.run(StayFinderCorp.class, args);
	}

}
