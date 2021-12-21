package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;
import java.text.ParseException;

import com.emailserver.email_server.Controllers.Proxy;
import com.emailserver.email_server.userAndMessage.contact;

public class LoggingManager {
    public sessionManager sessionManage = sessionManager.getInstanceOf();
    //
    //public server
    public LoggingManager(){}

    /**
     * validate the existence of a user
     * @param username
     * @param password
     * @return
     */
    private int validateUser(String username, String password){
        Proxy securityProxy = new Proxy(username, password);
        try {
            contact c = securityProxy.logIn();
            return c.getID();
        } catch (IOException | ParseException e) {
            return 0;
        }
        /**fetch userbyid from server */
    }

    /**
     * register users
     * @param username
     * @param password
     */
    public void REGISTER(String username, String password){
        int userId = this.validateUser(username,password);
        if(userId == 0){
            /**server.createUser(username,password) */
            this.sessionManage.createSession(userId, username, password);
            //return contact;
        }

    }

    /**
     * log a user in
     * @param username
     * @param password
     * @return
     */
    public boolean LOGIN(String username, String password){
        int userId = this.validateUser(username, password);
        if (userId != 0){
            if (this.sessionManage.getSessionByUserID(userId) == null){
                this.sessionManage.createSession(userId, username, password);
                return true;
            }
        }
        return false;
    }

 
    /**
     * log a user out
     * @param userId
     * @return
     */
    public boolean LOGOUT(int userId){
        if(this.sessionManage.getSessionByUserID(userId) != null){
            this.sessionManage.deleteSession(userId);
            return true;
        }
        return false;
    }
}
