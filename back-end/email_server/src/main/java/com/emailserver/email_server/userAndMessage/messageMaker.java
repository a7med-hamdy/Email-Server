package com.emailserver.email_server.userAndMessage;

import java.util.ArrayList;

public class messageMaker {
    public messageMaker(){}

    public messageHeader makeHeader(int from,ArrayList<Integer> to,String subject){
        return new messageHeader(from, to, subject);
    }
    

    public messageBody makebody(String body){
        return new messageBody(body);
    }
  
    public message getNewMessage(int id,String body, int from, ArrayList<Integer> to,String subject,String time,int priority){
        return new message(id, makebody(body), makeHeader(from, to,subject), time, priority);
    }


}
