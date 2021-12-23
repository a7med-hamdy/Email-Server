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
  routnig?:string;
  id?:string;

  constructor(public route:ActivatedRoute,
              public router:Router) {
                router.navigate(['main']);

              }
  ngOnInit(): void {
    this.routerEventListener();

    //this.router.urlHandlingStrategy.extract(this.router.url)= "reload";
    console.log(this.router.url);
  }
  routerEventListener(){
    this.router.navigateByUrl('main/');
    this.router.events.subscribe((event) => {
      console.log(event)
      this.active(this.router.url)
      this.profile1(this.router.url);
      this.search1(this.router.url);
      this.make1(this.router.url);
    });
  }
  /*activet(a:number){
    console.log(a)
    this.router.navigate(['/main',a]);
  }*/

  active(a:string){
    console.log(this.router.url);

    console.log(a)
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
