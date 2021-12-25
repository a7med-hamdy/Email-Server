package com.emailserver.email_server.userAndMessage;

public class message {
    private int  ID;      
    private messageBody body;      
    private messageHeader header;
    private messageAttachmenets attachments;
    private String time;
    private int priority;

    public message(int ID, messageBody body, messageHeader header, String time, int priority,messageAttachmenets attachmenets) {
        this.ID = ID;
        this.body = body;
        this.header = header;
        this.time = time;
        this.priority = priority;
        this.attachments = attachmenets;
    }

    public int getID() {
        return this.ID;
    }
    public messageBody getBody(){
        return this.body;
    }
    public messageHeader getHeader() {
        return this.header;
    }
    public messageAttachmenets getAttachments(){
        return this.attachments;
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
}
