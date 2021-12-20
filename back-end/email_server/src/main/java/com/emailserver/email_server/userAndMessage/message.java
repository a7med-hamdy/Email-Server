package com.emailserver.email_server.userAndMessage;

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

    public int getID() {
        return ID;
    }
    public messageBody getBody() {
        return body;
    }
    public messageHeader getHeader() {
        return header;
    }

    public String getTime() {
        return time;
    }

    public int isPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setType(messageType type) {
        this.type = type;
    }
    public String getType() {
        return this.type.getmessageType();
    }

}
