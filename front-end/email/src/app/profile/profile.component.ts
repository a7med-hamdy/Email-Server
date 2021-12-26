import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  here1:Boolean=false;
  main:Boolean=true;
  here2:Boolean=false;


  back():void {
    this.here1=false;
    this.main=true;
    this.here2=false;
  }

  getcontact():void{
    this.here1=true;
    this.main=false;
    this.here2=false;
  }

  getFolder():void{
    this.here1=false;
    this.main=false;
    this.here2=true;
  }
}
