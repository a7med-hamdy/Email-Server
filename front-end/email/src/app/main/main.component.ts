import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  selected?: string;
  search:Boolean=false;
  profile:Boolean=false;
  make:Boolean=false;
  view:Boolean=true;
  viewI:Boolean=true;
  viewS:Boolean=false;
  viewD:Boolean=false;
  viewT:Boolean=false;
  filter?:string;

  constructor() { }

  ngOnInit(): void {
  }

  active(a:number){
    this.view=true
    this.profile=false;
    this.search=false;
    this.make=false;
    if(a==1){
      this.viewI=true;

      this.viewS=false;
      this.viewD=false;
      this.viewT=false;
    }
    else if(a==2){
      this.viewS=true;
      this.viewI=false;
      this.viewD=false;
      this.viewT=false;
    }
    else if(a==3){
      this.viewD=true;
      this.viewI=false;
      this.viewS=false;
      this.viewT=false;
    }
    else if(a==4){
      this.viewT=true;
      this.viewI=false;
      this.viewS=false;
      this.viewD=false;
    }


  }
  profile1(){
    this.profile=true;
    this.search=false;
    this.make=false;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
  }
  make1(){
    this.profile=false;
    this.search=false;
    this.make=true;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
  }
  search1(){
    this.profile=false;
    this.search=true;
    this.make=false;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
  }



}
