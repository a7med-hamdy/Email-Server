package com.emailserver.email_server;


import java.io.IOException;
import java.util.ArrayList;

import com.emailserver.email_server.userAndMessage.Server;
import com.emailserver.email_server.userAndMessage.inbox;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.messageMaker;
import com.emailserver.email_server.userAndMessage.messageType;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServerApplication {

	public static void main(String[] args) throws IOException{
		SpringApplication.run(EmailServerApplication.class, args);
		Server s = Server.getInstanceOf();
		s.SignUp(234,"aly", "pass", "@gmail");
		s.SignUp(342544,"dqly", "pAAss", "@gmail");
		s.SignUp(99999,"pablo", "cocaine", "@colombia");
		s.SignUp(887788,"messi", "score", "@barcelona");
		s.SignUp(555,"tony Soprano", "big head", "@mafia");
		ArrayList<Integer> to = new ArrayList<>();
		to.add(234);
		to.add(555);
		messageType t = new inbox();
		messageMaker maker = new messageMaker();
		message n = maker.getNewMessage(10, "hello", 342544, to, "subject", "time", 1, t);
		s.sendMessage(n);
		// s.createFolder(555, "tobe");
		// s.moveMessage(555, 10, "inbox","tobe");
		// s.createFolder(234, "mails");
		// s.moveMessage(234, 10, "inbox","mails");
		// s.renameFolder(234, "newName", "mails");
		// System.out.println(s.getMessage(234, 10));
	}

}
