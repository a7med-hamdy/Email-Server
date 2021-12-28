package com.emailserver.email_server.Server;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.emailserver.email_server.userAndMessage.user;
import com.emailserver.email_server.userAndMessage.userContact;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


class contactManager {
    private String path;
    private Gson gson;
    PriorityQueue<userContact> queue;
    protected contactManager(String path)
    {
        this.path = path;
        this.gson = new Gson();
        this.queue = new PriorityQueue<>(new contactComparator());
    }
    private  ArrayList<userContact> sortContacts (ArrayList<userContact> users)
    {
        ArrayList<userContact> sorted = new ArrayList<>();
        for(userContact u: users)
        {
            this.queue.add(u);
        }
        for(int i = 0; i <= this.queue.size();i++)
        {
            sorted.add(this.queue.poll());
        }
        return sorted;
    }
    public String searchContacts(int userID, String keyword)
    {
        JSONArray ret = new JSONArray();
        JSONArray arr = new JSONArray(this.getContacts(userID));
        for(int i = 0; i < arr.length();i++)
        {
            if(arr.getJSONObject(i).optString("name").contains(keyword))
            {
                ret.put(arr.getJSONObject(i));
            }
        }
        return ret.toString();
    }
    public String getContacts(int userID)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        if(index == -1)
        {
            return "fail user not found";
        }
        JSONArray contacts = new JSONArray();
        ArrayList<userContact> sorted = this.sortContacts(users[index].getContacts());
        for(userContact contact : sorted)
        {
            JSONObject temp = new JSONObject(contact);
            contacts.put(temp);
        }
        return contacts.toString();   
    }
    public String addContact(int userID, String email, String name)
    {   
    
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        int index2 = this.findUser(users, email);
  
        if(index == -1 || index2 == -1)
        {     
  
            return "fail user not found";
        }
        userContact contact = new userContact(users[index2], name);
        users[index].addContact(contact);
        String json = this.gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
        return "success";
    }

    public String editContactName(int userID, int contactID, String name)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        if(index == -1)
        {
            return "fail user not found";
        }
        users[index].changeContact(contactID, name);
        String json = this.gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
        return "success";
    }
    public String deleteContact(int userID, int contactID)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        if(index == -1)
        {
            return "fail user not found";
        }
        users[index].deleteContact(contactID);
        String json = this.gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
        return "success";
    }
    public String addContactEmail(int userID, int contactID, String newEmail)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        int index2 = this.findUser(users, newEmail);
        if(index == -1 || index2 == -1)
        {
            return "fail user not found";
        }
        users[index].addContactEmail(contactID, newEmail);
        String json = this.gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
        return "success";
    }
    public String removeContactEmail(int userID, int contactID, String email)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        if(index == -1)
        {
            return "fail user not found";
        }
        users[index].removeContactEmail(contactID, email);
        String json = this.gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
        return "success";
    }
    public String editContactEmail(int userID, int contactID, String oldEmail, String newEmail)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        int index2 = this.findUser(users, newEmail);
        if(index == -1 || index2 == -1)
        {
            return "fail user not found";
        }
        users[index].editContactEmail(contactID, oldEmail, newEmail);;
        String json = gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
        return "success";
    }

    private int findUser(user[] users, int userID)
    {
        int index = 0;
        boolean found = false;
        for(user user: users)
        {
            if(user.getID() == userID)
            {
                found = true;
                break;
            }
            index++;
        }
        return found? index : -1;
    }

    private int findUser(user[] users, String email)
    {
        int index = 0;
        boolean found = false;
        for(user user: users)
        {
            if(user.getEmail().equalsIgnoreCase(email))
            {
                found = true;
                break;
            }
            index++;
        }
        return found? index : -1;
    }

}
