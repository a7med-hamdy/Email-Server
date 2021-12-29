package com.emailserver.email_server.Server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class bodyCriteria implements Criteria{
       /**
     * take the field to filter 
     * @param path where to search
     * @param body search key word body
     * @return messeages ids
     */
    @Override
    public ArrayList<String> meetCriteria(String path, String body) {
        ArrayList<String> IDs = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(ReaderWriter.readData(path));
        for(int i = 0; i < array.length();i++)
        {
            if(array.getJSONObject(i).optString("body").contains(body))
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
