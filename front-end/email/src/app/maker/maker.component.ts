import { Component, OnInit,Inject ,ViewChild } from '@angular/core';
import { HttpClient, HttpEventType, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from 'src/app/services/file-upload.service';
import { urlx } from './type'
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';




@Component({
  selector: 'app-maker',
  templateUrl: './maker.component.html',
  styleUrls: ['./maker.component.css']
})
export class MakerComponent implements OnInit {

  to?:string;
  subject?:string;
  messagex?:string;


  selectedFiles?: FileList;

  url = "http://localhost:8080";

  constructor(private uploadService: FileUploadService,private sanitizer: DomSanitizer,
              private http: HttpClient,
              private fb: FormBuilder,
              private router: Router) { }

  fileList: File[] = [];
  urls:SafeUrl[] = [];

  ngOnInit(): void {
  }
  /******************************************************* */
  messageForm = this.fb.group({
    // ID: [''],
    toEmails: this.fb.array([
      this.fb.control('', [Validators.email, Validators.minLength(2)])
    ]),
    subject: [''],
    body: [''],
    priority: [''],
    /* attatchments: this.fb.array([
      this.fb.control('')
    ]), */
  });

  // make message (sent or draft) - request
  makeMessage(Type: string){
    let _url = `${this.url}/makeMessage`;
    let bdy = this.messageForm.value.body;
    let subj: string= this.messageForm.value.subject;
    let pri: number = this.messageForm.value.priority;
    let receivers_emails: string[] = this.messageForm.value.toEmails;
    // let attachs: string[] = this.messageForm.value.attachements;
    console.log(receivers_emails, subj)
    /* let params = new HttpParams()
    params = params.append('subject',subj)
    params = params.append('body',bdy)
    params = params.append('type',Type)
    params = params.append('priority',pri) */
    return this.http.post<any>(_url,
      {params: {/* recs: receivers_emails, */ subject: subj, body: bdy, 
                type: Type, priority: pri}})
    .subscribe(done => {
      if(done){
        console.log("Message composed & saved successfully!!");
      }
      else{
        console.log("Error!! Something went WRONG!!");
      }
    }/* ,err => {alert("something went WRONG!!")} */)
  }

  makeMessageOfType(type: string){
    console.log(this.messageForm.value)
    this.makeMessage(type)
  }
  
  get toEmails(){
    return this.messageForm.get('toEmails') as FormArray;
  }
  addTo(){
    if(this.toEmails.length !== 5){
      this.toEmails.push(this.fb.control('', Validators.email));
    }
  }
  deleteTo(i: number){
    if(this.toEmails.length !== 1){
      this.toEmails.removeAt(i);
    }
  }
  enableSentBtn(): boolean{
    return this.messageForm.valid  && (this.messageForm.value.toEmails[0] as string).length !== 0;
  }
  /* ****************************************************** */

  selectFiles(event:any): void {
    this.urls = [];
    this.selectedFiles = event.target.files;
    if(this.selectedFiles){
     for (let i = 0; i < this.selectedFiles.length; i++) {
        this.fileList.push(this.selectedFiles[i])
        if(this.selectedFiles[i].type.includes("pdf") || this.selectedFiles[i].type.includes("text") || this.selectedFiles[i].type.includes("image")
        || this.selectedFiles[i].type.includes("mp4") || this.selectedFiles[i].type.includes("json") || this.selectedFiles[i].type.includes("XML")
        || this.selectedFiles[i].type.includes("mp3")){
        var reader = new FileReader();
        reader.readAsDataURL(this.selectedFiles[i]);
        reader.onload = (events:any) => {
          this.urls.push(this.sanitizer.bypassSecurityTrustResourceUrl( events.target.result))
        }
      }

      else{
        this.urls.push(this.sanitizer.bypassSecurityTrustResourceUrl( "https://uploads-us-west-2.insided.com/looker-en/attachment/d0a25f59-c9b7-40bd-b98e-de785bbd04e7.png"))
        }
       }

    }

  }



  uploadFiles(): void {

    if (this.fileList) {
      for (let i = 0; i < this.fileList.length; i++) {
        this.upload(i, this.fileList[i]);
      }
    }
  }


  upload(idx: number, file: File): void {
    console.log(file);
    if (file) {
      this.uploadService.upload(file).subscribe({
        next: (event: any) => {
          console.log(event)
        },
        error: (err: any) => {
          console.log(err)
        }
      });
    }
  }

  del(inter:SafeUrl):void {
    console.log("here")
    let x=this.urls.indexOf(inter);
    this.urls.splice(x,1);
    this.fileList.splice(x,1);

  }







}
