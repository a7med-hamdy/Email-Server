package com.emailserver.email_server.Server;

import java.util.Comparator;

import org.json.JSONObject;

public class messageComparator implements Comparator<JSONObject> {

    /**
     * compare between data of messages
     * @param o1 message 1
     * @param o2 message 1
     * @return 1 if data of message 1 is older than message 2  ,0 if equal ,  -1 other
     */
    @Override
    public int compare(JSONObject o1, JSONObject o2) {
        if(Long.parseLong(o1.optString("time")) < Long.parseLong(o2.optString("time")))
        {
            return 1;
        }
        else if(Long.parseLong(o1.optString("time")) > Long.parseLong(o2.optString("time")))
        {
            return -1;
        }
        return 0;
    }
    
}
