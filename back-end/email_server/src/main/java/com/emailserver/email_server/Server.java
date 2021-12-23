package com.emailserver.email_server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.logging.log4j.message.Message;

import java.io.*;
import java.util.PriorityQueue;

public class Server {
    private String path = "email_server\\src\\main\\java\\com\\emailserver\\email_server\\Data\\";
    private PriorityQueue<Message> queue = new PriorityQueue<>();
    private static Server instance;
    Gson gson;
    private Server(){gson = new GsonBuilder().setPrettyPrinting().create();}
    public static Server getInstanceOf(){
        if(instance == null)
        {
            return new Server();
        }
        return instance;
    }

    public void SignUp(String username, String password, String id){    
        File f = new File(this.path+id);
        boolean b = f.mkdir();
        if(b)
        {
            System.out.println("directory created successfully");
        }
        else{
            System.out.println("error creating directory");
        }
    }
    public void SignIn(String username, String password){

    }
}
