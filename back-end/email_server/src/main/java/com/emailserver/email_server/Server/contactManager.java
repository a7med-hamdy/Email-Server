package com.emailserver.email_server.Server;

import com.emailserver.email_server.userAndMessage.user;
import com.emailserver.email_server.userAndMessage.userContact;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


class contactManager {
    private String path;
    private Gson gson;
    public contactManager(String path)
    {
        this.path = path;
        gson = new Gson();
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
        for(userContact contact :users[index].getContacts())
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
        String json = gson.toJson(users);
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
        String json = gson.toJson(users);
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
        String json = gson.toJson(users);
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
        String json = gson.toJson(users);
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
        String json = gson.toJson(users);
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
