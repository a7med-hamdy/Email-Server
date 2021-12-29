package com.emailserver.LoginAndSessionManagement;

import java.util.ArrayList;
import java.util.List;

public class sessionManager {
    
    private List<sessionInterface> sessions = new ArrayList<sessionInterface>();

    private static sessionManager instance;    

    private sessionManager(){}
    public static sessionManager getInstanceOf(){
        if(instance == null)
        {   
            instance = new sessionManager();
        }
        return instance;
    }
    /**
     * Create a session for the user after logging in 
     * 
     * @param userID user's id
     * @param userName user's name 
     * @param password user's password
     * @param userEmail user's email
     */
    public void createSession(int userID, String userName, String password,String userEmail)throws ArrayIndexOutOfBoundsException{
        if(this.getSessions().size() <=1000){
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
        else {throw new ArrayIndexOutOfBoundsException();}
    }

    /**
     * search for the session that contains user's id
     * 
     * @param userID user's id
     * @return session of that user | null if not found
     */
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

    /**
     * get all active sessions
     * @return list of active sessions
     */
    public List<sessionInterface> getSessions(){
           return this.sessions;
    }
    /**
     * destroy a session on logout
     * @param userID id of the logged out user
     */
    public void deleteSession(int userID){
        sessionInterface s =(sessionInterface) this.getSessionByUserID(userID);
        if(s != null)
            this.sessions.remove(s);
    }

    
}
