import { Component, OnInit,Inject ,ViewChild } from '@angular/core';
import { HttpClient, HttpEventType, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from 'src/app/services/file-upload.service';
import { urlx } from './type'
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RequestsService } from '../requests/requests.service';




@Component({
  selector: 'app-maker',
  templateUrl: './maker.component.html',
  styleUrls: ['./maker.component.css']
})
export class MakerComponent implements OnInit {

  to?:string;
  subject?:string;
  messagex?:string;
  msg: number=-1;
  userID?:string;
  selectedFiles?: FileList;
  attachNamse: Array<string>=[];
  url = "http://localhost:8080";
  selected: string = '0';
  done: boolean = false;

  constructor(private uploadService: FileUploadService,private sanitizer: DomSanitizer,
              private http: HttpClient,
              private fb: FormBuilder,
              private router: Router,
              public route:ActivatedRoute,
              private rs: RequestsService) { }

  fileList: File[] = [];
  urls:SafeUrl[] = [];

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{ this.userID = params["ID"]});
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
    attatchments: this.fb.array([
      this.fb.control('')
    ]),
  });

  // make message (sent or draft) - request
  makeMessage(type: string): void{
    let _url = `${this.url}/makeMessage/${this.userID}`;

    let params = new HttpParams()
    params = params.append('subject',this.messageForm.value.subject)
    params = params.append('body',this.messageForm.value.body)
    params = params.append('type',type)
    params = params.append('priority',this.messageForm.value.priority)
    params = params.append('receivers', "" + this.messageForm.value.toEmails)
    params = params.append('attachments', "" + this.attachNamse)
    this.http.post<any>(_url,params)
    .subscribe(done => {
      if(done>-1){
        this.msg=done;
        console.log(this.msg,done);
        console.log("Message composed & saved successfully!!");
        this.done = true;
      }
      else{
        this.msg=done;
        console.log("Error!! Something went WRONG!!");
        this.done = false;
      }
      this.uploadFiles(this.msg);
    },err => {alert("something went WRONG!!")})



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
  enableDraftOrDeleteBtn(): boolean{
    return (this.messageForm.value.toEmails[0] as string).length !== 0
    || (this.messageForm.value.subject as string).length !== 0
    || (this.messageForm.value.body as string).length !== 0
  }
  /* ****************************************************** */

  selectFiles(event:any): void {
    this.urls = [];
    this.fileList = [];
    this.attachNamse=[];
    this.selectedFiles = event.target.files;
    if(this.selectedFiles){
     for (let i = 0; i < this.selectedFiles.length; i++) {
        this.fileList.push(this.selectedFiles[i])
        this.attachNamse.push(this.selectedFiles[i].name);
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



  uploadFiles(ids:number): void {

    if (this.fileList && ids>-1) {
      for (let i = 0; i < this.fileList.length; i++) {
        this.upload(i, this.fileList[i],ids);
      }
    }
  }


  upload(idx: number, file: File,ids: number): void {

    if (file) {
      this.uploadService.upload(file,ids).subscribe({
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
    this.attachNamse.splice(x,1);
    console.log(this.urls)
    console.log(this.fileList)
    console.log(this.attachNamse)
  }







}
