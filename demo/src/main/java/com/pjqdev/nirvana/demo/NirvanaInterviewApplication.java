package com.pjqdev.nirvana.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.pjqdev.nirvana.demo.database.IssuesDB.getIssues;

@SpringBootApplication
public class NirvanaInterviewApplication {

	public static void main(String[] args) {
		getIssues();
		SpringApplication.run(NirvanaInterviewApplication.class, args);
	}
}
