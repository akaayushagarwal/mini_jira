package com.example.mini_jira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniJiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniJiraApplication.class, args);
	}

}
