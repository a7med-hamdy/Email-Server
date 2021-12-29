package com.emailserver.email_server.Server;

import java.util.PriorityQueue;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * a class that sorts the given messages according to the given criteria using priority queue
 */
class messageSorter {
    PriorityQueue<JSONObject> queue;
    // create the queue with the desired criteria priority/body length/ time / subject
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
        else if(type.equalsIgnoreCase("body"))
        {
            queue = new PriorityQueue<>(new bodyComparator());
        }
        else
        {
            queue = new PriorityQueue<>(new subjectComparator());
        }
    }
    /**
     * a method that adds the objects to the queue
     * @param obj the JSONObject to be added
     */
    protected void addToQueue(JSONObject obj)
    {
        this.queue.add(obj);
    }
    /**
     * a function that returns the desired page of messages from the queue
     * @param count the desired page
     * @return JSONArray of the desired page of messages from the queue
     */
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
