package com.emailserver.email_server.Server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class subjectCriteria implements Criteria{
    
    /**
     * take the field to filter 
     * @param path where to search
     * @param subject search key word in subject
     * @return messeages id
     */
    @Override
    public ArrayList<String> meetCriteria(String path, String subject) {
        ArrayList<String> IDs = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(ReaderWriter.readData(path));
        for(int i = 0; i < array.length();i++)
        {
            if(array.getJSONObject(i).optString("subject").contains(subject))
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
