package br.com.challenge.tasklistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@SpringBootApplication
public class TasklistAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasklistAppApplication.class, args);
	}

}
