package com.emailserver.email_server.userAndMessage;
import java.util.ArrayList;

public class user implements contact{
    private int Id;
    private String userName;
    private String password;
    private String email;
    private ArrayList<userContact> Contacts;
   
////////////////////set attribute of user ///////////////////////
    public user(int Id,String userName, String password, String email, ArrayList<userContact> contacts) {
        this.Id=Id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.Contacts = contacts;
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

    public void setcontact(userContact contact){
        this.Contacts.add(contact);
    }
    public ArrayList<userContact> getContacts(){
        return this.Contacts;
    }
    public void deleteContact(int id){
        for (userContact element : Contacts){
            if (element.getID()==id){
                Contacts.remove(element);
                break;
            }
        }
    }
    public void changeContact(int id,String name){
        for (userContact element : Contacts){
            if (element.getID()==id){
                element.setName(name);
                break;
            }
        }
    }
    public void addContact(userContact contact)
    {
        this.Contacts.add(contact);
    }
    public void addContactEmail(int id, String newEmail)
    {
        for (userContact element : Contacts){
            if (element.getID()==id){
                element.addEmail(newEmail);
                break;
            }
        }
    }
    public void removeContactEmail(int id, String email)
    {
        for (userContact element : Contacts){
            if (element.getID()==id){
                element.removeEmail(email);
                break;
            }
        }
    }
    public void editContactEmail(int id, String oldEmail, String newEmail)
    {
        for (userContact element : Contacts){
            if (element.getID()==id){
                element.editEmail(oldEmail, newEmail);
                break;
            }
        }
    }
}
