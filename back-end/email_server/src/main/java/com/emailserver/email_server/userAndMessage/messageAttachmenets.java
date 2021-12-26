package com.emailserver.email_server.userAndMessage;

import java.io.File;
import java.util.ArrayList;

public class messageAttachmenets {
    private ArrayList<String> attachements = new ArrayList<>();
    private int number = 0;
    public messageAttachmenets(ArrayList<String> arr)
    {
        if(arr!=null){
        for(String file : arr)
            {
                this.attachements.add(file);
                this.number++;
        }
        }
    }

    public void setAttachments(ArrayList<String> arr)
    {
        this.number = 0;
        this.attachements.clear();
        for(String file : arr)
        {
            this.attachements.add(file);
            this.number++;
        }
    }
    public void addAttachment(String f)
    {
        this.attachements.add(f.toString());
    }
    public ArrayList<String> getAttachment()
    {
       return this.attachements;
    }

    public int getNumber()
    {
        return this.number;
    }
}
