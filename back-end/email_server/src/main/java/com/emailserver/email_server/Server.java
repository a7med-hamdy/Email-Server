package com.emailserver.email_server;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.user;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Server {
    private String path = "src\\main\\java\\com\\emailserver\\email_server\\Data\\";
    private PriorityQueue<message> queue = new PriorityQueue<>();
    private ArrayList<user> users = new ArrayList<>();
    private JSONArray arr = new JSONArray();
    private static Server instance;  
    private Gson gson;
    private File current_users;
    private Server() throws IOException{
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.current_users = new File("currentUsers.json");
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
        this.writeUsers();
        File f = new File(this.path+Integer.toString(id));
        f.mkdir();
        f = new File(this.path+id+"\\inbox");
        f.mkdir();
        f = new File(this.path+id+"\\sent");
        f.mkdir();
        f = new File(this.path+id+"\\trash");
        f.mkdir();
        f = new File(this.path+id+"\\draft");
        f.mkdir();
    }

    public ArrayList<user> getUsers() throws IOException
    {
        String content = new Scanner(this.current_users).useDelimiter("\\Z").next();
        user[] users = gson.fromJson(content, user[].class);
        JSONArray temp = new JSONArray(content);
        this.arr.clear();
        this.arr.putAll(temp);
        System.out.println(this.arr);
        return new ArrayList<user>(Arrays.asList(users));
    }

    public void addMessage(message m)
    {

    }

    private void writeUsers() throws IOException
    {
        FileWriter myWriter = new FileWriter(this.current_users);
        myWriter.write(this.arr.toString());
        myWriter.close();
    }
}
