package com.emailserver.email_server.Server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class senderCriteria implements Criteria{
      /**
     * serche for keyword in  messages sender
     * @param path where to search
     * @param sender search key word in sender
     * @return messeages id
     */
    @Override
    public ArrayList<String> meetCriteria(String path, String sender) {
        ArrayList<String> IDs = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(ReaderWriter.readData(path));
        for(int i = 0; i < array.length();i++)
        {
            if(array.getJSONObject(i).optString("sender").equalsIgnoreCase(sender))
            {
                IDs.add(array.getJSONObject(i).optString("ID"));
            }
        }
        }
        catch(JSONException e)
        {
            // System.out.println("error creating json array in subject criteria");
            return IDs;
        }
        return IDs;
    }
    
}
