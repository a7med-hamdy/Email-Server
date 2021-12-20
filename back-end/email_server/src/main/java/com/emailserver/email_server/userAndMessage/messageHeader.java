package com.emailserver.email_server.userAndMessage;

import java.util.ArrayList;

public class messageHeader {
    
    private int senderId;
    private ArrayList<Integer> recieverIds ; //fields of messgaes header 
    private String subject;
   
   

/////////////set attribut of message header ///////////////
    public messageHeader(int sender, ArrayList<Integer> reciever, String subject ){
        this.senderId = sender;
        this.recieverIds = reciever;
        this.subject = subject;
       
       
    }

//setters and getters of message header
    public int getSender() {
        return senderId;
    }

    public ArrayList<Integer> getReciever() {
        return recieverIds;
    }

    public void setReciever(ArrayList<Integer> reciever) {
        this.recieverIds = reciever;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



}
