package com.emailserver.email_server.Server;

import java.util.PriorityQueue;

import org.json.JSONArray;
import org.json.JSONObject;


class messageSorter {
    PriorityQueue<JSONObject> queue;
    protected messageSorter(String type)
    {
        if(type.equalsIgnoreCase("priority"))
        {
            queue = new PriorityQueue<>(new priorityComparator());
        }
        else if(type.equalsIgnoreCase("time"))
        {
            queue = new PriorityQueue<>(new messageComparator());
        }
        else
        {
            queue = new PriorityQueue<>(new bodyComparator());
        }
    }

    protected void addToQueue(JSONObject obj)
    {
        this.queue.add(obj);
    }
    protected JSONArray sortMessages(int count)
    {
        JSONArray messages = new JSONArray();
        for(int i = 0; i < (count-1)*5;i++)
        {
            queue.poll();
        }
        int n = queue.size();
        if(n > 5)
        {
            n=5;
        }
        for(int i = 0; i < n;i++)
        {
            messages.put(queue.poll());
        }
        return messages;   
    }
}
