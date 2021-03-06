import { Component, OnInit,Inject ,ViewChild, SecurityContext } from '@angular/core';
import { HttpClient, HttpEventType, HttpParams, HttpResponse } from '@angular/common/http';
import { concatMap, delay, map, Observable, range } from 'rxjs';
import { FileUploadService } from 'src/app/services/file-upload.service';

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
        this.uploadFiles(this.msg);
      }
      else{
        this.msg=done;
        console.log("Error!! Something went WRONG!!");
        this.done = false;
      }

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


  readFile(file: File): Observable<string> {

    return new Observable(obs => {
      const reader = new FileReader();
      reader.onload = () => {
        this.fileList.push(file);
        this.attachNamse.push(file.name);
        if(file.type.includes("pdf") || file.type.includes("text") || file.type.includes("image")||
        file.type.includes("mp4") || file.type.includes("json") ||file.type.includes("mp3") ||file.type.includes("Xml")){
        obs.next(reader.result as string);
        obs.complete();
        }else{
          obs.next("");
          obs.complete()
        }
      }
      reader.readAsDataURL(file);
    });
  }




  selectFiles(event:any): void {
    this.urls = [];
    this.fileList = [];
    this.attachNamse=[];

    this.selectedFiles = event.target.files;
    let z =  event.target.files;
    if(this.selectedFiles){
      range(0, this.selectedFiles.length).pipe(

        concatMap(index => {

          return this.readFile(z[index]).pipe(
            map(result => ({ result }))
          );
        })
      ).subscribe(fileWithIndex => {
        let x=0;
        if(fileWithIndex.result.length==0){
          this.urls.push(this.sanitizer.bypassSecurityTrustResourceUrl( `dummy${x}`));
          x++;
        }else{
          this.urls.push(this.sanitizer.bypassSecurityTrustResourceUrl( fileWithIndex.result))
        }

      });
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

    let x=this.urls.indexOf(inter);
    this.urls.splice(x,1);
    this.fileList.splice(x,1);
    this.attachNamse.splice(x,1);


  }









}
