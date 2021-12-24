package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;
import java.text.ParseException;

import com.emailserver.email_server.Controllers.Proxy;
import com.emailserver.email_server.userAndMessage.Server;
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
    private user validateUser(String username, String password){
        Proxy securityProxy = new Proxy(username, password);
        try {
            user c = securityProxy.logIn();
            return c;
        } catch (IOException | ParseException e) {
            return null;
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
        Proxy securityProxy = new Proxy(username, password,email);
        try {
            if(securityProxy.signUp()){
                int newID  =(int)Math.random();
                server.SignUp(newID,username,password, email);
                this.sessionManage.createSession(newID, username, password,email);
                return newID;
            }
            else{
                throw new IOException();
            }
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * log a user in
     * @param username
     * @param password
     * @return
     */
    public int LOGIN(String username, String password)throws IOException{
        user user = this.validateUser(username, password);
        if (user != null){
            if (this.sessionManage.getSessionByUserID(user.getID()) == null){
                this.sessionManage.createSession(user.getID(), username, password,user.getEmail());
                return user.getID();
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
