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

  constructor(private route:ActivatedRoute,
              private router:Router) {

              }

  ngOnInit(): void {
    let id=this.route.snapshot.paramMap.get('id');
    console.log(id);
  }
  /*activet(a:number){
    console.log(a)
    this.router.navigate(['/main',a]);
  }*/

  active(a:number){
    console.log(a)
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
    }else{
      console.log("a7a")
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
