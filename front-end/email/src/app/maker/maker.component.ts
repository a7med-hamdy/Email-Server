import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-maker',
  templateUrl: './maker.component.html',
  styleUrls: ['./maker.component.css']
})
export class MakerComponent implements OnInit {

  to?:string;
  subject?:string;
  message?:string;
  selectedfiles?:File
  constructor() { }

  ngOnInit(): void {
  }

  Upload(file:any){
    console.log(file)
  }

}
