package com.emailserver.email_server.Server;

import java.util.Comparator;

import org.json.JSONObject;

public class priorityComparator implements Comparator<JSONObject>{
 /**
     * compare betwwen message priority
     * @param o1 the message 1
     * @param o2 the message 2
     * @return 1 if the first less pirior, -1 else , 0 if equal
     */
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
