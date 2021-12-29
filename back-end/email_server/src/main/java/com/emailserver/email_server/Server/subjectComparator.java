package com.emailserver.email_server.Server;

import java.util.Comparator;

import org.json.JSONObject;

public class subjectComparator implements Comparator<JSONObject> {
     /**
     * compare between message subject
     * @param o1 the first message
     * @param o2 the second message
     * @return 1 if subject1 is first in order ,0 if equal, -1 if less
     */
    @Override
    public int compare(JSONObject o1, JSONObject o2) {
        return (o1.optString("subject").compareTo(o2.optString("subject")));
    }
    
}
