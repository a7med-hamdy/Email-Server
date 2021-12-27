package com.emailserver.email_server.Controllers;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.UrlResource;
import com.emailserver.LoginAndSessionManagement.LoggingManager;
import com.emailserver.LoginAndSessionManagement.sessionInterface;
import com.emailserver.LoginAndSessionManagement.sessionManager;
import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.contact;
import com.emailserver.email_server.userAndMessage.message;

import com.emailserver.email_server.userAndMessage.messageMaker;
// import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.emailserver.email_server.userAndMessage.user;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;





@CrossOrigin
@RestController
public class requestHandler {
    
    //needed attributes
    public sessionManager sManager = sessionManager.getInstanceOf();
    public LoggingManager lManager = new LoggingManager();
    public messageMaker mMaker=new messageMaker();
    public message msg;
    public Proxy proxy;
   

/*---------------------------------------------------------------
Logging & Signing up Requests
-----------------------------------------------------------------*/

    //signup - post
    @PostMapping("/signUp")
    @ResponseBody
    public int signUp(@RequestBody user user){
        System.out.println("Sign up");
        try {
            return lManager.REGISTER(user.getUserName(), user.getEmail(), user.getPassword());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in signUp request!!");
            return 0;
        }
    }

    //login - get
    @GetMapping("/login-{userName}-{password}")
    @ResponseBody
    public int login(@PathVariable String userName, @PathVariable String password){
        System.out.println( "Log In\n" + 
                            "Username = " + userName + "\n" + 
                            "Password = " + password);
        try {
            System.out.println(sManager.getSessions());

            return lManager.LOGIN(userName, password);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in logIn request!!");
            return 0;
        }
    }

    @PostMapping("/{id}/Logout")
    public void logOut(@PathVariable("id") String userID)
    {
        sManager.deleteSession(Integer.parseInt(userID));
        System.out.println(sManager.getSessions());
    }

/*---------------------------------------------------------------
Emails (create | delete) Requests
-----------------------------------------------------------------*/

    //create email (Type: sent | draft) - post
    @PostMapping("/makeMessage/{userId}")
    public int createEmail( @RequestParam("receivers") String to,
                                @RequestParam("subject") String subject,
                                @RequestParam("body") String body,
                                @RequestParam("type") String type,
                                @PathVariable String userId,
                                @RequestParam("priority") String priority,
                                @Nullable @RequestParam("attachments") ArrayList<String> files) throws IOException{
        System.out.println(to+"\n"+ subject +"\n"+ body +"\n"+ type +"\n"+ priority +"\n"+ userId);
    
         try {
            Date time = new Date();
             // making a random number for id
            int min=1,max=50000;
            int id=(int)Math.floor(Math.random()*(max-min+1)+min);
            Queue <Integer> To = new LinkedList<>();
            proxy = new Proxy("", "");
            To = proxy.getReceiversIds(to.split(","));
            message Msg = this.mMaker.getNewMessage(id, body, body.length(), Integer.parseInt(userId), To, subject, time, Integer.parseInt(priority), files);
            sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userId));
            s.addMessage(Msg);
            return id;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    //delete email(s) (moveToTrash | restoreFromTrash) - delete
    @DeleteMapping("/delete/{id}-{page}")
    @ResponseBody
    public String delete_or_restore(  @RequestParam("IDs") String[] IDs, 
                                    @RequestParam("type") String type, 
                                    @RequestParam("toTrash") String toTrash,
                                    @PathVariable("id") String userId,
                                    @PathVariable("page")String page){
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userId));
        try {
            for (String i : IDs) {
                if (toTrash.equals("true"))
                    s.moveMessage(Integer.parseInt(i),type,"trash");
                else
                    s.moveMessage(Integer.parseInt(i),"trash", type);
                i = i + 1;
            }
            return s.getMessages(type, "priority", Integer.parseInt(page)).toString();
        }catch (Exception ignoredException){return null;}
    }


    @DeleteMapping("/Move/{id}-{page}")
    @ResponseBody
    public String moveEmail(        @RequestParam("ID") String[] IDs, 
                                    @RequestParam("type") String type, 
                                    @RequestParam("destination") String destination,
                                    @PathVariable("id") String userId,
                                    @PathVariable("page")String page){
        System.out.println(IDs);
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userId));
        try {
            for (String i : IDs) {
                    s.moveMessage(Integer.parseInt(i),type,destination);
                i = i + 1;
            }
            return s.getMessages(type, "priority", Integer.parseInt(page)).toString();
        }catch (Exception ignoredException){return null;}
        //return "oK";
    }

