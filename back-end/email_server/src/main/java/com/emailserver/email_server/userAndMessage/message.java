package com.emailserver.email_server.userAndMessage;

import java.util.ArrayList;

public class message {
    private int  ID;      
    private messageBody body;      
    private messageHeader header;
    private String time;
    private messageType type;
    private int priority;


    public message(int ID, messageBody body, messageHeader header, String time, int priority ,messageType type) {
        this.ID = ID;
        this.body = body;
        this.header = header;
        this.time = time;
        this.priority = priority;
        this.type=type;
    }

    protected int getSender()
    {
        return this.header.getSender();
    }
    protected ArrayList<Integer> getReceivers()
    {
        return this.header.getReciever();
    }
    public int getID() {
        return this.ID;
    }
    public String getBody() {
        return this.body.getBody();
    }
    public messageHeader getHeader() {
        return this.header;
    }

    public String getTime() {
        return this.time;
    }

    public int isPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setType(messageType type) {
        this.type = type;
    }

}
