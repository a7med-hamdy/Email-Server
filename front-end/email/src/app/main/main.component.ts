import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";



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




  constructor(public route:ActivatedRoute,
              public router:Router) {
              }
  ngOnInit(): void {
    this.routerEventListener();

    //this.router.urlHandlingStrategy.extract(this.router.url)= "reload";
  }
  routerEventListener(){
    this.router.events.subscribe((event) => {
      this.active(this.router.url)
      this.profile1(this.router.url);
      this.search1(this.router.url);
      this.make1(this.router.url);
    });
  }


  active(a:string){
    this.view=true
    this.profile=false;
    this.search=false;
    this.make=false;
    if(a=='/main/Inbox'){
      this.viewI=true;
      this.viewS=false;
      this.viewD=false;
      this.viewT=false;
    }
    else if(a=='/main/Sent'){
      this.viewS=true;
      this.viewI=false;
      this.viewD=false;
      this.viewT=false;
    }
    else if(a=='/main/Drafted'){
      this.viewD=true;
      this.viewI=false;
      this.viewS=false;
      this.viewT=false;
    }
    else if(a=='/main/Deleted'){
      this.viewT=true;
      this.viewI=false;
      this.viewS=false;
      this.viewD=false;
    }


  }
  profile1(a:string){
    if(a == "/main/Profile"){
    this.profile=true;
    this.search=false;
    this.make=false;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
    }
  }
  make1(a:String){
    if(a == "/main/Create"){
    this.profile=false;
    this.search=false;
    this.make=true;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
    }
  }
  search1(a:String){
    if(a == "/main/Search"){
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



}
