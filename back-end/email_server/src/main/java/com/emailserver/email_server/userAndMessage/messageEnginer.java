package com.emailserver.email_server.userAndMessage;

public class messageEnginer {
    messageBuldier messageBuldier;
    messageEnginer(messageBuldier messageBuldier){
        this.messageBuldier=messageBuldier;
    }

    public message makeMessage(){
        this.messageBuldier.makeHeader();
        this.messageBuldier.makebody();
        return this.messageBuldier.getNewMessage();
    }
}
