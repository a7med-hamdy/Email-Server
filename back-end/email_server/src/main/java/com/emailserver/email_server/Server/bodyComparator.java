package com.emailserver.email_server.Server;
import java.util.Comparator;
import org.json.JSONObject;



public class bodyComparator implements Comparator<JSONObject>{
    /**
     * make Comparator of messaege body
     * @param o1 the message 1
     * @param o2 the message 2
     * @return 1 if the first is smaller than the seacond -1 else 
     */
    @Override
    public int compare(JSONObject o1, JSONObject o2) {
        JSONObject body1 = o1.getJSONObject("body");
        JSONObject body2 = o2.getJSONObject("body");
        if(Integer.parseInt(body1.optString("length")) < Integer.parseInt(body2.optString("length")))
        {
            return 1;
        }
        else if(Integer.parseInt(body1.optString("length")) > Integer.parseInt(body2.optString("length")))
        {
            return -1;
        }
        return 0;
    }
    
}
