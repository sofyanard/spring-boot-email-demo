package com.sofyanard.springbootemaildemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpringBootEmailDemoApplication {

	// @Autowired EmailSenderService emailSenderService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailDemoApplication.class, args);
	}

	/*
	@EventListener(ApplicationReadyEvent.class)
	public void sendEmail() {
		emailSenderService.sendEmail("sofyanard@gmail.com",
				"This is Subject",
				"This is Body");
	}
	*/
}
