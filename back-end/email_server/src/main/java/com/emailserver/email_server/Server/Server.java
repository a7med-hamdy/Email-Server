package com.emailserver.email_server.Server;

import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.user;
import com.emailserver.email_server.userAndMessage.userContact;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Server {
    private String path = "src\\Data";
    private JSONArray arr = new JSONArray();
    private static Server instance;  
    private Gson gson;
    private File current_users;
    private folderManager folderManager;
    private contactManager contactManager;
    private Server() throws IOException{
        File f = new File(this.path);
        f.mkdir();
        this.path += "\\";
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.current_users = new File(path+"currentUsers.json");
        this.current_users.createNewFile();
        this.folderManager = new folderManager(this.path);
        this.contactManager = new contactManager(this.path+"currentUsers.json");
    }

    public static Server getInstanceOf() throws IOException{
        if(instance == null)
        {
            instance = new Server();
        }
        return instance;
    }

    public void SignUp(int id, String username, String password, String email, ArrayList<userContact> contacts) throws IOException{    
        user new_user = new user(id,username,password,email,contacts);
        this.arr.put(new JSONObject(this.gson.toJson(new_user)));
        this.writeUsers();
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

    public String getContacts(int userID)
    {
       return this.contactManager.getContacts(userID);
    }
    public void deleteContact(int userID, int contactID)
    {
        this.contactManager.deleteContact(userID, contactID);
    }

    public void editContactName(int userID, int contactID, String name)
    {
        this.contactManager.editContactName(userID, contactID, name);
    }
    public void addContact(int userID, userContact contact)
    {
        this.contactManager.addContact(userID, contact);
    }
    public void addContactEmail(int userID, int contactID, String newEmail)
    {
        this.contactManager.addContactEmail(userID, contactID, newEmail);
    }
    public void removeContactEmail(int userID, int contactID, String email)
    {
        this.contactManager.removeContactEmail(userID, contactID, email);
    }
    public void editContactEmail(int userID, int contactID, String oldEmail, String newEmail)
    {
        this.contactManager.editContactEmail(userID, contactID, oldEmail, newEmail);
    }

    public ArrayList<user> getUsers() throws IOException
    {
        String content = new Scanner(this.current_users).useDelimiter("\\Z").next();
        user[] users = this.gson.fromJson(content, user[].class);
        JSONArray temp = new JSONArray(content);
        this.arr.clear();
        this.arr.putAll(temp);
        return new ArrayList<user>(Arrays.asList(users));
    }

    //type "time" for sort by time and "priority" for priority
    public JSONArray requestFolder(int userID, String folder, String type, int count)
    {
        PriorityQueue<JSONObject> queue;
        if(type.equalsIgnoreCase("priority"))
        {
            queue = new PriorityQueue<>(new priorityComparator());
        }
        else
        {
            queue = new PriorityQueue<>(new messageComparator());
        }
        JSONArray messages = new JSONArray();
        File[] folders = new File(this.path+userID+"\\"+folder).listFiles((FileFilter)FileFilterUtils.directoryFileFilter());
        for(File iter: folders)
        {
            ArrayList<File> files = (ArrayList<File>) FileUtils.listFiles(iter, new String[]{"json"}, false);
            String content = ReaderWriter.readData(files.get(0).toString());
            JSONObject temp = new JSONObject(content);
            queue.add(temp);
        }
        for(int i = 0; i < (count-1)*5;i++)
        {
            JSONObject temp = queue.poll();
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


    public void sendMessage(message m, String folder)
    {
        String json = this.gson.toJson(m);
        String path = this.path+m.getHeader().getSender()+"\\"+folder+"\\";
        this.addToIndex(path,json);
        path += + m.getID();
        File f = new File(path);
        f.mkdir();
        ReaderWriter.writeData(path +"\\"+m.getID()+".json", json);
    }

    public void sendMessage(message m) 
    {
        String json = this.gson.toJson(m);
        String path = this.path+m.getHeader().getSender()+"\\sent\\";
        this.addToIndex(path,json);
        path += + m.getID();
        File f = new File(path);
        f.mkdir();
        ReaderWriter.writeData(path +"\\"+m.getID()+".json", json);
        Queue<Integer> receivers = m.getHeader().getReceiver();
        for(int i = 0; i <= receivers.size();i++)
        {
            int r = receivers.poll();
            path = this.path+r+"\\inbox\\";
            this.addToIndex(path,json);
            path += m.getID();
            File f2 = new File(path);
            f2.mkdir();
            ReaderWriter.writeData(path+"\\"+m.getID()+".json", json);
        }
    }

    public String getMessage(int userID, int messageID)
    {
        File[] folders = new File(this.path+userID).listFiles((FileFilter)FileFilterUtils.directoryFileFilter());
        for(File folder : folders)
        {
            System.out.println(folder);
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
         
        String content = ReaderWriter.readData(path+"index.json");
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
        ReaderWriter.writeData(path+"index.json", messages.toString());
    }

    private void addToIndex(String path, String m)
    {
        String content = ReaderWriter.readData(path+"index.json");
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
        ReaderWriter.writeData(path+"index.json", messages.toString());
    }

    

    public void addAttachment(int messageID)//, MultipartFile file) throws IOException
    {
        JSONArray content = new JSONArray(ReaderWriter.readData(this.path+"currentUsers.json"));
        for(int i = 0; i < content.length();i++)
        {
            JSONObject temp = content.getJSONObject(i);
            String ID = temp.optString("Id");
            System.out.println(temp);
            String [] folders = this.getFolders(Integer.parseInt(ID));
            for (String folder: folders)
            {
                if(!this.findMessage(path+ID+"\\"+folder, messageID, false).equalsIgnoreCase("-1"))
                {
                    File f = new File(this.path+ID+"\\"+folder+"\\"+messageID+"\\");
                    System.out.println(f);
                    //Files.copy(file.getInputStream(), f.toPath().resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING );
                }
            }
        }
    }





    private String findMessage(String path, int id, boolean flag)
    {
        String content = ReaderWriter.readData(path+"index.json");
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
                content=ReaderWriter.readData(path+"\\"+id+"\\"+id+".json");
                return flag ? content : Integer.toString(i);
            }
        }
        return "-1";
    }
    private void writeUsers() throws IOException
    {
        FileWriter myWriter = new FileWriter(this.current_users);
        myWriter.write(this.arr.toString());
        myWriter.close();
    }
    public String[] getFolders(int userID)
    {
        File f = new File(this.path+userID);
        String[] folders = f.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return new File(dir,name).isDirectory();
            }
            
        });
        return folders;
    }
    public void createFolder(int userID, String name)
    {
        this.folderManager.createFolder(userID, name);
    } 

    public void renameFolder(int userID, String newName, String oldName)
    {
        this.folderManager.renameFolder(userID, newName, oldName);
    }
    public void deleteFolder(int userID, String name)
    {
        this.folderManager.deleteFolder(userID, name);
    }
}