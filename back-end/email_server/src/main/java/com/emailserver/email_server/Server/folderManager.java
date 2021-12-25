package com.emailserver.email_server.Server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

class folderManager {
    String path = "";
    protected folderManager(String str)
    {
        this.path = str;
    }
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
    protected void renameFolder(int userID, String newName, String oldName)
    {
        File f = new File(this.path+userID+"\\"+oldName);
        if(f.isDirectory())
        {
            f.renameTo(new File(this.path+userID+"\\"+newName));
        }
    }

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
