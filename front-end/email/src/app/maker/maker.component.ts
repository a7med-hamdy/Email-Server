import { Component, OnInit,Inject ,ViewChild } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from 'src/app/services/file-upload.service';
import { urlx } from './type'
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';




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


  constructor(private uploadService: FileUploadService,private sanitizer: DomSanitizer) { }

  fileList: File[] = [];
  urls:SafeUrl[] = [];

  ngOnInit(): void {
  }




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
