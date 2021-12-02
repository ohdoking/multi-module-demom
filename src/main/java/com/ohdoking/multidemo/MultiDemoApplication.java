package com.ohdoking.multidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ohdoking.multi")
@EnableJpaRepositories(basePackages = "com.ohdoking.multi.api.repository")
@EntityScan(basePackages = "com.ohdoking.multi.api.domain")
public class MultiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDemoApplication.class, args);
	}

}
