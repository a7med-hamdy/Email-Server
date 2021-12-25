package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;

import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.message;

public class session implements sessionInterface{
    
     protected int sessionID;
     protected int userID;
     protected String userName;
     protected String userPass;
     protected String userEmail;
     private Server server;

     /**Getters and Setters*/
    
     public int getUserId(){return this.userID;}
     public void setUserId(int newID){this.userID=newID;}

     public int getSessionID(){return this.sessionID;}
     public void setSessionID(int newID){this.sessionID=newID;}

     public String getUserName(){return this.userName;}
     public void setUserName(String userName){this.userName = userName;}

     public String getUserPassword(){return this.userPass;}
     public void setUserPassword(String userPass){this.userPass = userPass;}

     public String getUserEmail(){return this.userEmail;}
     public void setUserEmail(String userEmail){this.userEmail = userEmail;}


     /** CRUD Operations on Messages */

     public void addMessage(message message) throws IOException{
          server = Server.getInstanceOf();
          server.sendMessage(message);
     }

     public void moveMessage(int msgID,String source, String folder)throws IOException{
          server = Server.getInstanceOf();
          server.moveMessage(this.getUserId(), msgID, source, folder);
     }

     public void editMessage(int msgID, String folder, String message){

     }

     public void getMessages(String folder){

     }


     /**CRUD Operations on Contacts */

     public void getContacts(String folder){

     }

     public void addContact(String name, String email){

     }
     public void deleteContact(String name){

     }
     public void editContact(String email, String newName, String oldname){
       
     }
}
