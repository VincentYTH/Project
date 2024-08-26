package com.bootcamp.project.project_yahoo_finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectYahooFinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectYahooFinanceApplication.class, args);
	}

}
