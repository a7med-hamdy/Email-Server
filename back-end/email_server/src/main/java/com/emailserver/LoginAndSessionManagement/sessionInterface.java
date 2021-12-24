package com.emailserver.LoginAndSessionManagement;

public interface sessionInterface {
    
    int getUserId();
    void setUserId(int id);

    int getSessionID();
    void setSessionID(int id);

    String getUserName();
    void setUserName(String userName);

    String getUserPassword();
    void setUserPassword(String userPassword);

    
   public void addUserMessage(String message);

public void deleteUserMessage(String message);


public void editUserMessage(int msgID ,String message);


public void getUserMessage(int msgID ,String message);

public void getUserContacts(int id);


public void addUserContacts(int id);

public void deleteUserContact(int id);

public void editUserContact(int id);
}
