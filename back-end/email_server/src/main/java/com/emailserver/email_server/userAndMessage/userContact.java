package com.emailserver.email_server.userAndMessage;

import java.util.ArrayList;

public class userContact implements contact{
   
    private String name;
    private int id;
    private ArrayList<String> email = new ArrayList<>();
    private String userName;

    public userContact(contact contact,String name){
        this.name=name;
        this.id=contact.getID();
        this.email.add(contact.getEmail());
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
         return this.email.toString();
     }

    public void addEmail(String newEmail){
        this.email.add(newEmail);
    } 
    public void removeEmail(String email){
        this.email.remove(email);
    }
    public void editEmail(String oldEmail, String newEmail){
        this.email.remove(oldEmail);
        this.email.add(newEmail);
    }
    public String getUserName() {
        return  this.userName;
     
    }
    
}
