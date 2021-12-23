package com.emailserver.email_server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServerApplication {

	public static void main(String[] args){
		SpringApplication.run(EmailServerApplication.class, args);
		Server s = Server.getInstanceOf();
		s.SignUp("aly", "pass", "2344");
	}

}
