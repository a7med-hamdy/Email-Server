package com.emailserver.email_server.userAndMessage;


import java.util.Queue;
import java.util.ArrayList;
import java.util.Date;

public class messageMaker {
   

    public messageHeader makeHeader(int from,Queue<Integer> to,String subject){
        return new messageHeader(from, to, subject);
    }
    

    public messageBody makebody(String body, int length){
        return new messageBody(body, length);
    }

    public messageAttachmenets makeAttachmenets(ArrayList<String> files)
    {
        return new messageAttachmenets(files);
    }
  
    public message getNewMessage(int id,String body, int length,int from, Queue<Integer> to,String subject,Date time,int priority, ArrayList<String> files){
        return new message(id, makebody(body,length), makeHeader(from, to,subject), time, priority, makeAttachmenets(files));
    }


}
