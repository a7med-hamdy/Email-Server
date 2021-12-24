package com.emailserver.email_server.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.emailserver.email_server.userAndMessage.Server;
import com.emailserver.email_server.userAndMessage.user;

public class Proxy {
	
    private String userName;
	private String password;
	private String email;

	private Server server;
	
	//constructor of proxy class
	public Proxy(String userName,String password, String... email) {
		this.userName = userName;
		this.password = password;
		this.email = email[0];
	}
	
	/**
	 * Prevents the new user from using another user's attributes (Username, Password, or Email)
	 * @return true if the new user has unique attributes, and false otherwisw
	 */
	public boolean signUp() throws FileNotFoundException, IOException, ParseException{
		ArrayList<user> ExistUsers = new ArrayList<user>(){};
		ExistUsers = server.getUsers();
		// boolean[] sameIn = {false, false, false}; //{username, Email, Password}
		for(user  user : ExistUsers){
			if(userName.equals(user.getUserName()) || email.equals( user.getEmail() ) || password.equals( user.getPassword() )){
				 return false;
			}
			/* 
			if(userName.equals(user.getUserName()))
				sameIn[0] = true;
			else if(email.equals( user.getEmail() ))
				sameIn[1] = true;
			else if(password.equals( user.getPassword() ))
				sameIn[2] = true;
			 */
		}
		// return sameIn;
		return true;
	}
	
	/**
	 * prevents the new user from logging in before registration
	 * @return the user if found , and null otherwise
	 */
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