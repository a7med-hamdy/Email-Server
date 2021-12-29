package com.emailserver.email_server.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.user;

public class Proxy {
	
    private String userName;
	private String password;
	private String email;

	private Server server = Server.getInstanceOf();
	
	//constructors of proxy class
	public Proxy(String userName,String password) throws IOException {
		this.userName = userName;
		this.password = password;
	}
	public Proxy(String userName,String password, String email) throws IOException {
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	/**
	 * Getting the user's email from knowing the id
	 * @param id
	 * @return the email of that id
	 * @throws IOException
	 */
	public String getEmailFromId(int id) throws IOException{
		ArrayList<user> ExistUsers = new ArrayList<user>(){};
		ExistUsers = server.getUsers();
		for(user  user : ExistUsers){
			if(user.getID() == id){
				return user.getEmail();
			}
		}
		return "";
	}

	/**
	 * Getting the user's id from knowing the email
	 * @param email
	 * @return the id of that email
	 * @throws IOException
	 */
	public int getIdFromEmail(String email) throws IOException{
		ArrayList<user> ExistUsers = new ArrayList<user>(){};
		ExistUsers = server.getUsers();
		for(user  user : ExistUsers){
			if(user.getEmail().equals(email)){
				return user.getID();
			}
		}
		return 0;
	}

	/**
	 * Getting the users's ids who will receive the message in a Queue
	 * @param to
	 * @return a Queue of receivers' ids
	 * @throws IOException
	 */
	public Queue<Integer> getReceiversIds(String[] to) throws IOException{
		Queue<Integer> Ids = new LinkedList<>(); 
		ArrayList<user> ExistUsers = new ArrayList<user>(){};
		ExistUsers = server.getUsers();
        for(user  user : ExistUsers){
			for(String To: to){
				if(To.equals(user.getEmail())){
					Ids.add(user.getID());
					break;
				}
			}
        }
        return Ids;
	}
	
	/**
	 * Prevents the new user from using another user's attributes (Username, Password, or Email)
	 * @return true if the new user has unique attributes, and false otherwise
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public boolean signUp() throws FileNotFoundException, IOException, ParseException{
		ArrayList<user> ExistUsers = new ArrayList<user>(){};
		ExistUsers = server.getUsers();
		for(user  user : ExistUsers){
			if(userName.equals(user.getUserName()) || email.equals( user.getEmail() ) || password.equals( user.getPassword() )){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * prevents the new user from logging in before registration
	 * @return the user if found , and null otherwise
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
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