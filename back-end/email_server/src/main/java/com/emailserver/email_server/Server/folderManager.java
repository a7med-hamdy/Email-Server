package com.emailserver.email_server.Server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
/**
 * a helper class to manage the folders
 */
class folderManager {
    String path = "";
    protected folderManager(String str)
    {
        this.path = str;
    }
    /**
     * a method that creates a folder
     * @param userID the id of the user
     * @param name the name of the folder
     */
    protected void createFolder(int userID, String name)
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
    /** 
     * a function that renames a folder
     * @param userID the id of the user
     * @param newName the new name of the folder
     * @param oldName the old name of the folder
     */
    protected void renameFolder(int userID, String newName, String oldName)
    {
        File f = new File(this.path+userID+"\\"+oldName);
        if(f.isDirectory())
        {
            f.renameTo(new File(this.path+userID+"\\"+newName));
        }
    }
    /**
     * a function that deletes a folder
     * @param userID the id of the user
     * @param name the name of the folder to be deleted
     */
    protected void deleteFolder(int userID, String name)
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
