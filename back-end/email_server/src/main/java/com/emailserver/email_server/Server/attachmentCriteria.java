package com.emailserver.email_server.Server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;


public class attachmentCriteria implements Criteria{
      /**
     * search in attachments of messages for key word
     * @param path where to search
     * @param attachment search key word in attachment
     * @return messeages id
     */
    @Override
    public ArrayList<String> meetCriteria(String path, String attachment) {
        ArrayList<String> IDs = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(ReaderWriter.readData(path));
        for(int i = 0; i < array.length();i++)
        {
            JSONArray temp = new JSONArray(array.getJSONObject(i).optString("attachments"));
            for(int j = 0; j < temp.length();j++)
            {
                String content = temp.getString(j).substring(0, temp.getString(j).indexOf('.'));
                if(content.contains(attachment))
                {
                    IDs.add(array.getJSONObject(i).optString("ID"));
                }
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
