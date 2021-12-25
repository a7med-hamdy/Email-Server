package com.emailserver.email_server.Server;

import com.emailserver.email_server.userAndMessage.user;
import com.emailserver.email_server.userAndMessage.userContact;
import com.google.gson.Gson;


class contactManager {
    private String path;
    private Gson gson;
    public contactManager(String path)
    {
        this.path = path;
        gson = new Gson();
    }

    public void addContact(int userID, userContact contact)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        users[index].addContact(contact);
        String json = gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
    }

    public void editContactName(int userID, int contactID, String name)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        users[index].changeContact(contactID, name);
        String json = gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
    }
    public void deleteContact(int userID, int contactID)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        users[index].deleteContact(contactID);
        String json = gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
    }
    public void addContactEmail(int userID, int contactID, String newEmail)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        users[index].addContactEmail(contactID, newEmail);
        String json = gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
    }
    public void removeContactEmail(int userID, int contactID, String email)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        users[index].removeContactEmail(contactID, email);
        String json = gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
    }
    public void editContactEmail(int userID, int contactID, String oldEmail, String newEmail)
    {
        String content = ReaderWriter.readData(this.path);
        user[] users = this.gson.fromJson(content, user[].class);
        int index = this.findUser(users, userID);
        users[index].editContactEmail(contactID, oldEmail, newEmail);;
        String json = gson.toJson(users);
        ReaderWriter.writeData(this.path, json);
    }

    private int findUser(user[] users, int userID)
    {
        int index = 0;
        for(user user: users)
        {
            if(user.getID() == userID)
            {
                break;
            }
            index++;
        }
        return index;
    }

}
