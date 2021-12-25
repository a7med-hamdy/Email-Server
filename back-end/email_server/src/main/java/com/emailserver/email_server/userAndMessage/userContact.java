package com.emailserver.email_server.userAndMessage;

public class userContact{
    contact contact;
    String name;

    userContact(contact contact,String name){
        this.contact=contact;
        this.name=name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public String getUsername(){
        return contact.getUserName();
    }
    public int getID(){
        return contact.getID();
    }
    public String getEmail(){
        return contact.getEmail();
    }
    
}
