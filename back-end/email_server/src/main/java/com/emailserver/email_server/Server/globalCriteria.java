package com.emailserver.email_server.Server;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

class globalCriteria implements Criteria{
    private Criteria bodyCriteria;
    private Criteria attachmentCriteria;
    private Criteria senderCriteria;
    private Criteria receiverCriteria;
    private Criteria subjectCriteria;

    protected globalCriteria(Criteria bodyCriteria, Criteria attachmentCriteria, Criteria senderCriteria, Criteria receiverCriteria, Criteria subjectCriteria)
    {
        this.bodyCriteria = bodyCriteria;
        this.attachmentCriteria = attachmentCriteria;
        this.senderCriteria = senderCriteria;
        this.receiverCriteria = receiverCriteria;
        this.subjectCriteria = subjectCriteria;
    }
    @Override
    public ArrayList<String> meetCriteria(String path, String criteria) {
        Set<String> set = new LinkedHashSet<>();
        set.addAll(this.bodyCriteria.meetCriteria(path, criteria));
        set.addAll(this.attachmentCriteria.meetCriteria(path, criteria));
        set.addAll(this.senderCriteria.meetCriteria(path, criteria));
        set.addAll(this.receiverCriteria.meetCriteria(path, criteria));
        set.addAll(this.subjectCriteria.meetCriteria(path, criteria));
        ArrayList<String> ret = new ArrayList<>();
        ret.addAll(set);
        return ret;
    }
    
}
