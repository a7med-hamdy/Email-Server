package com.emailserver.email_server.Server;

import java.util.Comparator;

import org.json.JSONObject;

public class subjectComparator implements Comparator<JSONObject> {

    @Override
    public int compare(JSONObject o1, JSONObject o2) {
        return (o1.optString("subject").compareTo(o2.optString("subject")));
    }
    
}
