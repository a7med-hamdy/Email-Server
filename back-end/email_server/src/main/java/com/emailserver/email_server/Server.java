package com.emailserver.email_server;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.user;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

//don't forget to create the Data directory because it might not be there on other devices
public class Server {
    private String path = "src\\Data";
    private PriorityQueue<message> queue = new PriorityQueue<>();
    private ArrayList<String> folders = new ArrayList<>(Arrays.asList("inbox","trash","sent","draft"));
    private JSONArray arr = new JSONArray();
    private static Server instance;  
    private Gson gson;
    private File current_users;

    private Server() throws IOException{
        File f = new File(this.path);
        f.mkdir();
        this.path += "\\";
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.current_users = new File(path+"currentUsers.json");
        this.current_users.createNewFile();
    }

    public static Server getInstanceOf() throws IOException{
        if(instance == null)
        {
            return new Server();
        }
        return instance;
    }

    public void SignUp(int id, String username, String password, String email) throws IOException{    
        user new_user = new user(id,username,password,email);
        String json = gson.toJson(new_user);
        JSONObject temp = new JSONObject(json);
        this.arr.put(temp);
        this.writeData();
        File f = new File(this.path+id);
        f.mkdir();
        for(String folder : this.folders)
        {
            f = new File(this.path+id+"\\"+folder);
            f.mkdir();
            f = new File(this.path+id+"\\"+folder+"\\index.json");
            f.createNewFile();
        }
    }

    public ArrayList<user> getUsers() throws IOException
    {
        String content = new Scanner(this.current_users).useDelimiter("\\Z").next();
        user[] users = gson.fromJson(content, user[].class);
        JSONArray temp = new JSONArray(content);
        this.arr.clear();
        this.arr.putAll(temp);
        return new ArrayList<user>(Arrays.asList(users));
    }

    public void sendMessage(message m) 
    {
        String json = gson.toJson(m);
        String path = this.path+m.getSender()+"\\sent\\";
        JSONObject js = new JSONObject(m);
        this.addToIndex(path,js);
        path += + m.getID();
        File f = new File(path);
        f.mkdir();
        this.writeData(path +"\\"+m.getID()+".json", json);
        ArrayList<Integer> receivers = m.getReceivers();
        for(int r : receivers)
        {
            path = this.path+r+"\\inbox\\";
            this.addToIndex(path,js);
            path += m.getID();
            File f2 = new File(path);
            f2.mkdir();
            this.writeData(path+"\\"+m.getID()+".json", json);
        }
    }

    // public JSONObject getMessage(String userID, String messageID)
    // {

    // }

    public void moveMessage(int userID, int messageID, String src, String dst)
    {
        String source = this.path+userID+"\\"+src+"\\";
        JSONObject json = this.findMessage(source, messageID, true);
        this.removeFromIndex(source, messageID);
        source += "\\"+messageID;
        File sourceFile = new File(source);
        String destination = this.path+userID+"\\"+dst+"\\";
        this.addToIndex(destination, json);
        destination += messageID;
        File destinationFile = new File(destination);
        try {
            FileUtils.moveDirectory(sourceFile, destinationFile);
        } catch (IOException e) {
            System.out.println("Folder is already there.");
        }
    }

    public void createFolder(int userID, String name)
    {
        File f = new File(this.path+userID+"\\"+name);
        f.mkdir();
        f = new File(this.path+userID+"\\"+name+"\\index.json");
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("Folder creation failure");
        }
        this.folders.add(name);
    }
    public void renameFolder(int userID, String newName, String oldName)
    {
        File f = new File(this.path+userID+"\\"+oldName);
        if(f.isDirectory())
        {
            f.renameTo(new File(this.path+userID+"\\"+newName));
        }
        this.folders.remove(oldName);
        this.folders.add(newName);
    }

    public void deleteFolder(int userID, String name)
    {
        File f = new File(this.path+userID+"\\"+name);
        if(f.isDirectory())
        {
            try {
                FileUtils.deleteDirectory(f);
            } catch (IOException e) {
                System.out.println("Error deleting folder");
            }
        }
        this.folders.remove(name);
    }

    private void removeFromIndex(String path, int id)
    {
        JSONObject temp = this.findMessage(path, id, false);
        int index = (int)temp.opt("index");
        String content = this.readData(path+"index.json");
        JSONArray messages;
        if(content.isEmpty())
        {
            messages = new JSONArray();
        }
        else
        {
            messages = new JSONArray(content);
            messages.remove(index);
        }
        this.writeData(path+"index.json", messages.toString());
    }

    private void addToIndex(String path, JSONObject m)
    {
        String content = this.readData(path+"index.json");
        JSONArray messages;
        if(content.isEmpty())
        {
            messages = new JSONArray();
        }
        else
        {
            messages = new JSONArray(content);
        }
        messages.put(m);
        this.writeData(path+"index.json", messages.toString());
    }

    private JSONObject findMessage(String path, int id, boolean flag)
    {
        String content = this.readData(path+"index.json");
        JSONArray messages = new JSONArray(content);
        for(int i = 0; i < messages.length();i++)
        {
            JSONObject temp = messages.getJSONObject(i);
            if(temp.optString("ID").equals(Integer.toString(id)))
            {
                return flag ? temp : new JSONObject().put("index", i);
            }
        }
        return new JSONObject();
    }



    private String readData(String path)
    {
        File index = new File(path);
        
        String content = "";
        try {
            Scanner sc = new Scanner(index).useDelimiter("\\Z");
            if(sc.hasNext())
            {
                content = sc.next();
            }
            sc.close();
        } catch (FileNotFoundException | NoSuchElementException e) {
            System.out.println("ERROR reading the index file.");
        }
        return content;
    }
    private void writeData() throws IOException
    {
        FileWriter myWriter = new FileWriter(this.current_users);
        myWriter.write(this.arr.toString());
        myWriter.close();
    }

    private void writeData(String path, String content)
    {
        if(content.equalsIgnoreCase("[]"))
        {
            content = "";
        }
        try (FileWriter myWriter = new FileWriter(path)) {
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
