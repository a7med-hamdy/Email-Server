import { Component, OnInit } from '@angular/core';
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
  selectedfiles?:File

  selectedFiles?: FileList;


  constructor(private uploadService: FileUploadService,private sanitizer: DomSanitizer) { }
  url ?: SafeUrl;

  urls:string[] = [];

  ngOnInit(): void {
  }




  selectFiles(event:any): void {
    this.selectedFiles = event.target.files;
    if(this.selectedFiles){
     for (let i = 0; i < this.selectedFiles.length; i++) {
        var reader = new FileReader();
        reader.readAsDataURL(this.selectedFiles[i]);
        reader.onload = (events:any) => {
          this.url=events.target.result;
      }
     }
    }
  }


  uploadFiles(): void {

    if (this.selectedFiles) {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i]);
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

}
