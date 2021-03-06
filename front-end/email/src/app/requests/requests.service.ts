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
  getSessionID(id:string){
    let _url = `${this.url}/auth/${id}`;
    return this.http.get<any>(_url);
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
  deleteEmail(ID:string,IDs: string[], Type: string, page:string){
    let _url = `${this.url}/delete/${ID}-${page}`;
    return this.http.delete<any>(`${this.url}/delete/${ID}-${page}`, {params: { IDs: IDs, type: Type, toTrash:'true'}})

  }
  MoveEmail(ID:string,IDs: string[], Type: string,destination: string,page:string){
    let _url = `${this.url}/Move/${ID}-${page}`;
    return this.http.delete<any>(`${this.url}/Move/${ID}-${page}`, {params: { ID: IDs, type: Type, destination: destination}})

  }


/*---------------------------------------------------------------
  Get Emails (Inbox | Trash | Draft | Sent)
  ---------------------------------------------------------------*/
  getEmailFolders(id:string){
    return this.http.get<any>(`${this.url}/getFolders/${id}`);
  }
  // get mails
  getEmails(t: string, id:string, page:string ,srt:string){
    return this.http.get<any>(`${this.url}/getEmails/${id}-${page}`, {params: {type: t, folder: srt}});
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
  getFilteredEmails(id: string, field:string, keyword:string, sortType:string, page:string){
    return this.http.get<any>(`${this.url}/filter/${id}-${page}`,{params: {keyword: keyword, sortType:sortType, field:field}})
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
  addContact(id:string, name: string, email: string){
    let params = new HttpParams
    params = params.append("name", name)
    params = params.append("email", email)
    return this.http.post<any>(`${this.url}/addContact/${id}`, params)
  }
  // delete contact
  deleteContact(id:string, ids: number[]){
    return this.http.delete<any>(`${this.url}/deleteContacts/${id}`, {params:{ids:ids}})
  }
  // edit contact
  editContact(id:string, contactId: number, oldEmails: string, newEmails: string, oldName: string, newName: string){
    let params = new HttpParams
    params = params.append("oldEmail", oldEmails)
    params = params.append("newEmail", newEmails)
    params = params.append("oldName", oldName)
    params = params.append("newName", newName)
    return this.http.put<any>(`${this.url}/editContacts/${id}/${contactId}`, params)
  }
  // filter contacts
  filterContacts(id :string, keyword: string){
    return this.http.get<any>(`${this.url}/filterContacts/${id}`,{params: {keyword: keyword}});
  }
  /*---------------------------------------------------------------
  folder Requests
  ---------------------------------------------------------------*/
  getFolders(id: string){
    return this.http.get<any>(`${this.url}/getFolderss/${id}`)
  }

  addFolder(id:string ,name:string){
    let param=new HttpParams();

    return this.http.post<any>(`${this.url}/makefolder/${id}/${name}`,param)
  }

  deleteFolder(id:string ,name:string){


    return this.http.delete<any>(`${this.url}/deletefolder/${id}/${name}`)
    }

  editFolder(id:string ,name:string,name2:string){
      let param=new HttpParams();

      return this.http.put<any>(`${this.url}/editfolder/${id}/${name}/${name2}`, param)
    }

  downfile(id:string){
    return this.http.get<any>(`${this.url}/deletefolder/${id}`)
  }
}


