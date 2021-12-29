package com.emailserver.LoginAndSessionManagement;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.emailserver.email_server.Controllers.Proxy;
import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.user;
import com.emailserver.email_server.userAndMessage.userContact;

public class LoggingManager {
    public sessionManager sessionManage = sessionManager.getInstanceOf();
    //
        public LoggingManager(){}

    /**
     * validate the existence of a user
     * @param username user's name
     * @param password user's password
     * @return the user object | null if not found
     * 
     */
    private user validateUser(String username, String password) throws IOException{
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
     * @param username user's name
     * @param email user's email
     * @param password user's password
     * @return user's id after signing up | 0 if the user exists
     */
    public int REGISTER(String username, String email,String password) throws IOException{
        Proxy securityProxy = new Proxy(username, password,email);
        try {
            if(securityProxy.signUp()){
                int min=1,max=1000000000;
                int newID=(int)Math.floor(Math.random()*(max-min+1)+min);                
                System.out.println(newID);
                Server server = Server.getInstanceOf();
                server.SignUp((int) newID,username,password, email, new ArrayList<userContact>());
                this.sessionManage.createSession((int) newID, username, password,email);
                return (int) newID;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    /**
     * log a user in
     * @param username user's name
     * @param password user's password
     * @return user's id
     */
    public int LOGIN(String username, String password)throws IOException{
        user user = this.validateUser(username, password);
        if (user != null){
            if (this.sessionManage.getSessionByUserID(user.getID()) == null){
                this.sessionManage.createSession(user.getID(), username, password,user.getEmail());
                return user.getID();
            }
        }
        return 0;
    }

 
    /**
     * log a user out
     * @param userId user's id
     * @return true if logged out | false if session not found 
     */
    public boolean LOGOUT(int userId){
        if(this.sessionManage.getSessionByUserID(userId) != null){
            this.sessionManage.deleteSession(userId);
            return true;
        }
        return false;
    }
}
