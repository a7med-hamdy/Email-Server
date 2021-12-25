package com.emailserver.email_server.userAndMessage;

import java.time.ZonedDateTime;
import java.util.Date;

import com.fasterxml.jackson.databind.node.BooleanNode;

public class message {
    private int  ID;      
    private messageBody body;      
    private messageHeader header;
    private messageAttachmenets attachments;
    private Date time;
    private int priority;

    public message(int ID, messageBody body, messageHeader header, Date time, int priority,messageAttachmenets attachmenets) {
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

    public Date getTime() {
        return this.time;
    }
    public void setTime(Date date) {
        this.time=date;
    }
    public int isPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public Boolean getDeleted(){
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime thirtyDaysAgo = now.plusDays(-30);

    if (this.time.toInstant().isBefore(thirtyDaysAgo.toInstant())) {
        return true;
    }
    return false;
    }
}
