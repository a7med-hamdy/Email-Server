package com.emailserver.email_server.userAndMessage;

import java.util.Comparator;

import org.json.JSONObject;

public class priorityComparator implements Comparator<JSONObject>{

    @Override
    public int compare(JSONObject o1, JSONObject o2) {
        if(Integer.parseInt(o1.optString("priority")) < Integer.parseInt(o2.optString("priority")))
        {
            return 1;
        }
        else if(Integer.parseInt(o1.optString("priority")) > Integer.parseInt(o2.optString("priority")))
        {
            return -1;
        }
        return 0;
    }
    
}
