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

    
    //void setRequestHandler();

}
