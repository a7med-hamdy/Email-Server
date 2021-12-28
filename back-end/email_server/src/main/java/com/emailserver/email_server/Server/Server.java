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
import java.util.NoSuchElementException;
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
        ReaderWriter.writeData(this.current_users.toString(), this.arr.toString());
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
        String content = ReaderWriter.readData(this.current_users.toString());
        user[] users = this.gson.fromJson(content, user[].class);
        JSONArray temp = new JSONArray(content);
        this.arr.clear();
        this.arr.putAll(temp);
        return new ArrayList<user>(Arrays.asList(users));
    }

    /////////////for deleting
    public void deleteDirectory(File file)
    {
        // store all the paths of files and folders present
        // inside directory
        for (File subfile : file.listFiles()) {
  
            // if it is a subfolder,e.g Rohan and Ritik,
            // recursiley call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
  
            // delete files and empty subfolders
            subfile.delete();
        }
    }
    //type "time" for sort by time and "priority" for priority
    public JSONArray requestFolder(int userID, String folder, String type, int count)
    {
        messageSorter Sorter = new messageSorter(type);
        File[] folders = new File(this.path+userID+"\\"+folder).listFiles((FileFilter)FileFilterUtils.directoryFileFilter());
        for(File iter: folders)
        {
            ArrayList<File> files = (ArrayList<File>) FileUtils.listFiles(iter, new String[]{"json"}, false);
            String content = ReaderWriter.readData(files.get(0).toString());
            message m = gson.fromJson(content, message.class);
            if(iter.toString().contains("trash") && m.getDeleted()){
                this.removeFromIndex(this.path+userID+"\\"+folder+"\\", m.getID());
                this.deleteDirectory(iter);
                iter.delete();
            }else{
                JSONObject temp = new JSONObject(this.addMessageAttachments(iter, m));
                Sorter.addToQueue(temp);
            }
           
        }
        return Sorter.sortMessages(count);
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

    // public String getMessage(int userID, int messageID)
    // {
    //     File[] folders = new File(this.path+userID).listFiles((FileFilter)FileFilterUtils.directoryFileFilter());
    //     if(folders == null)
    //     {
    //         return "error occurred retrieving the message";
    //     }
    //     for(File folder : folders)
    //     {
    //         System.out.println(folder);
    //         String content = this.findMessage(folder+"\\", messageID, true);
    //         if(!content.equalsIgnoreCase("-1"))
    //         {
    //             JSONObject json = new JSONObject(content);
    //             System.out.println(json);
    //             return content;
    //         }
    //     }
    //     return "not Found";
    // }

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
        obj.putOpt("sender", temp.getJSONObject("header").optString("senderId"));
        obj.putOpt("receivers", temp.getJSONObject("header").optString("recieverIds"));
        obj.putOpt("attachments", temp.getJSONObject("attachments").optString("attachements").replaceAll("\\\\", ""));
        obj.putOpt("body", temp.getJSONObject("body").optString("body"));
        messages.put(obj);
        ReaderWriter.writeData(path+"index.json", messages.toString());
    }

    

    public void addAttachment(int messageID, MultipartFile file) throws IOException
    {
        JSONArray content = new JSONArray(ReaderWriter.readData(this.path+"currentUsers.json"));
        for(int i = 0; i < content.length();i++)
        {
            JSONObject temp = content.getJSONObject(i);
            String ID = temp.optString("Id");
            String [] folders = this.getFolders(Integer.parseInt(ID));
            for (String folder: folders)
            {
                if(!this.findMessage(path+ID+"\\"+folder+"\\", messageID, false).equalsIgnoreCase("-1"))
                {
                    File f = new File(this.path+ID+"\\"+folder+"\\"+messageID+"\\");
                    Files.copy(file.getInputStream(), f.toPath().resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING );
                }
            }
        }
    }
    /**
     * 
     * @param userID
     * id of the user
     * @param field
     * the field is one of attachment/sender/receiver/subject/body/global for search
     * @param keyword
     * the keyword
     * @param sortType
     * the type of sort wanted time/priority/body
     * @param count
     * the needed page
     * @return
     */
    public JSONArray filterMessages(int userID, String field, String keyword, String sortType, int count)
    {
        Criteria criteria = CriteriaManager.getCriteria(field);
        messageSorter Sorter = new messageSorter(sortType);
        ArrayList<String> IDs = new ArrayList<>(); 
        String[] folders = this.getFolders(userID);
        for(String folder : folders)
        {
            IDs = criteria.meetCriteria(this.path+userID+"\\"+folder+"\\"+"index.json", keyword);
            for(String id : IDs)
            {
                File iter = new File (folder);
                message m = gson.fromJson(ReaderWriter.readData(this.path+userID+"\\"+folder+"\\"+id+"\\"+id+".json"), message.class);
                if(iter.toString().contains("trash") && m.getDeleted()){
                    this.removeFromIndex(this.path+userID+"\\"+folder+"\\", m.getID());
                    deleteDirectory(iter);
                    iter.delete();
                }
                else
                {
                    Sorter.addToQueue(new JSONObject(this.addMessageAttachments(new File(this.path+userID+"\\"+folder+"\\"+id), m)));
                }
            }
        }
        return Sorter.sortMessages(count);
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

    private String addMessageAttachments(File file, message m)
    {
        ArrayList<String> paths = new ArrayList<>();
        for(String t : m.getAttachments().getAttachment())
        {
            File f = new File(file, t);
            paths.add(f.getAbsolutePath());

        }
        m.getAttachments().setAttachments(paths);
        return gson.toJson(m);
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

    public String getContacts(int userID)
    {
       return this.contactManager.getContacts(userID);
    }
    public String deleteContact(int userID, int contactID)
    {
        return this.contactManager.deleteContact(userID, contactID);
    }

    public String editContactName(int userID, int contactID, String name)
    {
        return this.contactManager.editContactName(userID, contactID, name);
    }
    public String addContact(int userID, String email, String name)
    {
        return this.contactManager.addContact(userID, email, name);
    }
    public String addContactEmail(int userID, int contactID, String newEmail)
    {
        return this.contactManager.addContactEmail(userID, contactID, newEmail);
    }
    public String removeContactEmail(int userID, int contactID, String email)
    {
        return this.contactManager.removeContactEmail(userID, contactID, email);
    }
    public String editContactEmail(int userID, int contactID, String oldEmail, String newEmail)
    {
        return this.contactManager.editContactEmail(userID, contactID, oldEmail, newEmail);
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
