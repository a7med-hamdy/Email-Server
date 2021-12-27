package com.emailserver.email_server;
import java.io.File;
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
		Server s = Server.getInstanceOf();
		Server s2 = Server.getInstanceOf();
		ArrayList<userContact> contacts = new ArrayList<>();
		user user = new user(77,"aaser","yaser","@aaser", contacts);
		user user2 = new user(222,"222","22","@2", contacts);
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
		to2.add(234);
		to2.add(555);
		Queue<Integer> to3 = new LinkedList<>();
		to3.add(234);
		to3.add(555);
		Queue<Integer> to4 = new LinkedList<>();
		to4.add(234);
		to4.add(555);
		Queue<Integer> to5 = new LinkedList<>();
		to5.add(234);
		to5.add(555);
		Queue<Integer> to6 = new LinkedList<>();
		to6.add(887788);
		to6.add(555);
		Queue<Integer> to1 = new LinkedList<>();
		to1.add(234);
		to1.add(555);
		messageMaker maker = new messageMaker();
		////////
		Date now=new Date();
		message n = maker.getNewMessage(1111, "hello", 5,342544, to, "subject", now, 1, new ArrayList<>(Arrays.asList("image.jpf")));
		message n1 = maker.getNewMessage(2222, "hello", 5,342544, to1, "subject", now, 1, new ArrayList<>(Arrays.asList("image.jpf")));
		message n2 = maker.getNewMessage(3333, "hello", 5,342544, to2, "subject", now, 1, new ArrayList<>(Arrays.asList("image.jpf")));
		message n3 = maker.getNewMessage(4444, "hello", 5,342544, to3, "subject", now, 1, new ArrayList<>(Arrays.asList("image.jpf")));
		message n4 = maker.getNewMessage(5555, "hello", 5,342544, to4, "subject", now, 1, new ArrayList<>(Arrays.asList("image.jpf")));
		message n5 = maker.getNewMessage(6666, "hello", 5,342544, to5, "subject", now, 1, new ArrayList<>(Arrays.asList("image.jpf")));
		message n6 = maker.getNewMessage(7777, "hello", 5,342544, to6, "subject", now, 1, new ArrayList<>(Arrays.asList("image.jpf","another.extesnion")));
		message x = maker.getNewMessage(1010, "another message", 15,342544, to2, "TWO", now, 4, new ArrayList<>((Arrays.asList("image.jpf"))));
		
		
		s.sendMessage(n);
		s.sendMessage(n1);
		s.sendMessage(n2);
		s.sendMessage(n3);
		s.sendMessage(n4);
		s.sendMessage(n5);
		s.sendMessage(n6);
		// s.addAttachment(1111);
		System.out.println(s.requestFolder(887788, "inbox","time",1).toString());
		// s.createFolder(555, "tobe");
		// s.moveMessage(555, 10, "inbox","tobe");
		// s.createFolder(234, "mails");
		// s.moveMessage(234, 10, "inbox","mails");
		// s.renameFolder(234, "newName", "mails");
	}

}
