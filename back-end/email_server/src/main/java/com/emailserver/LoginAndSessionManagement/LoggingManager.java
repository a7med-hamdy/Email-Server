package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;
import java.text.ParseException;

import com.emailserver.email_server.Server;
import com.emailserver.email_server.Controllers.Proxy;
import com.emailserver.email_server.userAndMessage.user;

public class LoggingManager {
    public sessionManager sessionManage = sessionManager.getInstanceOf();
    //
    private Server server;
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
            user c = securityProxy.logIn();
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
     * @throws IOException
     */
    public int REGISTER(String username, String email,String password) throws IOException{
        int userId = this.validateUser(username,password);
        if(userId == 0){
            server.SignUp((int)Math.random(),username,password, email);
            this.sessionManage.createSession(userId, username, password);
            return userId;
        }
        else{
            throw new IOException();
        }
    }

    /**
     * log a user in
     * @param username
     * @param password
     * @return
     */
    public int LOGIN(String username, String password)throws IOException{
        int userId = this.validateUser(username, password);
        if (userId != 0){
            if (this.sessionManage.getSessionByUserID(userId) == null){
                this.sessionManage.createSession(userId, username, password);
                return userId;
            }
        }
        else{
            throw new IOException();
        }
        return 0;
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