/*---------------------------------------------------------------
Get Emails (unsorted | sorted | priority | filter) Requests
-----------------------------------------------------------------*/
    @GetMapping("/getFolders/{id}")
    public String[] getEmailFolders( 
                        @PathVariable("id") String userId){
    System.out.println(userId);
    try {
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userId));
        return s.getEmailFolders();
    }catch (Exception e){
        e.printStackTrace();
        return null;
    }
}
    //get mails (Type: Inbox | Trash | Draft | sent)
    @GetMapping("/getEmails/{id}-{page}")
    public String getEmails(@RequestParam("type") String type, 
                            @RequestParam("folder") String folder,
                            @PathVariable("id") String userId,
                            @PathVariable("page") String p){
        System.out.println(type);
        try {
            sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userId));
            return s.getMessages(type, folder,Integer.parseInt(p)).toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //sort
    @GetMapping("/sort/{id}")
    public ArrayList<messageMaker> getSorted( @RequestParam("folder") String folder, 
                                                @RequestParam("type") String type){
        try {
            System.out.println("Folder = " + folder
                             + "\nType = " + type);
            // ArrayList<messageCreator> messages = server.sort(folder, type);
            // System.out.println("Size is  "+ messages.size());
            return /* messages */null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //sortPriority
    @GetMapping("/sortPriority/{id}")
    public ArrayList<message> sortPriority(@RequestParam("inboxOrSent") String inbox){
        /* ArrayList<message> list = new ArrayList<>();
        ArrayList<? extends message> primary = server.myFilter(Constants.TRUE ,Constants.PRIORITY , inbox);
        ArrayList<? extends message> defaultList = server.myFilter(Constants.FALSE , Constants.PRIORITY , inbox);
        if(primary != null)
            list.addAll(primary);
        if(defaultList != null)
            list.addAll(defaultList);
        System.out.println(list);

        return list.isEmpty() ? null : list; */
        return null;
    }
    //filter
    @GetMapping("/filter/{id}")
    public ArrayList<? extends message> filterBy(@RequestParam(name = "filterName")String filterName,
                                                 @RequestParam(name = "subOrRec") String subOrRec,
                                                 @RequestParam(name = "inboxOrSent") String inOrSent){

        System.out.println(filterName);
        System.out.println(subOrRec);
        System.out.println(inOrSent);

        return /* server.myFilter(filterName, subOrRec, inOrSent) */null;
    }

/*---------------------------------------------------------------
Download Attachment Request
-----------------------------------------------------------------*/

    //download Attachment
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, 
                                                            @RequestParam(name="id")String id, 
                                                            @RequestParam(name="type") String type,
                                                            @RequestParam(name="name")String file)  {
        return null;
    }

/*---------------------------------------------------------------
Contacts (get | add | delete | edit | filter) Requests
-----------------------------------------------------------------*/
    //contacts - get
    @GetMapping("/getContacts/{id}")
    @ResponseBody
    public String getContacts(@PathVariable("id") String userID){
        try {
            sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userID));
            return s.getContacts();
        }catch (Exception e){
            return null;
        }
    }
   

    //add contact
    @PostMapping("/addContact/{id}")
    public boolean addContact(  @PathVariable("id") String userID,
                                @RequestParam("email") String email, 
                                @RequestParam("name") String name) throws IOException  {
        System.out.println("Contact name = " + name);
        System.out.println("Emails = " + email);
        try {
            Server server = Server.getInstanceOf();
            server.addContact(Integer.parseInt(userID), email, name);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //delete contact
    @DeleteMapping("/deleteContacts/{id}")
    @ResponseBody
    public void deleteContacts(@RequestBody ArrayList<String> names){
        try {
            // for (String name : names) continue;
                // server.deleteContact(name);
        }catch (Exception e){
            // e.printStackTrace();
        }
    }

    //edit contact
    @PutMapping("/editContacts/{id}")
    public boolean editContacts(@RequestParam("email") String emails, 
                                @RequestParam("newName") String newName, 
                                @RequestParam("oldName") String oldName){
        // ArrayList<String> emailsList = new ArrayList<>();
        // String[] list = emails.split(",");
        // Collections.addAll(emailsList , list);
        try {
            // server.editContact(oldName, newName, emailsList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    //filter contact
    @GetMapping("/filterContacts/{id}")
    public ArrayList<contact> filterContacts( @RequestParam("typeOfSort") String type, 
                                                        @RequestParam("name") String name){
        return /* server.searchingContact(type, name) */null;
    }

    /*private final Path root = Paths.get("uploads");*/

    ////atachment 
    @PostMapping("/upload/{id}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("id") int Id) {
    String message = "";

    try {
     /* Files.createDirectory(root);*/
    
      Server s = Server.getInstanceOf();
      s.addAttachment(Id, file);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  

  /*---------------------------------------------------------------
folder (get | make | delete | edit ) Requests
-----------------------------------------------------------------*/
// make folder
@PostMapping("/makefolder/{id}/{name}")
public boolean makeFolder(@PathVariable("name") String name,
                          @PathVariable("id") String user){
    try {
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(user));
        s.addFolder(name);
        return true;
    }catch (Exception e){
        return false;
    }
}
//delete folder
@DeleteMapping("/deletefolder/{id}/{name}")
public boolean deleteFolder(@PathVariable("name") String name,
                          @PathVariable("id") String user){
 
    try {
    
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(user));
        s.deleteFolder(name);
        return true;
    }catch (Exception e){
        return false;
    }
}
 //edit contact
 @PutMapping("/editfolder/{id}/{oldname}/{newname}")
 public boolean editFoldre(@PathVariable("id") String user,
                            @PathVariable("oldname") String name1,
                            @PathVariable("newname") String name2){
   
 
     try {
        
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(user));
        s.renameFolder(name1,name2);
     }catch (Exception e){
         e.printStackTrace();
     }
     return true;
 }


}
