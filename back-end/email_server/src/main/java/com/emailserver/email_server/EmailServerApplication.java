package com.emailserver.email_server;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.messageMaker;
import com.emailserver.email_server.userAndMessage.user;
import com.emailserver.email_server.userAndMessage.userContact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServerApplication {

	public static void main(String[] args) throws IOException{
		SpringApplication.run(EmailServerApplication.class, args);
	}

}
