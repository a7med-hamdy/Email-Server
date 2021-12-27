package com.emailserver.email_server.Controllers;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.emailserver.LoginAndSessionManagement.LoggingManager;
import com.emailserver.LoginAndSessionManagement.sessionInterface;
import com.emailserver.LoginAndSessionManagement.sessionManager;
import com.emailserver.email_server.Server.Server;
import com.emailserver.email_server.userAndMessage.contact;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.messageAttachmenets;
import com.emailserver.email_server.userAndMessage.messageBody;
import com.emailserver.email_server.userAndMessage.messageHeader;
import com.emailserver.email_server.userAndMessage.messageMaker;
// import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.emailserver.email_server.userAndMessage.user;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.json.JSONArray;
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
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete_or_restore(  @RequestBody int[] IDs, 
                                    @RequestParam("type") String type, 
                                    @RequestParam("toTrash") boolean toTrash,
                                    @PathVariable("id") String userId){
        System.out.println("IDs are "+ Arrays.toString(IDs));
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userId));
        try {
            for (int i : IDs) {
                if (toTrash)
                    s.moveMessage(i,type,"trash");
                else
                    s.moveMessage(i,"trash", type);
                i = i + 1;
            }
        }catch (Exception ignoredException){}
    }

/*---------------------------------------------------------------
Get Emails (unsorted | sorted | priority | filter) Requests
-----------------------------------------------------------------*/

    //get mails (Type: Inbox | Trash | Draft | sent)
    @GetMapping("/getEmails/{id}-{page}")
    public String getEmails(@RequestParam("type") String type, 
                            @PathVariable("id") String userId,
                            @PathVariable("page") String p){
        System.out.println(type);
        try {
            sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(userId));
            return s.getMessages(type, "priority",Integer.parseInt(p)).toString();
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
    public boolean addContact(  @RequestParam("email") String email, 
                                @RequestParam("name") String contactName)  {
       /*  String[]  contactsList = email.split(",");
        ArrayList<String> emails = new ArrayList<>();
        Collections.addAll(emails , contactsList); */
        System.out.println("Contact name = " + contactName);
        System.out.println("Emails = " + email);
        try {
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
@PostMapping("/makefolder/{id}")
public boolean makeFolder(@RequestParam("name") String name,
                          @RequestParam("name") String user){
    try {
        sessionInterface s = (sessionInterface)sManager.getSessionByUserID(Integer.parseInt(user));
        s.addFolder(name);
        return true;
    }catch (Exception e){
        return false;
    }
}
}
