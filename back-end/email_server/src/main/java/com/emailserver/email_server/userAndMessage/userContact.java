package com.emailserver.email_server.userAndMessage;

public class userContact implements contact{
   
    private String name;
    private int id;
    private String email;
    private String userName;

    public userContact(contact contact,String name){
     
        this.name=name;
        this.id=contact.getID();
        this.email=contact.getEmail();
        this.userName=contact.getUserName();
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
     public int getID(){
         return  this.id;
     }
     public String getEmail(){
         return this.email;
     }

    @Override
    public String getUserName() {
        return  this.userName;
     
    }
    
}
