package com.emailserver.email_server.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReaderWriter {
    /**
     * take the file and read it and convert it to string
     * @param path the path of the file will be read
     * @return the content of the string
     */
    static String readData(String path)
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
            System.out.println("ERROR reading the file.");
        }
        return content;
    }

    /**
     * take string and write it in file
     * @param path where file will be  saved 
     * @param content the content will be written in the files
     */
    static void writeData(String path, String content)
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
