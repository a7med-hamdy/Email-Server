package com.emailserver.email_server;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.util.ArrayList;

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
		Server s = Server.getInstanceOf();
		Server s2 = Server.getInstanceOf();
		ArrayList<userContact> contacts = new ArrayList<>();
		user user = new user(77,"aaser","yaser","@aaser", contacts);
		contacts.add(new userContact(user, "mohamed"));
		s.SignUp(234,"aly", "pass", "a7a@gmail",contacts);
		s.SignUp(342544,"dqly", "pAAss", "@gmail",contacts);
		s.SignUp(99999,"pablo", "cocaine", "@colombia",contacts);
		s2.SignUp(887788,"messi", "score", "@barcelona",contacts);
		s2.SignUp(555,"tony Soprano", "big head", "@mafia",contacts);
		
		Queue<Integer> to = new LinkedList<>();
		to.add(234);
		to.add(555);
		Queue<Integer> to2 = new LinkedList<>();
		to2.add(887788);
		to2.add(555);
		messageMaker maker = new messageMaker();
		////////
		Date now=new Date();
		message n = maker.getNewMessage(10, "hello", 5,342544, to, "subject", now, 1, new File []{new File("image.jpf")});
		message x = maker.getNewMessage(1010, "another message", 15,342544, to2, "TWO", now, 4, new File []{new File("book.pdf")});
		//////////
		s.sendMessage(n);
		//s.sendMessage(x);
		s.sendMessage(x, "draft");
		// System.out.println(s.requestFolder(342544, "sent","time").toString());
		s.createFolder(555, "tobe");
		s.moveMessage(555, 10, "inbox","tobe");
		s.createFolder(234, "mails");
		s.moveMessage(234, 10, "inbox","mails");
		s.renameFolder(234, "newName", "mails");
	}

}
