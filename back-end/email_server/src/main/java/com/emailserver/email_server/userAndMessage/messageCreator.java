package com.emailserver.email_server.userAndMessage;

import java.util.ArrayList;

public class messageCreator {
    private int id;	
    private String body;
    private int from;
    private ArrayList<Integer> to;
    private String subject;
    private String time;
    private int priority;
    private messageType type;
  
    messageCreator(int id,String body, int from, ArrayList<Integer> to,String subject,String time,int priority,messageType type){
     this.id=id;	
     this.body=body;
     this.from=from;
     this.to=to;
     this.subject=subject;
     this.time=time;
     this.priority=priority;
     this.type=type;
    }

    messageHeader header = new messageHeader(this.from, this.to, this.subject);

    messageBody bodymess = new messageBody(this.body);

    public message getNewMessage(){
        return new message(id, bodymess, header, this.time, this.priority, this.type);
    }


}
