package com.emailserver.email_server;


import java.io.IOException;
import java.util.ArrayList;

import com.emailserver.email_server.userAndMessage.Server;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.messageMaker;


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
		ArrayList<Integer> to2 = new ArrayList<>();
		to2.add(887788);
		to2.add(555);
		messageMaker maker = new messageMaker();
		message n = maker.getNewMessage(10, "hello", 342544, to, "subject", "9000", 1);
		message x = maker.getNewMessage(1010, "another message", 342544, to2, "TWO", "10", 4);
		s.sendMessage(n);
		s.sendMessage(x);
		System.out.println(s.requestFolder(342544, "sent","time").toString());
		s.createFolder(555, "tobe");
		s.moveMessage(555, 10, "inbox","tobe");
		s.createFolder(234, "mails");
		s.moveMessage(234, 10, "inbox","mails");
		s.renameFolder(234, "newName", "mails");
	}

}
