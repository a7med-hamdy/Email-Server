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
    /**
     * a helper method that sorts contacts alphabetically
     * @param users array list of the contacts
     * @return sorted array list of the user contacts
     */
    private ArrayList<userContact> sortContacts (ArrayList<userContact> users)
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
    /**
     * a function that searches the contacts of a user for a keyword
     * @param userID the id of the users
     * @param keyword String : the keyword to be searched for
     * @return the String json of the contacts
     */
    protected String searchContacts(int userID, String keyword)
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
    /**
     * a method that returns the contacts of uset
     * @param userID int : the id of the user
     * @return String json of the contacts
     */
    protected String getContacts(int userID)
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
    /**
     * a method that adds a contact
     * @param userID the id of the uset
     * @param email the email of the contact
     * @param name the name of the contact
     * @return
     */
    protected String addContact(int userID, String email, String name)
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
    /**
     * a method that edits contact name 
     * @param userID the user id
     * @param contactID the contact id
     * @param name the name of the contact
     * @return String success or fail
     */
    protected String editContactName(int userID, int contactID, String name)
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
    /**
     * a method that deletes a contact
     * @param userID int : the id of the user
     * @param contactID int : the id of the contact to be deleted
     * @return String success or fail
     */
    protected String deleteContact(int userID, int contactID)
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
    /**
     * a method that add an email to a contact
     * @param userID the id of the user
     * @param contactID the id of the contact
     * @param newEmail the new email
     * @return String success or fail
     */
    protected String addContactEmail(int userID, int contactID, String newEmail)
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
    /**
     * a method that removes an email of a contact
     * @param userID the ID of the user
     * @param contactID the ID of the contact
     * @param email email to be deleted
     * @return String success or fail
     */
    protected String removeContactEmail(int userID, int contactID, String email)
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
    /**
     * a method that edites the email of an contact
     * @param userID the user ID
     * @param contactID the ID of the contact
     * @param oldEmail the old email of the contact
     * @param newEmail the new email of the contact
     * @return String success or fail
     */
    protected String editContactEmail(int userID, int contactID, String oldEmail, String newEmail)
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
    /**
     * a helper method finds a user given the id
     * @param users users array to search for the user in
     * @param userID the id of the user
     * @return the index of the user in the user array
     */
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
    /**
     * a helper method finds a user given the email
     * @param users users array to search for the user in
     * @param email the email of the user
     * @return the index of the user in the user array
     */
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
