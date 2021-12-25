package com.emailserver.email_server.userAndMessage;

public class userContact{
    // private contact contact;
    private String name;

    public userContact(contact contact,String name){
        // this.contact=contact;
        this.name=name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    // public String getUsername(){
    //     return contact.getUserName();
    // }
    // public int getID(){
    //     return contact.getID();
    // }
    // public String getEmail(){
    //     return contact.getEmail();
    // }
    
}
