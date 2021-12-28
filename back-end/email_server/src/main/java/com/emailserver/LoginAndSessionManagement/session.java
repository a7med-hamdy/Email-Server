package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;

import com.emailserver.email_server.Controllers.Proxy;
import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.message;

import org.json.JSONArray;
import org.json.JSONException;

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


     /******************************************************************************************** *****************************/
     /**MAPPING FUNCTION ***/
     /**
      * 
      * @param arr Array to be mapped
      * @return mapped array
      * @throws JSONException
      * @throws IOException
      */
     private JSONArray MapEmails(JSONArray arr) throws JSONException, IOException{
          Proxy proxy = new Proxy(this.getUserName(),this.getUserPassword());

          for(int i = 0; i < arr.length();i++){

              int ID =  Integer.parseInt(arr.getJSONObject(i).getJSONObject("header").optString("senderId"));
              int noOFRecievers = arr.getJSONObject(i).getJSONObject("header").getJSONArray("recieverIds").length();

              arr.getJSONObject(i)
              .getJSONObject("header")
              .put("senderId",proxy.getEmailFromId(ID));

              for(int j = 0; j < noOFRecievers; j++){
               int ID1 =  (arr.getJSONObject(i).getJSONObject("header").getJSONArray("recieverIds").optInt(j));
               
               arr.getJSONObject(i).getJSONObject("header")
               .getJSONArray("recieverIds")
               .put(j, proxy.getEmailFromId(ID1));
              }            
          }
          return arr;
     }

     /****************************Messages************************************************************ */

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

     public JSONArray getMessages(String folder, String criteria, int count)throws IOException{
          server = Server.getInstanceOf();

          //System.out.println(server.requestFolder(this.getUserId(), folder, criteria));
          return this.MapEmails(server.requestFolder(this.getUserId(), folder, criteria, count));
     }

     public JSONArray FilterMessages(String field, String keyword, String sortType, int count)throws IOException{
          server = Server.getInstanceOf();
          return this.MapEmails(server.filterMessages(this.getUserId(), field, keyword, sortType, count));
     }


/******************************************Contacts************************************************************ */

     /**CRUD Operations on Contacts */

     public String getContacts()throws IOException{
          Server server = Server.getInstanceOf();
          return server.getContacts(this.getUserId());
     }

     public void addContact(String email, String name)throws IOException{
          Server server = Server.getInstanceOf();
          server.addContact(this.getUserId(), email, name);

     }
     public void deleteContact(int[] ids)throws IOException{
          Server server = Server.getInstanceOf();
          for (int id : ids){
              
              server.deleteContact(this.getUserId(), id);
          }
     }
     public void editContact(String NewEmails, String oldEmails,String newName, int contactId)throws IOException{
          Server server = Server.getInstanceOf();
          server.editContactEmail(this.getUserId(), contactId, oldEmails, NewEmails);
          server.editContactName(this.getUserId(), contactId, newName);
       
     }


     /*******************************************Folders********************************************************** */

     public String[] getEmailFolders()throws IOException{
          server= Server.getInstanceOf();
          return server.getFolders(this.getUserId());
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
