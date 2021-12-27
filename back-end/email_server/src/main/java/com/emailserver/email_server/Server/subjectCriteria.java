package com.emailserver.email_server.Server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class subjectCriteria implements Criteria{

    @Override
    public ArrayList<String> meetCriteria(String path, String subject) {
        ArrayList<String> IDs = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(ReaderWriter.readData(path));
        for(int i = 0; i < array.length();i++)
        {
            if(array.getJSONObject(i).optString("subject").equalsIgnoreCase(subject))
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
