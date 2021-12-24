package com.emailserver.email_server.userAndMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Server {
    private String path = "src\\Data";
    private PriorityQueue<message> queue = new PriorityQueue<>();
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
        ArrayList<String> folders = new ArrayList<>(Arrays.asList("inbox","trash","sent","draft"));
        for(String folder : folders)
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

    public String requestFolder(int userID, String folder)
    {
        
    }

    public void sendMessage(message m) 
    {
        String json = gson.toJson(m);
        
        System.out.println(new JSONArray(new ArrayList<>(Arrays.asList(m))));
        
        String path = this.path+m.getSender()+"\\sent\\";
        this.addToIndex(path,json);
        path += + m.getID();
        File f = new File(path);
        f.mkdir();
        this.writeData(path +"\\"+m.getID()+".json", json);
        ArrayList<Integer> receivers = m.getReceivers();
        for(int r : receivers)
        {
            path = this.path+r+"\\inbox\\";
            this.addToIndex(path,json);
            path += m.getID();
            File f2 = new File(path);
            f2.mkdir();
            this.writeData(path+"\\"+m.getID()+".json", json);
        }
    }

    public String getMessage(int userID, int messageID)
    {
        File[] folders = new File(this.path+userID).listFiles((FileFilter)FileFilterUtils.directoryFileFilter());
        for(File folder : folders)
        {
            String content = this.findMessage(folder+"\\", messageID, true);
            if(!content.equalsIgnoreCase("-1"))
            {
                System.out.println(folder);
                return content;
            }
        }
        return "not Found";
    }

    public void moveMessage(int userID, int messageID, String src, String dst)
    {
        String source = this.path+userID+"\\"+src+"\\";
        String json = this.findMessage(source, messageID, true);
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

    

    private void removeFromIndex(String path, int id)
    {
        int index = Integer.parseInt(this.findMessage(path, id, false));
         
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

    private void addToIndex(String path, String m)
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
        JSONObject temp = new JSONObject(m);
        JSONObject obj = new JSONObject().putOpt("ID", temp.optString("ID")).putOpt("subject", temp.getJSONObject("header").optString("subject"));
        messages.put(obj);
        this.writeData(path+"index.json", messages.toString());
    }

    private String findMessage(String path, int id, boolean flag)
    {
        String content = this.readData(path+"index.json");
        if(content.equals(""))
        {
            return "-1";
        }
        JSONArray messages = new JSONArray(content);
        for(int i = 0; i < messages.length();i++)
        {
            JSONObject temp = messages.getJSONObject(i);
            if(temp.optString("ID").equals(Integer.toString(id)))
            {
                content = readData(path+"\\"+id+"\\"+id+".json");
                return flag ? content : Integer.toString(i);
            }
        }
        return "-1";
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
    }
    public void renameFolder(int userID, String newName, String oldName)
    {
        File f = new File(this.path+userID+"\\"+oldName);
        if(f.isDirectory())
        {
            f.renameTo(new File(this.path+userID+"\\"+newName));
        }
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
    }
}
