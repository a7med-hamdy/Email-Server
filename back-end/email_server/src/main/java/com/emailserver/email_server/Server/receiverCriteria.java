package com.emailserver.email_server.Server;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

public class receiverCriteria implements Criteria {

    /**
     *serche for keyword in  messages receiver
     * @param path where to search
     * @param receiver search key word in receiver
     * @return messeages id
     */
    @Override
    public ArrayList<String> meetCriteria(String path, String receiver) {
        ArrayList<String> IDs = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(ReaderWriter.readData(path));
        for(int i = 0; i < array.length();i++)
        {
            JSONArray temp = new JSONArray(array.getJSONObject(i).optString("receivers"));
            for(int j = 0; j < temp.length();j++)
            {
                if((int)temp.get(j) == Integer.parseInt(receiver))
                {
                    IDs.add(array.getJSONObject(i).optString("ID"));
                }
            }
        }
        }
        catch(JSONException | NumberFormatException e)
        {
            // System.out.println("error creating json array in subject criteria");
            return IDs;
        }
        return IDs;
    }
    
}
