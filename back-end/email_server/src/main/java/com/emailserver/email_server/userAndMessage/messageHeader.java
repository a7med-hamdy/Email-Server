package com.emailserver.email_server.userAndMessage;

import java.util.Queue;

public class messageHeader {
    
    private int senderId;
    private Queue<Integer> recieverIds; //fields of messgaes header 
    private String subject;
   
   

/////////////set attribut of message header ///////////////
    public messageHeader(int sender, Queue<Integer> reciever, String subject ){
        this.senderId = sender;
        this.recieverIds = reciever;
        this.subject = subject;
    }

//setters and getters of message header
    public int getSender() {
        return senderId;
    }

    public Queue<Integer> getReceiver() {
        return recieverIds;
    }

    public void setReciever(Queue<Integer> reciever) {
        this.recieverIds = reciever;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



}
