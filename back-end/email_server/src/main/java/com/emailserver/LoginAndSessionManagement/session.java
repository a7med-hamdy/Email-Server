package com.emailserver.LoginAndSessionManagement;
public class session implements sessionInterface{
    
    protected int sessionID;
    protected int userID;
    protected String userName;
    protected String userPass;
    //private server server = server.getInstanceOf();
    //protected requestHandler req;


    //getters and setters
    public int getUserId(){return this.userID;}
    public void setUserId(int newID){this.userID=newID;}

    public int getSessionID(){return this.sessionID;}
    public void setSessionID(int newID){this.sessionID=newID;}

    public String getUserName(){return this.userName;}
    public void setUserName(String userName){this.userName = userName;}

    public String getUserPassword(){return this.userPass;}
    public void setUserPassword(String userPass){this.userPass = userPass;}


   public void addUserMessage(String message){
        //
   }

   public void deleteUserMessage(String message){

   }

   public void editUserMessage(int msgID ,String message){

   }

   public void getUserMessage(int msgID ,String message){
        
   }
}
