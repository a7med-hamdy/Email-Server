package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;

import com.emailserver.email_server.userAndMessage.message;

public interface sessionInterface {

    /**Getters and setters */
    public int getUserId();
    public void setUserId(int id);

    public int getSessionID();
    public void setSessionID(int id);

    public String getUserName();
    public void setUserName(String userName);

    public String getUserPassword();
    public void setUserPassword(String userPassword);
    
    public String getUserEmail();
    public void setUserEmail(String userEmail);
    

    /**CRUD Operations on messages */
    public void addMessage(message message) throws IOException;

    public void moveMessage(int msgID,String source, String folder)throws IOException;

    public void editMessage(int msgID ,String folder,String message);

    public void getMessages(String folder);

    /**CRUD Operations on Contacts */
    public void getContacts(String folder);

    public void addContact(String email, String name);

    public void deleteContact(String name);

    public void editContact(String email, String newName, String oldname);
}
