package com.emailserver.email_server.Server;

import java.util.Comparator;

import org.json.JSONObject;

public class messageComparator implements Comparator<JSONObject> {


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
