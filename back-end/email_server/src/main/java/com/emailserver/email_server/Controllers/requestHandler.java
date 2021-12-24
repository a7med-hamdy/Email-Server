package com.emailserver.email_server.Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.emailserver.email_server.userAndMessage.contact;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.messageMaker;
// import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.emailserver.email_server.userAndMessage.user;

import org.springframework.http.ResponseEntity;
// import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class requestHandler {
    
    //needed attributes


/*---------------------------------------------------------------
Logging & Signing up Requests
-----------------------------------------------------------------*/

    //signup - post
    @PostMapping("/signUp")
    @ResponseBody
    public boolean signUp(@RequestBody user user){
        System.out.println("Sign up");
        try {
            return /* server.signUp(user) */true;
        }catch (Exception e){
            System.out.println("Error in signUp request!!");
            return false;
        }
    }

    //login - get
    @GetMapping("/login-{userName}-{password}")
    public boolean login(@PathVariable String userName, @PathVariable String password){
        System.out.println( "Log In\n" + 
                            "Username = " + userName + "\n" + 
                            "Password = " + password);
        try {
            return /* server.logIn(username, password) */true;
        }catch (Exception e){
            System.out.println("Error in logIn request!!");
            return false;
        }
    }

/*---------------------------------------------------------------
Emails (create | delete) Requests
-----------------------------------------------------------------*/

    //create email (Type: sent | draft) - post
    @PostMapping("/makeMesssage")
    public boolean createEmail( @RequestBody message Msg, 
                                // @RequestParam("from") String from,
                                // @RequestParam("subject") String subject,
                                // @RequestParam("body") String body,
                                @RequestParam("time") String time,
                                @RequestParam("type") String type
                                // @RequestParam("priority") boolean priority
                                /* ,
                                @RequestParam(name = "receivers") String receivers, 
                                @Nullable @RequestParam(name="myFile") MultipartFile[] multipartFiles */){
        System.out.println( "Subject = " + Msg.getHeader().getSubject() + 
                            "\nBoody = " + Msg.getBody());
        try {
            return /* create message */true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    //delete email(s) (moveToTrash | restoreFromTrash) - delete
    @DeleteMapping("/delete")
    @ResponseBody
    public void delete_or_restore(  @RequestBody int[] IDs, 
                                    @RequestParam("type") String type, 
                                    @RequestParam("toTrash") boolean toTrash){
        System.out.println("IDs are "+ Arrays.toString(IDs));

        try {
            for (int i : IDs) {
                if (toTrash)
                    /* server.sendToTrash(type, i) */;
                else
                    /* server.restoreFromTrash(i) */;
                i = i + 1;
            }
        }catch (Exception ignoredException){}
    }

/*---------------------------------------------------------------
Get Emails (unsorted | sorted | priority | filter) Requests
-----------------------------------------------------------------*/

    //get mails (Type: Inbox | Trash | Draft | sent)
    @GetMapping("/getEmails")
    public ArrayList<messageMaker> getEmails(@RequestParam("type") String type){
        System.out.println(type);
        try {
            return /*server.getMails(type)*/null;
        }catch (Exception e){
            return null;
        }
    }
    //sort
    @GetMapping("/sort")
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
    @GetMapping("/sortPriority")
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
    @GetMapping("/filter")
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
    @GetMapping("/download")
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
    @GetMapping("/getContacts")
    @ResponseBody
    public ArrayList<contact> getContacts(){
        try {
            /* ArrayList<Contact> list = server.sortContact();
            System.out.println("List of contacts : " + list); */
            return /* list */null;
        }catch (Exception e){
            return null;
        }
    }
   

    //add contact
    @PostMapping("/addContact")
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
    @DeleteMapping("/deleteContacts")
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
    @PutMapping("/editContacts")
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
    @GetMapping("/filterContacts")
    public ArrayList<contact> filterContacts( @RequestParam("typeOfSort") String type, 
                                                        @RequestParam("name") String name){
        return /* server.searchingContact(type, name) */null;
    }

}
