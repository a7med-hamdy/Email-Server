package com.emailserver.LoginAndSessionManagement;

import java.util.List;

public class sessionManager {
    
    List<sessionInterface> sessions;
    //serverDatabase server;
    //sessionManager(serverDatabase serve){
      //  this.server = serve;
    //}
    private static sessionManager instance;    

    private sessionManager(){}
    public static sessionManager getInstanceOf(){
        if(instance == null)
        {   
            instance = new sessionManager();
        }
        return instance;
    }

    public void createSession(int userID, String userName, String password){
        sessionInterface session = new session();
        session.setSessionID((int) Math.random());
        session.setUserId((int)userID);
        session.setUserName(userName);
        session.setUserPassword(password);
        this.sessions.add(session);    
    }

    private Object getSessionByID(int sessionID){
        for(sessionInterface s: this.sessions){
            if(s.getSessionID() == sessionID){
                return s;
            }
        }
        return 0;
    }
    public Object getSessionByUserID(int userID){
        for(sessionInterface s: this.sessions){
            if(s.getUserId() == userID){
                return s;
            }
        }
        return null;
    }

    List<sessionInterface> getSessions(){
           return this.sessions;
    }

    void deleteSession(int userID){
        sessionInterface s =(sessionInterface) this.getSessionByUserID(userID);
        if(s != null)
            this.sessions.remove(s);
    }

    void updateSession(int sessionID, int newuserID, String newuserName, String newpassword){
        
        sessionInterface session = (sessionInterface) this.getSessionByID(sessionID);
        session.setUserId((int)newuserID);
        session.setUserName(newuserName);
        session.setUserPassword(newpassword);    
    };


    
}