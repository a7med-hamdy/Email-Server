package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;


import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.message;

import org.json.JSONArray;

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
     public String[] getEmailFolders()throws IOException{
          server= Server.getInstanceOf();
          return server.getFolders(this.getUserId());
     }

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

     public JSONArray getMessages(String folder, String criteria, int count)throws IOException{
          server = Server.getInstanceOf();
          //System.out.println(server.requestFolder(this.getUserId(), folder, criteria));
          return server.requestFolder(this.getUserId(), folder, criteria, count);
     }


     /**CRUD Operations on Contacts */

     public String getContacts()throws IOException{
          server = Server.getInstanceOf();
          return server.getContacts(this.getUserId());
     }

     public void addContact(String name, String email)throws IOException{

     }
     public void deleteContact(String name)throws IOException{

     }
     public void editContact(String email, String newName, String oldname)throws IOException{
       
     }
     public void addFolder(String name)throws IOException{
          server = Server.getInstanceOf();
          if(name.equalsIgnoreCase("Deleted") || name.equalsIgnoreCase("Draft")
          || name.equalsIgnoreCase("Sent") || name.equalsIgnoreCase("Inbox")){
               ;
          }else{
               server.createFolder(this.getUserId(),name);
          }
          
     }
     public void deleteFolder(String name)throws IOException{
          server = Server.getInstanceOf();
          if(name.equalsIgnoreCase("Deleted") || name.equalsIgnoreCase("Draft")
          || name.equalsIgnoreCase("Sent") || name.equalsIgnoreCase("Inbox")){
               ;
          }else{
               
               server.deleteFolder(this.getUserId(),name);
          }
     }
     public void renameFolder(String oldname,String newName)throws IOException{
          server = Server.getInstanceOf();
          if(oldname.equalsIgnoreCase("Deleted") || oldname.equalsIgnoreCase("Draft")
          || oldname.equalsIgnoreCase("Sent") || oldname.equalsIgnoreCase("Inbox")
          || newName.equalsIgnoreCase("Deleted") || newName.equalsIgnoreCase("Draft")
          || newName.equalsIgnoreCase("Sent") || newName.equalsIgnoreCase("Inbox")
          ){
               ;
          }else{
               server.renameFolder(this.getUserId(), newName, oldname);
          }
     }
}
