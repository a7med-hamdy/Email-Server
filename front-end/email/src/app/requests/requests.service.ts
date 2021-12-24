import { Injectable } from '@angular/core';
import { HttpClient,
         HttpEvent, 
         HttpHeaders, 
         HttpParams, 
         HttpRequest, 
         HttpResponse } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { error } from 'console';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  Headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  url = "http://localhost:8080";

  //constructor
  constructor(private http: HttpClient,
              private formBuilder: FormBuilder,
              private router: Router) {}

  // for signup component
  public signupForm !: FormGroup;
  ngOnInit(): void{
    this.signupForm = this.formBuilder.group({
      userName: [''],
      email:    [''],
      password: [''],
    })
  }
  // for login component
  public loginForm !: FormGroup
  ngOnInit_2(): void{
    this.loginForm = this.formBuilder.group({
      username: [''],
      password: [''],
    });
  }

  // sign up - request
  signUp(){
    return this.http.post<any>(`${this.url}/signUp`, this.signupForm.value)
    .subscribe(response => {
      console.log("Signed up successfully!!")
      this.signupForm.reset();
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }

  // log in - request
  logIn(){
    let _url = `${this.url}/login-${this.loginForm.value.username}-${this.loginForm.value.password}`;
    return this.http.get<any>(_url)
    .subscribe(done => {
      console.log("Signed in successfully!!")
      if(done){
        this.loginForm.reset();
        this.router.navigate([/* 'homePage' */]) //navigate to user's home page
      }
      else{
        //action for Not Found User!!
      }
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }

/*---------------------------------------------------------------
  Emails Requests
  ---------------------------------------------------------------*/
  messageForm !: FormGroup;
  ngOnInit_3(): void{
    this.messageForm = this.formBuilder.group({
      to: [''],
      subject: [''],
      body: [''],
      // time: [''],
      // type: [''],
      priority: [''],
    });
  }
  // make message (sent or draft)
  makeMessage(Type: string, Time: string){
    let _url = `${this.url}/makeMessage`;
    return this.http.post<any>(_url, this.messageForm.value, {params: {time: Time, type: Type}})
    .subscribe(responnse => {
      console.log("Message composed & saved successfully!!")
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }

  // delete message(s) (moveToTrash or restoreFromTrash)
  delete_or_retrieve(IDs: number[], Type: string, movetoTrash: boolean){
    let _url = `${this.url}/delete`;
    this.http.delete(_url, this.messageForm.value)
    .subscribe(response => {
      console.log("deleted/restores successfuly!!")
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }


/*---------------------------------------------------------------
  Get Emails (Inbox | Trash | Draft | Sent)
  ---------------------------------------------------------------*/

  // get mails
  getEmails(t: string){
    return this.http.get<any>(`${this.url}/getEmails`, {params: {type: t}})
    .subscribe(response => {
      console.log("Emails gotten successfully!!")
      console.log(response)
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }
  // get sorted mails
  getSortedMails(f: string, t: string){
    return this.http.get<any>(`${this.url}/sort`, {params: {folder: f, type: t}})
    .subscribe(response => {
      console.log("Emails gotten successfully!!")
      console.log(response)
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }
  // get sorted priority mails
  getEmailsByPriority(isInbox: boolean){
    return this.http.get<any>(`${this.url}/sortPriority`, {params: {inboxOrSent: isInbox}})
    .subscribe(response => {
      console.log("Emails gotten successfully!!")
      console.log(response)
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }
  // get filtered mails
  getFilteredEmails(){

  }


/*--------------------------------------------------------------- 
  Download Attachment
  ---------------------------------------------------------------*/

  // download attachment
  downloadAttaches(){

  }


/*--------------------------------------------------------------- 
  Contacts requests
  ---------------------------------------------------------------*/

  // get contacts
  getContacts(){

  }

  // add contact
  addContact(){

  }
  // delete contact
  deleteContact(){

  }
  // edit contact
  editContact(){

  }
  // filter contacts
  filterContacts(){

  }
}
