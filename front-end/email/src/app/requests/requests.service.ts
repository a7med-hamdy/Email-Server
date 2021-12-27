import { Injectable } from '@angular/core';
import { HttpClient,
         HttpEvent,
         HttpHeaders,
         HttpParams,
         HttpRequest,
         HttpResponse } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
//import { error } from 'console';
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

  logOut(id:string){
    let _url = `${this.url}/${id}/Logout`;
    this.http.post(_url, {}).subscribe(response =>{
      console.log("logout");
    })
  }
/*---------------------------------------------------------------
  Emails Requests
  ---------------------------------------------------------------*/

  // make message (sent or draft)
  makeMessage(params: HttpParams): Observable<any>{
    let _url = `${this.url}/makeMessage/${1}`;
    return this.http.post<any>(_url, params)
    /* .subscribe(responnse => {
      console.log("Message composed & saved successfully!!")
    },err => {
      alert("something went WRONG!!")
    }) */
  }

  // delete message(s) (moveToTrash or restoreFromTrash)
  delete_or_retrieve(ID:string,IDs: string[], Type: string, movetoTrash: boolean, page:string){
    let _url = `${this.url}/delete/${ID}-${page}`;
    return this.http.delete<any>(`${this.url}/delete/${ID}-${page}`, {params: { IDs: IDs, type: Type, toTrash:'true'}})

  }


/*---------------------------------------------------------------
  Get Emails (Inbox | Trash | Draft | Sent)
  ---------------------------------------------------------------*/
  // get mails
  getEmails(t: string, id:string, page:string){
    return this.http.get<any>(`${this.url}/getEmails/${id}-${page}`, {params: {type: t}});
   /* err => {
      //alert("something went WRONG!!")
    //} */
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
  getContacts(id:string){
    return this.http.get<any>(`${this.url}/getContacts/${id}`)
  }

  // add contact
  addContact(name: string, email: string){
    let params = new HttpParams
    params = params.append("name", name)
    params = params.append("email", email)
    return this.http.post<any>(`${this.url}/addContact/${887788}`, params)
  }
  // delete contact
  deleteContact(names: String[]){
    return this.http.post<any>(`${this.url}/deleteContacts/${887788}`, names)
  }
  // edit contact
  editContact(emails: string, oldName: string, newName: string){
    let params = new HttpParams
    params = params.append("email", emails)
    params = params.append("oldName", oldName)
    params = params.append("newName", newName)
    return this.http.post<any>(`${this.url}/addContact/${887788}`, params)
  }
  // filter contacts
  filterContacts(){

  }
  /*---------------------------------------------------------------
  folder Requests
  ---------------------------------------------------------------*/
  addFolder(id:string ,name:string){
    let param=new HttpParams();

    return this.http.post<any>(`${this.url}/makefolder/${id}/${name}`,param).subscribe(response=>{
      console.log(response);
    }

    );
  }

  deleteFolder(id:string ,name:string){


    return this.http.delete<any>(`${this.url}/deletefolder/${id}/${name}`).subscribe(response=>{
      console.log(response);})
    }

    editFolder(id:string ,name:string,name2:string){
      let param=new HttpParams();

      return this.http.put<any>(`${this.url}/editfolder/${id}/${name}/${name2}`, param).subscribe(response=>{
        console.log(response);})
      }

}


