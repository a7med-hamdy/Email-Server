package com.emailserver.email_server.userAndMessage;

import java.util.ArrayList;

public class user implements contact{
    private int Id;
    private String userName;
    private String password;
    private String email;
    private ArrayList<userContact> Contacts;
    private ArrayList<message> messages;
   
////////////////////set attribute of user ///////////////////////
    public user(int Id,String userName, String password, String email) {
        this.Id=Id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
    
    //////////setters and getters of user///////////////////
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public int getID() {
        return this.Id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<message> getMessages(){
        return this.messages;
    }

    public void setcontact(userContact contact){
        this.Contacts.add(contact);
    }
    public ArrayList<userContact> getContact(){
        return Contacts;
    }
    public void deleteContact(int id){
        for (userContact element : Contacts){
            if (element.getID()==id){
                Contacts.remove(element);
            }
        }
    }
    public void changeContact(int id,String name){
        for (userContact element : Contacts){
            if (element.getID()==id){
                element.setName(name);
            }
        }
    }


//////////message operatoins of user///////////////////
    public void addmessage(message messa) {
        this.messages.add(messa);
    }
    
    // public void changeMessage(int id,String x) {
    //     for(message i : messages ){
    //         if(i.getID()==id){
    //             if(x.equalsIgnoreCase("sent")){
    //                 i.setType(new sent());
    //             }else if(x.equalsIgnoreCase("trash")){
    //                 i.setType(new trash());
    //             }
    //             else if(x.equalsIgnoreCase("draft")){
    //                 i.setType(new draft());
    //             }
    //             else{
    //                 i.setType(new inbox());
    //             }
    //         }
    //     }
    // }

   
 
}
