package com.emailserver.email_server.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.emailserver.email_server.userAndMessage.contact;
// import com.emailserver.email_server.userAndMessage.user;

public class Proxy {
    // private String Email;
    // private int ID;
    private String userName;
	private String password;
	// private user userClass;
	
	//constructor of proxy class
	public Proxy(String userName,String password) {
		this.userName = userName;
		this.password = password;
	}
	
	
	
	// check if the user exists
	public contact logIn() throws FileNotFoundException, IOException, ParseException{
		ArrayList<contact> ExistUsers = new ArrayList<contact>(){};
		//get the users in 'ExistUsers' here <----------------
        for(contact  user : ExistUsers){
           if(userName.equals((String) user.getUserName()) && password.equals( ""/* (String) user.getPassword() */ )){
                   return user;
            }
        }
        return null;
    
    }
}