package com.emailserver.LoginAndSessionManagement;

import java.util.ArrayList;
import java.util.List;

public class sessionManager {
    
    private List<sessionInterface> sessions = new ArrayList<sessionInterface>();
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

    public void createSession(int userID, String userName, String password,String userEmail){
        sessionInterface session = new session();
        int min=1,max=1000000000;
        int newID=(int)Math.floor(Math.random()*(max-min+1)+min);         
        session.setSessionID((newID));
        session.setUserId((int)userID);
        session.setUserName(userName);
        session.setUserPassword(password);
        session.setUserEmail(userEmail);
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
        
        if(!this.sessions.isEmpty()){
            try{
            for(sessionInterface s: this.sessions){
                if(s.getUserId() == userID){
                    return s;
                }
            }
            }
            catch(Exception e){
                return null;
            }
        }
        
            return null;
    }

    public List<sessionInterface> getSessions(){
           return this.sessions;
    }

    public void deleteSession(int userID){
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
