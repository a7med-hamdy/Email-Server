package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;
import java.util.ArrayList;

import com.emailserver.email_server.userAndMessage.message;

import org.json.JSONArray;

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

    public void editMessage(int msgID ,String folder,String message)throws IOException;

    public JSONArray getMessages(String folder, String criteria, int count)throws IOException;

    /**CRUD Operations on Contacts */
    public String getContacts()throws IOException;

    public void addContact(String email, String name)throws IOException;

    public void deleteContact(String name)throws IOException;

    public void editContact(String email, String newName, String oldname)throws IOException;
}
