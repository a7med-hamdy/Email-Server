package com.emailserver.email_server.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.emailserver.email_server.Server;
import com.emailserver.email_server.userAndMessage.contact;
import com.emailserver.email_server.userAndMessage.user;

public class Proxy {
    // private String Email;
    // private int ID;
    private String userName;
	private String password;
	private Server server;
	
	//constructor of proxy class
	public Proxy(String userName,String password) {
		this.userName = userName;
		this.password = password;
	}
	
	
	
	// check if the user exists
	public user logIn() throws FileNotFoundException, IOException, ParseException{
		ArrayList<user> ExistUsers = new ArrayList<user>(){};
		ExistUsers = server.getUsers();
        for(user  user : ExistUsers){
           if(userName.equals(user.getUserName()) && password.equals( user.getPassword() )){
                return user;
            }
        }
        return null;
    }
}