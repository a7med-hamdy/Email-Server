package com.emailserver.email_server;


import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServerApplication {

	public static void main(String[] args) throws IOException{
		SpringApplication.run(EmailServerApplication.class, args);
		Server s = Server.getInstanceOf();
		s.SignUp(234,"aly", "pass", "@gmail");
		s.SignUp(342544,"dqly", "pAAss", "@gmail");
		System.out.println(s.getUsers());
	}

}
